package me.sirimperivm.databaseFramework.core.database;

import com.zaxxer.hikari.HikariConfig;
import me.sirimperivm.databaseFramework.core.DatabaseConfig;
import me.sirimperivm.databaseFramework.core.SQLDatabase;

import java.util.concurrent.ExecutorService;

public class SQLiteDatabase extends SQLDatabase {

    private final DatabaseConfig config;

    public SQLiteDatabase(ExecutorService executor, DatabaseConfig config) {
        super(executor);
        this.config = config;
    }

    @Override
    protected HikariConfig createConfig() {
        HikariConfig hikari = new HikariConfig();

        hikari.setJdbcUrl("jdbc:sqlite:" + config.getFilePath());
        hikari.setMaximumPoolSize(1);

        return hikari;
    }

    @Override
    protected void configureDatabase() {
        executeUpdate("PRAGMA journal_mode=WAL;");
        executeUpdate("PRAGMA synchronous=NORMAL;");
    }
}
