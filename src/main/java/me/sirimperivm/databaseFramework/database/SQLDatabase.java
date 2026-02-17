package me.sirimperivm.databaseFramework.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.sirimperivm.databaseFramework.DatabaseQueryException;
import me.sirimperivm.databaseFramework.schema.TableNameResolver;

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
    private final TableNameResolver resolver;

    public SQLDatabase(ExecutorService executor, DatabaseConfig config, TableNameResolver resolver) {
        this.executor = executor;
        this.config = config;
        this.resolver = resolver;
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
    public CompletableFuture<Void> executeUpdate(String query, Object... params) {
        return CompletableFuture.runAsync(() -> {
            String resolvedQuery = resolveQuery(query);

            try (Connection con = getConnection();
                 PreparedStatement stmt = prepare(con, resolvedQuery, params)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseQueryException(e.getMessage(), query + "\n" + resolvedQuery, e);
            }
        }, executor);
    }

    @Override
    public <T> CompletableFuture<T> executeQuery(QueryMapper<T> mapper, String query, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            String resolvedQuery = resolveQuery(query);

            try (Connection con = getConnection();
                 PreparedStatement stmt = prepare(con, resolvedQuery, params);
                 ResultSet rs = stmt.executeQuery()) {
                return mapper.map(rs);
            } catch (SQLException e) {
                throw new DatabaseQueryException(e.getMessage(), query + "\n" + resolvedQuery, e);
            }
        }, executor);
    }

    private PreparedStatement prepare(Connection con, String query, Object... params) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }

    private String resolveQuery(String query) {
        return resolver.resolve(query);
    }
}
