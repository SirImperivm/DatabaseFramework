package me.sirimperivm.databaseFramework.schema;

import me.sirimperivm.databaseFramework.database.DatabaseConfig;
import me.sirimperivm.databaseFramework.database.DatabaseType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder {

    private final String name;
    private final DatabaseConfig config;

    private final List<Column> columns = new ArrayList<>();

    public TableBuilder(String name, DatabaseConfig config) {
        this.name = name;
        this.config = config;
    }

    public TableBuilder column(Column column) {
        columns.add(column);
        return this;
    }

    public TableDefinition build(DatabaseType type) {
        String columnSQL = columns.stream()
                .map(c -> c.build(type))
                .collect(Collectors.joining(", "));

        String sql = "CREATE TABLE IF NOT EXISTS " + config.getTablePrefix() + name + " (" + columnSQL + ");";

        return new TableDefinition(name, sql);
    }
}
