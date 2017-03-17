package am.enews.service.dto.news;

import java.util.Date;

/**
 * Created by vazgent on 3/15/2017.
 */
public class NewsSimpleDto {


    private long id;
    private String title;
    private String content;
    private Date created;

    public NewsSimpleDto(long id, String title, String content, Date created) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
