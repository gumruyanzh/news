package am.enews.service.news;

import am.enews.data.NewsDetailEntity;
import am.enews.data.NewsEntity;
import am.enews.data.UserEntity;
import am.enews.data.repository.NewsRepository;
import am.enews.data.repository.UserRepository;
import am.enews.service.dto.news.AddNewsDto;
import am.enews.service.dto.news.NewsSimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vazgent on 3/15/2017.
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public long add(AddNewsDto news) {
        NewsEntity newsEntity = new NewsEntity();

        NewsDetailEntity detail = new NewsDetailEntity();
        detail.setTitle(news.getTitle());
        detail.setContent(news.getContent());

        UserEntity creator = userRepository.getOne(news.getCreatorId());

        newsEntity.setUser(creator);
        newsEntity.setDetail(detail);

        newsRepository.save(newsEntity);

        return newsEntity.getId();

    }

    @Override
    public NewsSimpleDto getById(long id) {
        NewsEntity news = newsRepository.getOne(id);

        NewsSimpleDto newsSimpleDto = new NewsSimpleDto();
        newsSimpleDto.setId(news.getId());
        newsSimpleDto.setTitle(news.getDetail().getTitle());
        newsSimpleDto.setContent(news.getDetail().getContent());

        return newsSimpleDto;


    }
}
