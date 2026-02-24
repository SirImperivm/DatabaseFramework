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

    <T> T query(QueryMapper<T> mapper, String query, Object... params);
    <T> CompletableFuture<T> queryAsync(QueryMapper<T> mapper, String query, Object... params);

    TableNameResolver getResolver();

    Database before(Runnable action);
    Database after(Runnable action);
}
