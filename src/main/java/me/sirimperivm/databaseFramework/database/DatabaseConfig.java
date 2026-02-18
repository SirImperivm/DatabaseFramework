package me.sirimperivm.databaseFramework.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DatabaseConfig {

    private final DatabaseType type;
    private final String poolName;

    // SQLite
    private String filePath;

    // MySQL
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String tablePrefix = "";

    // Pool Options (MySQL only)
    private int maxPoolSize = 5;
    private int minimumIdle = 5;
    private int connectionTimeout = 10000;
    private int idleTimeout = 10000;
    private int maxLifeTime = 1800000;
    private int keepAliveTime = 300000;

    // DataSourceProperty (MySQL only)
    private String cachePrepStmts = "true";
    private String prepStmtCacheSize = "250";
    private String prepStmtCacheSqlLimit = "2048";
    private String useServerPrepStmts = "true";
    private String useUnicode = "true";
    private String characterEncoding = "utf8";
    private String useSSL = "false";
}
