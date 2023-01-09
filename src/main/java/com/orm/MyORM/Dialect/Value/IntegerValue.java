package  com.orm.MyORM.Dialect.Value;

public class IntegerValue implements Value {
    private int value;
    public IntegerValue(int value) {
        this.value = value;
    }
    @Override
    public String toSql() {
        return Integer.toString(value);
    }
}
