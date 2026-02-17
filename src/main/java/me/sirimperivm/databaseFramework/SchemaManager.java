package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.database.DatabaseType;
import me.sirimperivm.databaseFramework.schema.TableBuilder;
import me.sirimperivm.databaseFramework.schema.TableDefinition;

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
