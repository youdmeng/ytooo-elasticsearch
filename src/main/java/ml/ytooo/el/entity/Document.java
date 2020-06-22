package ml.ytooo.el.entity;

import java.util.Arrays;
import java.util.Date;

import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * 测试bean
 * Created by Youdmeng on 2019/6/27 0027.
 */
@Data
public class Document implements Cloneable {
    @JestId
    private long id;

    private String title;

    private String author;

    private String[] tags;

    private Date publishTime;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", publishTime=" + publishTime +
                '}';
    }
}
