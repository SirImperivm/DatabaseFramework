package me.sirimperivm.databaseFramework.core.schema;

import me.sirimperivm.databaseFramework.core.DatabaseType;

public enum SQLType {

    STRING,
    INTEGER,
    LONG,
    DOUBLE,
    BOOLEAN,
    TEXT,
    ;

    public String resolve(DatabaseType type, int length) {
        return switch (this) {
            case STRING -> switch (type) {
                case MYSQL -> "VARCHAR(" + length + ")";
                case SQLITE -> "TEXT";
            };
            case INTEGER -> switch (type) {
                case MYSQL -> "INT";
                case SQLITE -> "INTEGER";
            };
            case LONG -> switch (type) {
                case MYSQL -> "BIGINT";
                case SQLITE -> "INTEGER";
            };
            case DOUBLE -> switch (type) {
                case MYSQL -> "DOUBLE";
                case SQLITE -> "REAL";
            };
            case BOOLEAN -> switch (type) {
                case MYSQL -> "BOOLEAN";
                case SQLITE -> "INTEGER";
            };
            case TEXT -> "TEXT";
        };
    }
}
