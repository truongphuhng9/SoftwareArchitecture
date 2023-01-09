package com.orm.MyORM.Dialect.Condition;

public class AndCondition implements Condition{
    Condition left;
    Condition right;

    public AndCondition(Condition left, Condition right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String buildSql() {
        return left.buildSql() + " AND " + right.buildSql();
    }
}
