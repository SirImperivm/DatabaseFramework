package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.type.MySQL;
import me.sirimperivm.databaseFramework.database.type.SQLite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseFactory{

    public static Database create(DatabaseConfig config, int threadPoolSize) {
        int nThreads = Math.max(1, threadPoolSize);
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        return create(executor, config);
    }

    public static Database create(ExecutorService executor, DatabaseConfig config) {
        return switch (config.getType()) {
            case MYSQL -> new MySQL(executor, config);
            default -> new SQLite(executor, config);
        };
    }
}