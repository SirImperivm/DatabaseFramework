package me.sirimperivm.databaseFramework.database;

public class DatabaseQueryException extends DatabaseException {
    public DatabaseQueryException(String message, String query, Throwable cause) {
        super(message, query, cause);
    }
}
