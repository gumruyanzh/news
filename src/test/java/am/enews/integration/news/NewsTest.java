package am.enews.integration.news;

import am.enews.Application;
import am.enews.web.model.news.CreateNewsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static am.enews.integration.HelperConstant.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vazgent on 3/15/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NewsTest {


    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
    }


    @Test
    public void canViewNewsPage() throws Exception {

        mvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF_8))
                .andExpect(content().string(new Contains("Title1")))
                .andExpect(content().string(new Contains("Content1")));
//        ResponseEntity<String> responseEntity =
//                restTemplate.getForEntity("/news/1", String.class);
//
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assert.assertTrue("News title will be in html page title", responseEntity.getBody().contains("<title>News1 title</title>"));
//        Assert.assertTrue("News content will be in html page", responseEntity.getBody().contains("News1 content"));
    }

    @Test
    @WithMockUser(username = EXISTING_USERNAME, password = VALID_USER_PASSWORD)
    public void canAddNews() throws Exception {

        mvc.perform(post("/api/news")
                .param("title", "Title2")
                .param("content", "Content2")
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/news/2"));

        mvc.perform(get("/news/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF_8))
                .andExpect(content().string(new Contains("Title2")))
                .andExpect(content().string(new Contains("Content2")));
    }
}
