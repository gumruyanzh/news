package am.enews.data;

import javax.persistence.*;

/**
 * Created by vazgent on 3/15/2017.
 */
@Entity
@Table(name = "news")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "news", cascade = CascadeType.ALL)
    private NewsDetailEntity detail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsDetailEntity getDetail() {
        return detail;
    }

    public void setDetail(NewsDetailEntity detail) {
        this.detail = detail;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
