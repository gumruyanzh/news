package am.enews.integration.news;

import am.enews.Application;
import am.enews.web.model.news.CreateNewsModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vazgent on 3/15/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NewsTest {
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void canViewNewsPage() {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/news/1", String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue("News title will be in html page title",responseEntity.getBody().contains("<title>News1 title</title>"));
        Assert.assertTrue("News content will be in html page",responseEntity.getBody().contains("News1 content"));
    }

    @Test
    @WithMockUser
    public void canAddNews() {

        CreateNewsModel createNewsModel= new CreateNewsModel();
        createNewsModel.setContent("Content2");
        createNewsModel.setTitle("Title2");
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/api/news",createNewsModel, String.class);

        Assert.assertEquals(HttpStatus.MOVED_PERMANENTLY, responseEntity.getStatusCode());
    }
}
