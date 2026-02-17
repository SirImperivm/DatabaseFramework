package me.sirimperivm.databaseFramework.database.type;

import com.zaxxer.hikari.HikariConfig;
import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.SQLDatabase;
import me.sirimperivm.databaseFramework.schema.TableNameResolver;

import java.util.concurrent.ExecutorService;

public class SQLite extends SQLDatabase {

    public SQLite(ExecutorService executor, DatabaseConfig config, TableNameResolver resolver) {
        super(executor, config, resolver);
    }

    @Override
    protected HikariConfig createConfig() {
        HikariConfig hikari = new HikariConfig();

        hikari.setJdbcUrl("jdbc:sqlite:" + config.getFilePath());

        hikari.setMaximumPoolSize(1);
        hikari.setPoolName(config.getPoolName());

        return hikari;
    }

    @Override
    protected void configureDatabase() {
        executeUpdate("PRAGMA journal_mode=WAL;");
        executeUpdate("PRAGMA synchronous=NORMAL;");
    }
}
