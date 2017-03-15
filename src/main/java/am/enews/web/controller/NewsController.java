package am.enews.web.controller;

import am.enews.service.account.UserService;
import am.enews.service.dto.news.AddNewsDto;
import am.enews.service.dto.news.NewsSimpleDto;
import am.enews.service.news.NewsService;
import am.enews.web.model.news.CreateNewsModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by vazgent on 3/15/2017.
 */
@Controller
public class NewsController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/news")
    public String add(CreateNewsModel request, Principal principal){

        Long userId = userService.findByUsername(principal.getName()).getId();

        AddNewsDto newsDto= new AddNewsDto();
        newsDto.setTitle(request.getTitle());
        newsDto.setContent(request.getContent());
        newsDto.setCreatorId(userId);

        long newsId = newsService.add(newsDto);
        return "redirect:/news/"+newsId;
    }

    @GetMapping("/news/{id}")
    public String get(@PathVariable("id") long id, ModelMap modelMap){


        NewsSimpleDto news = newsService.getById(id);

        modelMap.addAttribute("news",news);
        return "news";
    }

    @ResponseBody
//    @PreAuthorize("authenticated")
    @GetMapping("/nw")
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return "Hello " + authentication;
    }
}
