package me.sirimperivm.databaseFramework.core;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryMapper<T> {

    T map(ResultSet rs) throws SQLException;
}
