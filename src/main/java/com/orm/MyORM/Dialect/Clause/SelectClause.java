package com.orm.MyORM.Dialect.Clause;

public class SelectClause extends Clause {
    protected String literal = "SELECT";
    public SelectClause() {
        this.paramsString = "*";
    }

    public SelectClause(String... params) {
        this.paramsString = String.join(",", params);
    }

    @Override
    public String build(String sql) {
        String newSql = String.join(" ", sql, this.literal, paramsString);
        return buildNext(newSql);
    }
}

// select *
// select Col1, Col2,...