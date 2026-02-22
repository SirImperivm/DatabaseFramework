package me.sirimperivm.databaseFramework.schema;

import me.sirimperivm.databaseFramework.database.DatabaseType;

public enum DataType {

    BIT,
    TINYINT,
    SHORT,
    INT,
    INTEGER,
    LONG,
    BIGINT,
    BOOLEAN,
    FLOAT,
    DOUBLE,
    DECIMAL,
    CHAR,
    VARCHAR,
    BINARY,
    VARBINARY,
    TINYBLOB,
    TINYTEXT,
    TEXT,
    BLOB,
    MEDIUMTEXT,
    MEDIUMBLOB,
    LONGTEXT,
    LONGBLOB,
    ENUM,
    SET,
    DATE,
    DATETIME,
    TIMESTAMP,
    TIME,
    YEAR,
    ;

    public String resolve(DatabaseType type, String... params) {
        return switch (this) {
            case BIT -> switch (type) {
                case MYSQL -> "BIT" + resolveParams(this, params);
                case SQLITE -> "INTEGER";
            };
            case TINYINT -> switch (type) {
                case MYSQL -> "TINYINT" + resolveParams(this, params);
                case SQLITE -> "INTEGER";
            };
            case SHORT -> switch (type) {
                case MYSQL -> "SMALLINT" + resolveParams(this, params);
                case SQLITE -> "INTEGER";
            };
            case INT, INTEGER -> switch (type) {
                case MYSQL -> "INT" + resolveParams(this, params);
                case SQLITE -> "INTEGER";
            };
            case LONG, BIGINT -> switch (type) {
                case MYSQL -> "BIGINT" + resolveParams(this, params);
                case SQLITE -> "INTEGER";
            };
            case BOOLEAN -> switch (type) {
                case MYSQL -> "BOOLEAN";
                case SQLITE -> "NUMERIC";
            };
            case FLOAT -> switch (type) {
                case MYSQL -> "FLOAT" + resolveParams(this, params);
                case SQLITE -> "REAL";
            };
            case DOUBLE -> switch (type) {
                case MYSQL -> "DOUBLE" + resolveParams(this, params);
                case SQLITE -> "REAL";
            };
            case DECIMAL -> switch (type) {
                case MYSQL -> "DECIMAL" + resolveParams(this, params);
                case SQLITE -> "NUMERIC";
            };
            case CHAR -> switch (type) {
                case MYSQL -> "CHAR" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case VARCHAR -> switch (type) {
                case MYSQL -> "VARCHAR" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case BINARY -> switch (type) {
                case MYSQL -> "BINARY" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case VARBINARY -> switch (type) {
                case MYSQL -> "VARBINARY" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case TINYBLOB -> switch (type) {
                case MYSQL -> "TINYBLOB" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case TINYTEXT -> switch (type) {
                case MYSQL -> "TINYTEXT" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case TEXT -> switch (type) {
                case MYSQL -> "TEXT" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case BLOB -> switch (type) {
                case MYSQL -> "BLOB" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case MEDIUMTEXT -> switch (type) {
                case MYSQL -> "MEDIUMTEXT" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case MEDIUMBLOB -> switch (type) {
                case MYSQL -> "MEDIUMBLOB" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case LONGTEXT -> switch (type) {
                case MYSQL -> "LONGTEXT" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case LONGBLOB -> switch (type) {
                case MYSQL -> "LONGBLOB" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case ENUM -> switch (type) {
                case MYSQL -> "ENUM" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case SET -> switch (type) {
                case MYSQL -> "SET" + resolveParams(this, params);
                case SQLITE -> "TEXT";
            };
            case DATE -> switch (type) {
                case MYSQL -> "DATE";
                case SQLITE -> "NUMERIC";
            };
            case DATETIME -> switch (type) {
                case MYSQL -> "DATETIME" + resolveParams(this, params);
                case SQLITE -> "NUMERIC";
            };
            case TIMESTAMP -> switch (type) {
                case MYSQL -> "TIMESTAMP" + resolveParams(this, params);
                case SQLITE -> "NUMERIC";
            };
            case TIME -> switch (type) {
                case MYSQL -> "TIME" + resolveParams(this, params);
                case SQLITE -> "NUMERIC";
            };
            case YEAR -> switch (type) {
                case MYSQL -> "YEAR";
                case SQLITE -> "NUMERIC";
            };
        };
    }

    private String resolveParams(DataType dataType, String... params) {
        boolean paramsEmpty = params == null || params.length == 0;
        switch (dataType) {
            case DOUBLE, DECIMAL, DATE, DATETIME, TIMESTAMP, TIME, YEAR -> {
                if (dataType == DECIMAL) {
                    boolean validParamsCount = !paramsEmpty && params.length <= 2;
                    if (!validParamsCount) return "";
                    return "(" + params[0] + "," + params[1] + ")";
                }
                if (paramsEmpty) return "";
                return "(" + params[0] + ")";
            }
            case TINYTEXT, MEDIUMTEXT, LONGTEXT,
                 TINYBLOB, MEDIUMBLOB, LONGBLOB -> {
                return "";
            }
            case CHAR, BINARY -> {
                if (paramsEmpty) return "(11)";
                return "(" + params[0] + ")";
            }
            case VARCHAR, VARBINARY -> {
                if (paramsEmpty) return "(255)";
                return "(" + params[0] + ")";
            }
            case ENUM, SET -> {
                if (paramsEmpty) return "(\"\")";
                String joinedParams = String.join(",", params);
                return "(" + joinedParams + ")";
            }
            default -> {
                return paramsEmpty || params.length > 1 ? "" : "(" + params[0] + ")";
            }
        }
    }
}
