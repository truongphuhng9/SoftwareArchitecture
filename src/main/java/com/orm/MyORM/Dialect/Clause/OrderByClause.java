package  com.orm.MyORM.Dialect.Clause;

import  com.orm.MyORM.Dialect.Clause.Clause;

public class OrderByClause extends Clause {
    protected String literal="ORDER BY";

    public OrderByClause(String... columns){
        this.paramsString= String.join(", ", columns);
    }

    @Override
    public String build(String sql) {
        String sqlStatement = String.join(" ", sql, literal, paramsString);
        return buildNext(sqlStatement);
    }
}
