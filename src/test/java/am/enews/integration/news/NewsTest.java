package am.enews.integration.news;

import am.enews.Application;
import am.enews.WebSecurityConfig;
import am.enews.service.account.NewsUser;
import am.enews.service.account.NewsUserDetails;
import am.enews.web.model.news.CreateNewsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

/**
 * Created by vazgent on 3/15/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class,SpringBootWebSecurityConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")


public class NewsTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }


    @Test
    public void canViewNewsPage() {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/news/1", String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue("News title will be in html page title", responseEntity.getBody().contains("<title>News1 title</title>"));
        Assert.assertTrue("News content will be in html page", responseEntity.getBody().contains("News1 content"));
    }

    @Test
    public void canAddNews() {

        CreateNewsModel createNewsModel = new CreateNewsModel();
        createNewsModel.setContent("Content2");
        createNewsModel.setTitle("Title2");
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/api/news", createNewsModel, String.class);

        Assert.assertEquals(HttpStatus.MOVED_PERMANENTLY, responseEntity.getStatusCode());
    }

    @Test
//    @WithUserDetails(value = "existinguser@vuvu.am",userDetailsServiceBeanName = "detailService2")
    @WithMockUser
    public void checkAuthentication() throws Exception {


        mockMvc.perform(
                MockMvcRequestBuilders.get("/nw").accept(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));


    }
}
