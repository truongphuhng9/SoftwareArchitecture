package  com.orm.MyORM.Dialect.Clause;

import  com.orm.MyORM.Dialect.Clause.Clause;

public class GroupByClause extends Clause {

    protected String literal = "GROUP BY";

    public GroupByClause(String... columns) {
        String columnsInString = String.join(", ", columns);
        this.paramsString =  columnsInString;
    }

    @Override
    public String build(String sql) {
        String sqlStatement = String.join(" ", sql, literal, paramsString);
        return buildNext(sqlStatement);
    }
}
