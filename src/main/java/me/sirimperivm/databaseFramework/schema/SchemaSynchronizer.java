package me.sirimperivm.databaseFramework.schema;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.database.DatabaseType;

import java.util.HashSet;
import java.util.Set;

public class SchemaSynchronizer {

    private final Database database;
    private final DatabaseType type;

    public SchemaSynchronizer(Database database, DatabaseType type) {
        this.database = database;
        this.type = type;
    }

    public void synchronize(TableDefinition table) {
        database.queryAsync(rs -> {
            Set<String> existingColumns = new HashSet<>();

            while (rs.next()) {
                existingColumns.add(rs.getString(1));
            }

            for (Column column : table.columns()) {
                if (!existingColumns.contains(column.getName())) {

                    String alter = "ALTER TABLE " +
                            table.name() +
                            " ADD COLUMN " +
                            column.build(type) +
                            ";";

                    database.executeAsync(alter);
                }
            }

            return null;
            }, type == DatabaseType.MYSQL
                    ? "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?"
                    : "PRAGMA table_info(" + table.name() + ");",
            type == DatabaseType.MYSQL ? table.name() : null
        );
    }
}
