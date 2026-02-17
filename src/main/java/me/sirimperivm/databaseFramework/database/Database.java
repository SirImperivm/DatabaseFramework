package me.sirimperivm.databaseFramework.database;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void connect();

    void disconnect();

    Connection getConnection();

    CompletableFuture<Void> executeUpdate(String query, Object... params);

    <T> CompletableFuture<T> executeQuery(QueryMapper<T> mappe, String query, Object... params);
}
