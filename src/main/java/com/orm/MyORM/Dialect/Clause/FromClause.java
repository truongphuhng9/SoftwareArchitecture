package  com.orm.MyORM.Dialect.Clause;

import  com.orm.MyORM.Dialect.Value.FieldValue;

public class FromClause extends Clause {
    protected String literal = "FROM";
    public FromClause(String tableName) {
        this.paramsString = tableName;
    }

    public FromClause(FieldValue value) {
        this.paramsString = value.toSql();
    }

    @Override
    public String build(String sql) {
        String newSql = String.join(" ", sql, this.literal, paramsString);
        return buildNext(newSql);
    }
}
