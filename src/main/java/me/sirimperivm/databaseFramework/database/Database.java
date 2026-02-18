package me.sirimperivm.databaseFramework.database;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void connect();

    void disconnect();

    Connection getConnection();

    void executeUpdate(String query, Object... params);
    CompletableFuture<Void> executeUpdateAsync(String query, Object... params);

    <T> T executeQuery(QueryMapper<T> mapper, String query, Object... params);
    <T> CompletableFuture<T> executeQueryAsync(QueryMapper<T> mapper, String query, Object... params);
}
