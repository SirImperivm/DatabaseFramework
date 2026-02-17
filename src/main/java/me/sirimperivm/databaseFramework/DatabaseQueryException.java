package me.sirimperivm.databaseFramework;

public class DatabaseQueryException extends RuntimeException {
    public DatabaseQueryException(String message) {
        super(message);
    }

    public DatabaseQueryException(String message, String query, Throwable cause) {
      super(message + "\n" + query, cause);
    }
}
