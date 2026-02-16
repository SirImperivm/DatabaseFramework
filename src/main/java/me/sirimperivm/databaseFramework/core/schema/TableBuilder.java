package me.sirimperivm.databaseFramework.core.schema;

import me.sirimperivm.databaseFramework.core.DatabaseType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder {

    private final String name;
    private final List<Column> columns = new ArrayList<>();

    public TableBuilder(String name) {
        this.name = name;
    }

    public TableBuilder column(Column column) {
        columns.add(column);
        return this;
    }

    public TableDefinition build(DatabaseType type) {
        String columnSQL = columns.stream()
                .map(c -> c.build(type))
                .collect(Collectors.joining(", "));

        String sql = "CREATE TABLE IF NOT EXISTS " + name + " (" + columnSQL + ");";

        return new TableDefinition(name, sql);
    }
}
