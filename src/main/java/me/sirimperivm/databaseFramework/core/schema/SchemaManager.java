package me.sirimperivm.databaseFramework.core.schema;

import me.sirimperivm.databaseFramework.core.Database;
import me.sirimperivm.databaseFramework.core.DatabaseType;

import java.util.ArrayList;
import java.util.List;

public class SchemaManager {

    private final Database database;
    private final DatabaseType type;

    private final List<TableDefinition> tables = new ArrayList<>();

    public SchemaManager(Database database, DatabaseType type) {
        this.database = database;
        this.type = type;
    }

    public SchemaManager register(TableBuilder builder) {
        tables.add(builder.build(type));
        return this;
    }

    public void createAll() {
        for (TableDefinition table : tables) {
            database.executeUpdate(table.createStatement());
        }
    }
}
