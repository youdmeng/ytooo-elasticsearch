package ml.ytooo.el.entity;

/**
 * el 查询条件
 * Created by Youdmeng on 2019/6/27 0027.
 **/
public class Criteria {
    private String fieldName;
    private Object fieldValue;

    public Criteria() {
    }

    public Criteria(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
