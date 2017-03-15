package am.enews.service.news;

import am.enews.service.dto.news.AddNewsDto;
import am.enews.service.dto.news.NewsSimpleDto;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface NewsService {
    long add(AddNewsDto news);

    NewsSimpleDto getById(long id);
}
