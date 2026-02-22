package me.sirimperivm.databaseFramework.schema;

@SuppressWarnings("unused")
public class ForeignKey {

    private final String column;
    private final String referenceTable;
    private final String referenceColumn;
    private String onDelete;
    private String onUpdate;

    public ForeignKey(String column, String referenceTable, String referenceColumn) {
        this.column = column;
        this.referenceTable = referenceTable;
        this.referenceColumn = referenceColumn;
    }

    public ForeignKey onDelete(String action) {
        this.onDelete = action;
        return this;
    }

    public ForeignKey onUpdate(String action) {
        this.onUpdate = action;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();

        sb.append("FOREIGN KEY (")
                .append(column)
                .append(") REFERENCES ")
                .append(referenceTable)
                .append("(")
                .append(referenceColumn)
                .append(")");

        if (onDelete != null)
            sb.append(" ON DELETE ").append(onDelete);

        if (onUpdate != null)
            sb.append(" ON UPDATE ").append(onUpdate);

        return sb.toString();
    }
}
