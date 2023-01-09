package com.orm.MyORM.Dialect.Clause;

import com.orm.MyORM.Dialect.Value.FieldValue;

public class DeleteClause extends Clause{
    protected String literal = "DELETE FROM";
    public DeleteClause(String tableName) {
        this.paramsString = tableName;
    }

    @Override
    public String build(String sql) {
        String newSql = String.join(" ", sql, this.literal, paramsString);
        return buildNext(newSql);
    }
}
