package me.sirimperivm.databaseFramework.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DatabaseConfig {

    private final DatabaseType type;

    // MySQL
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String tableName;
    private String options = "";

    // SQLite
    private String filePath;

    private int poolSize = 10;
}
