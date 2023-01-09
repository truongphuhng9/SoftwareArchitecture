package com.orm.MyORM.Dialect.Clause;

public class InsertClause extends Clause {
    protected String literal = "INSERT INTO";

    public InsertClause(String table, String... columns) {
        String columnsInString = String.join(", ", columns);
        this.paramsString = table + " (" + columnsInString + ")";
    }
    @Override
    public String build(String sql) {
        String newSql = String.join(" ", sql, this.literal, paramsString);
        return buildNext(newSql);
    }
}
