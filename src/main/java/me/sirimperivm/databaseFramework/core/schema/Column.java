package me.sirimperivm.databaseFramework.core.schema;

import me.sirimperivm.databaseFramework.core.DatabaseType;

public class Column {

    private final String name;
    private final SQLType type;

    private int length = 255;
    private boolean primaryKey;
    private boolean notNull;
    private boolean unique;
    private String defaultValue;
    private boolean autoIncrement;

    public Column(String name, SQLType type) {
        this.name = name;
        this.type = type;
    }

    public Column length(int length) {
        this.length = length;
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
                .append(type.resolve(databaseType, length));

        if (primaryKey) sb.append(" PRIMARY KEY");

        if (autoIncrement) {
            if (databaseType == DatabaseType.MYSQL) sb.append(" AUTO_INCREMENT");
            else {
                if (type == SQLType.INTEGER && primaryKey) sb.append(" AUTOINCREMENT");
            }
        }

        if (notNull) sb.append(" NOT NULL");

        if (unique) sb.append(" UNIQUE");

        if (defaultValue != null) sb.append(" DEFAULT ").append(defaultValue);

        return sb.toString();
    }
}
