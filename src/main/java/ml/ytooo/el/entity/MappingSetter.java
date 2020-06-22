package ml.ytooo.el.entity;

import javax.validation.constraints.NotBlank;

/**
 * 配置索引type mapping
 * Created by Youdmeng on 2019/7/3 0003.
 */
public class MappingSetter {

    @NotBlank
    private String index;
    @NotBlank
    private String type;
    @NotBlank
    private String mappingString;

    /**
     * 属性get
     */
    public String getIndex() {
        return index;
    }

    /**
     * 属性set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * 属性get
     */
    public String getType() {
        return type;
    }

    /**
     * 属性set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 属性get
     */
    public String getMappingString() {
        return mappingString;
    }

    /**
     * 属性set
     */
    public void setMappingString(String mappingString) {
        this.mappingString = mappingString;
    }
}
