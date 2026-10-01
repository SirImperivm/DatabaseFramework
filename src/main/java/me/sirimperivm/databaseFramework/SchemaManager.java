package me.sirimperivm.databaseFramework;

import me.sirimperivm.databaseFramework.database.Database;
import me.sirimperivm.databaseFramework.schema.TableDefinition;

import java.util.*;

@SuppressWarnings("unused")
public class SchemaManager {

    private final Database database;
    private final List<TableDefinition> tables = new LinkedList<>();

    public SchemaManager(Database database) {
        this.database = database;
    }

    public SchemaManager register(TableDefinition... definitions) {
        if (definitions.length == 0) return this;

        tables.addAll(Arrays.asList(definitions));
        return this;
    }

    public void createAll() {
        for (TableDefinition table : tables) {
            try {
                database.execute(table.createTableSQL());
                for (String indexSQL : table.indexSQL()) {
                    try {
                        database.execute(indexSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<TableDefinition> getTables() {
        return Collections.unmodifiableList(tables);
    }
}
