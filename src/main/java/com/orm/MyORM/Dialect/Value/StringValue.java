package  com.orm.MyORM.Dialect.Value;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String toSql() {
        return "'" + value + "'";
    }
}
