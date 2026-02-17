package me.sirimperivm.databaseFramework.schema;

import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.DatabaseType;

public class TableNameResolver {

    private final DatabaseConfig config;

    public TableNameResolver(DatabaseConfig config) {
        this.config = config;
    }

    public String resolve(String tableName) {
        if (config.getType() == DatabaseType.MYSQL) {
            return tableName.replaceAll("\\{(.+?)\\}", config.getTablePrefix() + "$1");
        } else {
            return tableName.replaceAll("\\{(.+?)\\}", "$1");
        }
    }
}
