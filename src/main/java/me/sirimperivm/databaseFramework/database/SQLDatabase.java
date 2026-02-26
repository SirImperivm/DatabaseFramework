package me.sirimperivm.databaseFramework.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public abstract class SQLDatabase implements Database {

    protected HikariDataSource dataSource;

    private final ExecutorService executor;
    protected final DatabaseConfig config;
    protected final TableNameResolver resolver;

    private Runnable before;
    private Runnable after;

    public SQLDatabase(ExecutorService executor, DatabaseConfig config) {
        this.executor = executor;
        this.config = config;
        this.resolver = new TableNameResolver(config);
    }

    protected abstract HikariConfig createConfig();

    @Override
    public void connect() {
        this.dataSource = new HikariDataSource(createConfig());
        configureDatabase();
    }

    protected void configureDatabase() {}

    @Override
    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get connection", e);
        }
    }

    @Override
    public void execute(String query, Object... params) {
        runBefore();

        String resolvedQuery = resolver.resolve(query);

        try (Connection con = getConnection();
             PreparedStatement stmt = prepare(con, resolvedQuery, params)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseQueryException(e.getMessage(), query + "\n" + resolvedQuery, e);
        } finally {
            runAfter();
        }
    }

    @Override
    public <T> T query(String query, QueryMapper<T> mapper, Object... params) {
        runBefore();

        String resolvedQuery = resolver.resolve(query);

        try (Connection con = getConnection();
             PreparedStatement stmt = prepare(con, resolvedQuery, params);
             ResultSet rs = stmt.executeQuery()) {
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new DatabaseQueryException(e.getMessage(), query + "\n" + resolvedQuery, e);
        } finally {
            runAfter();
        }
    }

    @Override
    public void executeList(String... queries) {
        for (String query : queries) execute(query);
    }

    @Override
    public CompletableFuture<Void> executeAsync(String query, Object... params) {
        return CompletableFuture.runAsync(() -> execute(query, params), executor);
    }

    @Override
    public CompletableFuture<Void> executeListAsync(String... queries) {
        return CompletableFuture.runAsync(() -> executeList(queries), executor);
    }

    @Override
    public <T> CompletableFuture<T> queryAsync(String query, QueryMapper<T> mapper, Object... params) {
        return CompletableFuture.supplyAsync(() -> query(query, mapper, params), executor);
    }

    @Override
    public TableNameResolver getResolver() {
        return resolver;
    }

    @Override
    public Database before(Runnable action) {
        this.before = action;
        return this;
    }

    @Override
    public Database after(Runnable action) {
        this.after = action;
        return this;
    }

    private void runBefore() {
        if (before != null) before.run();
    }

    private void runAfter() {
        if (after != null) after.run();
    }

    private PreparedStatement prepare(Connection con, String query, Object... params) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }
}
