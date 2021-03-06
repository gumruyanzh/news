package am.enews.service.news;

import am.enews.data.NewsDetailEntity;
import am.enews.data.NewsEntity;
import am.enews.data.UserEntity;
import am.enews.data.repository.NewsRepository;
import am.enews.data.repository.UserRepository;
import am.enews.service.dto.news.AddNewsDto;
import am.enews.service.dto.news.NewsSimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        NewsDetailEntity detailEntity = new NewsDetailEntity();
        detailEntity.setTitle(news.getTitle());
        detailEntity.setContent(news.getContent());

        UserEntity creator = userRepository.getOne(news.getCreatorId());

        detailEntity.setNews(newsEntity);
        newsEntity.setUser(creator);
        newsEntity.setDetail(detailEntity);

        newsRepository.save(newsEntity);

        return newsEntity.getId();

    }

    @Override
    public NewsSimpleDto getById(long id) {
        NewsEntity news = newsRepository.getOne(id);
        NewsSimpleDto newsSimpleDto = new NewsSimpleDto(news.getId(), news.getDetail().getTitle(), news.getDetail().getContent(), news.getDetail().getCreated());
        return newsSimpleDto;

    }

    @Override
    public List<NewsSimpleDto> getLastNews(PageRequest pageRequest) {
        Page<NewsEntity> newsList = newsRepository.findAll(pageRequest);
        return newsList.getContent().stream().map(n -> new NewsSimpleDto(n.getId(), n.getDetail().getTitle(), n.getDetail().getContent(), n.getDetail().getCreated())).collect(Collectors.toList());

    }
}
