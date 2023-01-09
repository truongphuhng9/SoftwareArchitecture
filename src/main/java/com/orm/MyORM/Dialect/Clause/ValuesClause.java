package com.orm.MyORM.Dialect.Clause;

import java.util.Arrays;
import java.util.List;

public class ValuesClause extends Clause {
    protected String literal = "VALUES";

    public ValuesClause(String... columns) {
        this.paramsString = "(" + String.join(", ", Arrays.stream(columns).map(x->"'"+x+"'").toList()) + ")";
    }
    @Override
    public String build(String sql) {
        return buildNext(sql + " " + literal + " " + paramsString);
    }
}
