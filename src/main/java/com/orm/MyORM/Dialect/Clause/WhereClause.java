package  com.orm.MyORM.Dialect.Clause;

import  com.orm.MyORM.Dialect.Condition.Condition;

public class WhereClause extends Clause {
    protected String literal = "WHERE";
    private Condition condition;
    private String conditionString = "";

    public WhereClause(Condition condition) {
        this.condition = condition;
    }

    public WhereClause(String condition) {
        this.conditionString = condition;
    }

    @Override
    public String build(String sql) {
        String newSql;
        if (conditionString != "") {
            newSql = String.join(" ", sql, literal, conditionString);
        } else {
            newSql = String.join(" ", sql, literal, condition.buildSql());
        }

        return buildNext(newSql);
    }
}
