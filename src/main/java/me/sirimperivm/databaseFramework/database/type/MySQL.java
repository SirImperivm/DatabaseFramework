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

        hikari.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s"
                , config.getHost()
                , config.getPort()
                , config.getDatabase()
        ));

        hikari.setUsername(config.getUsername());
        hikari.setPassword(config.getPassword());

        hikari.setPoolName(config.getPoolName());
        hikari.setMaximumPoolSize(config.getMaxPoolSize());
        hikari.setMinimumIdle(config.getMinimumIdle());
        hikari.setConnectionTimeout(config.getConnectionTimeout());
        hikari.setIdleTimeout(config.getIdleTimeout());
        hikari.setMaxLifetime(config.getMaxLifeTime());
        hikari.setKeepaliveTime(config.getKeepAliveTime());

        hikari.addDataSourceProperty("cachePrepStmts", config.getCachePrepStmts());
        hikari.addDataSourceProperty("prepStmtCacheSize", config.getPrepStmtCacheSize());
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", config.getPrepStmtCacheSqlLimit());
        hikari.addDataSourceProperty("useServerPrepStmts", config.getUseServerPrepStmts());
        hikari.addDataSourceProperty("useUnicode", config.getUseUnicode());
        hikari.addDataSourceProperty("characterEncoding", config.getCharacterEncoding());
        hikari.addDataSourceProperty("useSSL", config.getUseSSL());

        return hikari;
    }
}
