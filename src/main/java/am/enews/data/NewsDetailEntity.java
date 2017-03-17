package am.enews.data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by vazgent on 3/15/2017.
 */
@Entity
@Table(name = "news_detail")
public class NewsDetailEntity {

    @GenericGenerator(name = "newsdetail_newsid", strategy = "foreign", parameters = @Parameter(name = "property", value = "news"))
    @Id
    @GeneratedValue(generator = "newsdetail_newsid")
    @Column(name = "news_id", unique = true, nullable = false)
    private Long newsId;


    private String title;
    private String content;


    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private NewsEntity news;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
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

    public NewsEntity getNews() {
        return news;
    }

    public void setNews(NewsEntity news) {
        this.news = news;
    }
}
