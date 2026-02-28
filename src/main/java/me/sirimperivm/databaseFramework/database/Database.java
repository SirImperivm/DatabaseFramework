package me.sirimperivm.databaseFramework.database;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public interface Database {

    void connect();

    void disconnect();

    Connection getConnection();

    void execute(String query, Object... params);
    CompletableFuture<Void> executeAsync(String query, Object... params);

    void executeList(String... queries);
    CompletableFuture<Void>executeListAsync(String... queries);

    <T> T query(String query, QueryMapper<T> mapper, Object... params);
    <T> CompletableFuture<T> queryAsync(String query, QueryMapper<T> mapper, Object... params);

    TableNameResolver getResolver();
}
