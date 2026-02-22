package me.sirimperivm.databaseFramework.schema;

@SuppressWarnings("unused")
public class Index {

    private final String name;
    private final String column;
    private boolean unique;

    public Index(String name, String column) {
        this.name = name;
        this.column = column;
    }

    public Index unique() {
        this.unique = true;
        return this;
    }

    public String build(String tableName) {
        return "CREATE " +
                (unique ? "UNIQUE " : "") +
                "INDEX IF NOT EXISTS " +
                name + " ON " +
                tableName + "(" + column + ");";
    }
}
