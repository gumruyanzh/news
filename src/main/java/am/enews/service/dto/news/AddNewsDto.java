package am.enews.service.dto.news;

/**
 * Created by vazgent on 3/15/2017.
 */
public class AddNewsDto {
    private long creatorId;
    private String title;
    private String content;

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
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
}
