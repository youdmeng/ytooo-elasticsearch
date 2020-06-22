package ml.ytooo.el.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 配置索引type mapping
 * Created by Youdmeng on 2019/7/3 0003.
 */
@Data
public class MappingSetter {

    @NotBlank
    private String index;

    @NotBlank
    private String type;

    @NotBlank
    private String mappingString;
}
