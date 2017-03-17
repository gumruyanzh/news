package am.enews.service.news;

import am.enews.service.dto.news.AddNewsDto;
import am.enews.service.dto.news.NewsSimpleDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface NewsService {
    long add(AddNewsDto news);

    NewsSimpleDto getById(long id);

    List<NewsSimpleDto> getLastNews(PageRequest pageRequest );
}
