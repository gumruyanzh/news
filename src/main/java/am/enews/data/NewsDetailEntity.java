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

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private NewsDetailEntity news;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public NewsDetailEntity getNews() {
        return news;
    }

    public void setNews(NewsDetailEntity news) {
        this.news = news;
    }
}
