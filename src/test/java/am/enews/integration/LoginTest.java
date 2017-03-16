package am.enews.integration;

import am.enews.Application;
import am.enews.WebSecurityConfig;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static am.enews.integration.HelperConstant.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * Created by vazgent on 3/15/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoginTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
    }


    @Test
    public void canOpenLoginPage() throws Exception {


        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("login"));
    }


    @Test
    public void loginWithValidUserWillSuccess() throws Exception {


        mvc.perform(formLogin().loginProcessingUrl("/login").user(EXISTING_USERNAME).password(VALID_USER_PASSWORD)).
                andExpect(authenticated().withRoles("USER"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));


    }

    @Test
    public void loginWithInvalidPasswordWillRedirectLoginErrorPage() throws Exception {

        ResultActions resultActions = mvc
                .perform(formLogin().loginProcessingUrl("/login").user(EXISTING_USERNAME).password(INVALID_USER_PASSWORD))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login-error"))
                .andExpect(status().isFound());
        System.out.printf("res");


    }

    @Test
    public void loginWithInvalidPasswordAndInvalidUserNameWillRedirectLoginErrorPage() throws Exception {


        mvc.perform(formLogin().loginProcessingUrl("/login").user(NONE_EXISTING_USERNAME).password(INVALID_USER_PASSWORD))
                .andExpect(unauthenticated());
    }

    @Test
    public void canOpenLoginErrorPage() {

//        ResponseEntity<String> responseEntity =
//                restTemplate.getForEntity("/login-error", String.class);
//
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assert.assertTrue(responseEntity.getBody().contains("Սխալ հասցե կամ կամ գաղտանաբառ"));
    }

}
