package me.sirimperivm.databaseFramework.database;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Database {

    void connect();

    void disconnect();

    Connection getConnection();

    void execute(String query, Object... params);
    CompletableFuture<Void> executeAsync(String query, Object... params);

    void executeList(List<String> queries, Object... params);
    CompletableFuture<Void>executeListAsync(List<String> queries, Object... params);

    <T> T query(QueryMapper<T> mapper, String query, Object... params);
    <T> CompletableFuture<T> queryAsync(QueryMapper<T> mapper, String query, Object... params);
}
