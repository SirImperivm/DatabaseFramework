package me.sirimperivm.databaseFramework.database.type;

import com.zaxxer.hikari.HikariConfig;
import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.SQLDatabase;

import java.util.concurrent.ExecutorService;

public class SQLite extends SQLDatabase {

    public SQLite(ExecutorService executor, DatabaseConfig config) {
        super(executor, config);
    }

    @Override
    protected HikariConfig createConfig() {
        HikariConfig hikari = new HikariConfig();

        hikari.setJdbcUrl("jdbc:sqlite:" + config.getFilePath());

        hikari.setMaximumPoolSize(1);
        hikari.setPoolName(config.getPoolName());

        return hikari;
    }
}
