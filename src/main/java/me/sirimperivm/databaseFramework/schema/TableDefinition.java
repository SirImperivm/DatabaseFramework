package me.sirimperivm.databaseFramework.schema;

import java.util.List;

public class TableDefinition {

    private final String name;
    private final String createTableSQL;
    private final List<String> indexSQL;
    private final List<Column> columns;

    public TableDefinition(String name,
                           String createTableSQL,
                           List<String> indexSQL,
                           List<Column> columns) {
        this.name = name;
        this.createTableSQL = createTableSQL;
        this.indexSQL = indexSQL;
        this.columns = columns;
    }

    public String name() { return name; }
    public String createTableSQL() { return createTableSQL; }
    public List<String> indexSQL() { return indexSQL; }
    public List<Column> columns() { return columns; }
}
