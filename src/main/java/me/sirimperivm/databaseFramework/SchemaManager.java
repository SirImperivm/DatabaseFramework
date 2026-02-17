package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.database.DatabaseType;
import me.sirimperivm.databaseFramework.schema.Index;
import me.sirimperivm.databaseFramework.schema.TableBuilder;
import me.sirimperivm.databaseFramework.schema.TableDefinition;

import java.util.ArrayList;
import java.util.List;

public class SchemaManager {

    private final Database database;
    private final List<TableDefinition> tables = new ArrayList<>();

    public SchemaManager(Database database) {
        this.database = database;
    }

    public SchemaManager register(TableDefinition definition) {
        tables.add(definition);
        return this;
    }

    public void createAll() {
        for (TableDefinition table : tables) {
            database.executeUpdate(table.createTableSQL());
            for (String indexSQL : table.indexSQL()) {
                database.executeUpdate(indexSQL);
            }
        }
    }
}
