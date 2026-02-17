package me.sirimperivm.databaseFramework.database.type;

import com.zaxxer.hikari.HikariConfig;
import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.SQLDatabase;
import me.sirimperivm.databaseFramework.schema.TableNameResolver;

import java.util.concurrent.ExecutorService;

public class MySQL extends SQLDatabase {

    public MySQL(ExecutorService executor, DatabaseConfig config, TableNameResolver resolver) {
        super(executor, config, resolver);
    }

    @Override
    protected HikariConfig createConfig() {
        HikariConfig hikari = new HikariConfig();

        hikari.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s%s"
                , config.getHost()
                , config.getPort()
                , config.getDatabase()
                , config.getOptions()
        ));

        hikari.setUsername(config.getUsername());
        hikari.setPassword(config.getPassword());

        hikari.setMaximumPoolSize(config.getPoolSize());
        hikari.setMinimumIdle(2);
        hikari.setConnectionTimeout(10000);
        hikari.setLeakDetectionThreshold(60000);

        return hikari;
    }
}
