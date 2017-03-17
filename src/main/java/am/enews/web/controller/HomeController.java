package am.enews.web.controller;

import am.enews.service.account.UserService;
import am.enews.service.dto.news.NewsSimpleDto;
import am.enews.service.news.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by vazgent on 3/17/2017.
 */
@Controller
public class HomeController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String get(ModelMap modelMap){
        List<NewsSimpleDto> lastNews = newsService.getLastNews(new PageRequest(0, 3));
        modelMap.addAttribute("lastNews",lastNews);
        return "index";
    }
}
