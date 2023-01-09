package  com.orm.MyORM.Dialect.Clause;

public class ValuesClause extends Clause {
    protected String literal = "VALUES";

    public ValuesClause(String... columns) {
        this.paramsString = "(" + String.join(", ", columns) + ")";
    }
    @Override
    public String build(String sql) {
        return buildNext(sql + " " + literal + " " + paramsString);
    }
}
