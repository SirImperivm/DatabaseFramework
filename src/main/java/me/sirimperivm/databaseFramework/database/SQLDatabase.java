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

    public SQLDatabase(ExecutorService executor) {
        this.executor = executor;
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
            try (Connection con = getConnection();
                 PreparedStatement stmt = prepare(con, query, params)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    @Override
    public <T> CompletableFuture<T> executeQuery(QueryMapper<T> mapper, String query, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection con = getConnection();
                 PreparedStatement stmt = prepare(con, query, params);
                 ResultSet rs = stmt.executeQuery()) {
                return mapper.map(rs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
}
