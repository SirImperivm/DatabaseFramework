package me.sirimperivm.databaseFramework.schema;

import me.sirimperivm.databaseFramework.database.DatabaseType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder {

    private final String name;
    private final List<Column> columns = new ArrayList<>();
    private final List<Index> indexes = new ArrayList<>();
    private final List<ForeignKey> foreignKeys = new ArrayList<>();

    public TableBuilder(String name) {
        this.name = name;
    }

    public TableBuilder column(Column column) {
        columns.add(column);
        return this;
    }

    public TableBuilder index(Index index) {
        indexes.add(index);
        return this;
    }

    public TableBuilder foreignKey(ForeignKey fk) {
        foreignKeys.add(fk);
        return this;
    }

    public TableDefinition build(DatabaseType type, TableNameResolver resolver) {
        String tableNameResolved = resolver.resolve("{" + name + "}");
        StringBuilder create = new StringBuilder();
        create.append("CREATE TABLE IF NOT EXISTS ")
                .append(tableNameResolved)
                .append(" (");

        List<String> definitions = new ArrayList<>();

        for (Column column : columns) {
            definitions.add(column.build(type));
        }

        for (ForeignKey fk : foreignKeys) {
            definitions.add(fk.build());
        }

        create.append(String.join(", ", definitions));
        create.append(")");

        if (type == DatabaseType.MYSQL) {
            create.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
        }

        create.append(";");

        List<String> indexStatements = indexes.stream()
                .map(i -> i.build(tableNameResolved))
                .collect(Collectors.toList());

        return new TableDefinition(
                tableNameResolved,
                create.toString(),
                indexStatements,
                columns
        );
    }
}
