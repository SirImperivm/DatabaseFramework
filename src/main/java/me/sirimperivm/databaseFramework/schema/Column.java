package me.sirimperivm.databaseFramework.schema;

import lombok.Getter;
import me.sirimperivm.databaseFramework.database.DatabaseType;

public class Column {

    @Getter
    private final String name;
    private final DataType type;

    private String[] params;

    private boolean primaryKey;
    private boolean notNull;
    private boolean unique;
    private String defaultValue;
    private boolean autoIncrement;

    public Column(String name, DataType type) {
        this.name = name;
        this.type = type;
        params = new String[0];
    }

    public Column params(String... params) {
        this.params = params;
        return this;
    }

    public Column primaryKey() {
        this.primaryKey = true;
        this.notNull = true;
        return this;
    }

    public Column notNull() {
        this.notNull = true;
        return this;
    }

    public Column unique() {
        this.unique = true;
        return this;
    }

    public Column defaultValue(Object value) {
        this.defaultValue = value.toString();
        return this;
    }

    public Column autoIncrement() {
        this.autoIncrement = true;
        return this;
    }

    public String build(DatabaseType databaseType) {
        StringBuilder sb = new StringBuilder();

        sb.append(name)
                .append(" ")
                .append(type.resolve(databaseType, params));

        if (primaryKey) sb.append(" PRIMARY KEY");

        if (autoIncrement) {
            if (databaseType == DatabaseType.MYSQL) sb.append(" AUTO_INCREMENT");
            else {
                if (type == DataType.INTEGER && primaryKey) sb.append(" AUTOINCREMENT");
            }
        }

        if (notNull) sb.append(" NOT NULL");

        if (unique) sb.append(" UNIQUE");

        if (defaultValue != null) sb.append(" DEFAULT ").append(defaultValue);

        return sb.toString();
    }
}
