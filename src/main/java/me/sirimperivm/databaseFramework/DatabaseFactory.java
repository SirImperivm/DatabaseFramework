package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.type.MySQL;
import me.sirimperivm.databaseFramework.database.type.SQLite;
import me.sirimperivm.databaseFramework.schema.TableNameResolver;

import java.util.concurrent.ExecutorService;

public class DatabaseFactory{

    public static Database create(ExecutorService executor, DatabaseConfig config, TableNameResolver resolver) {
        return switch (config.getType()) {
            case MYSQL -> new MySQL(executor, config, resolver);
            default -> new SQLite(executor, config, resolver);
        };
    }
}