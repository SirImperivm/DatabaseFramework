package me.sirimperivm.databaseFramework.database;

public class DatabaseException extends RuntimeException {
  public DatabaseException(String message, String query, Throwable cause) {
    super(message + "\n" + query, cause);
  }
}
