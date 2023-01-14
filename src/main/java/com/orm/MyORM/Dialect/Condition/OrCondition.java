package  com.orm.MyORM.Dialect.Condition;

public class OrCondition implements Condition{
    Condition left;
    Condition right;

    public OrCondition(Condition left, Condition right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String buildSql() {
        return left.buildSql() + " OR " + right.buildSql();
    }
}
