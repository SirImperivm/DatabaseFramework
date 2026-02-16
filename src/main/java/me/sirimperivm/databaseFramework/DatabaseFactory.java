package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.core.Database;
import me.sirimperivm.databaseFramework.core.DatabaseConfig;
import me.sirimperivm.databaseFramework.core.database.MySQLDatabase;
import me.sirimperivm.databaseFramework.core.database.SQLiteDatabase;

import java.util.concurrent.ExecutorService;

public class DatabaseFactory{

    public static Database create(ExecutorService executor, DatabaseConfig config) {
        return switch (config.getType()) {
            case MYSQL -> new MySQLDatabase(executor, config);
            default -> new SQLiteDatabase(executor, config);
        };
    }
}