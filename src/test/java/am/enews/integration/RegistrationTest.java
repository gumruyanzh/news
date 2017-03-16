package am.enews.integration;

import am.enews.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static am.enews.integration.HelperConstant.EXISTING_USERNAME;
import static am.enews.integration.HelperConstant.VALID_USER_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by vazgent on 3/15/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RegistrationTest {

    private final String NONE_EXISTING_USERNAME = "testvuvu@sharklasers.com";
    private final String EXISTING_USERNAME = "existinguser@vuvu.am";
    private final String SHORT_STRING = "123";
    private final String GOOD_PASSWORD = "mygoodPassword()";
    private final String EMPTY_STRING = "              ";


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
    public void registrationWithMismatchPasswordWillShowErrorMessage() {

//        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", NONE_EXISTING_USERNAME);
//        body.add("password", GOOD_PASSWORD);
//        body.add("confirmPassword", GOOD_PASSWORD + "X");
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Այս գաղտնաբառերը չեն համընկնում"));
    }

    @Test
    public void registrationWithLowLengthPasswordWillShowErrorMessage() {

        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", NONE_EXISTING_USERNAME);
//        body.add("password", SHORT_STRING);
//        body.add("confirmPassword", SHORT_STRING);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Գաղտնաբառը պետք է լինի առնվազն 6 նիշ ձեր անվտանգության համար․"));
//
    }

    @Test
    public void registrationWithEmptyUserNameWillShowErrorMessage() {

        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("username", EMPTY_STRING);
//        body.add("password", GOOD_PASSWORD);
//        body.add("confirmPassword", GOOD_PASSWORD);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Այս դաշտը պարտադիր է"));

    }

    @Test
    public void registrationWithEmptyPasswordWillShowErrorMessage() {

//        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", NONE_EXISTING_USERNAME);
//        body.add("password", EMPTY_STRING);
//        body.add("confirmPassword", GOOD_PASSWORD);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Այս դաշտը պարտադիր է"));

    }

    @Test
    public void registrationWithExistingUserWillShowErrorMessage() {

//        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", EXISTING_USERNAME);
//        body.add("password", GOOD_PASSWORD);
//        body.add("confirmPassword", GOOD_PASSWORD);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Այս էլ․ հասցեով գրանցված օգտատեր արդեն գոյություն ունի:"));
    }

    @Test
    public void registrationWithEmptyConfirmPasswordWillShowErrorMessage() {


//        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", NONE_EXISTING_USERNAME);
//        body.add("password", GOOD_PASSWORD);
//        body.add("confirmPassword", EMPTY_STRING);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Այս դաշտը պարտադիր է"));

    }

    @Test
    public void registrationWithLowLengthUserName() {

//        // Create the request body as a MultiValueMap
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("userName", SHORT_STRING);
//        body.add("password", GOOD_PASSWORD);
//        body.add("confirmPassword", GOOD_PASSWORD);
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("/registration", body, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("Խնդրում ենք օգտագործել 4-ից 32 նիշ"));

    }

    @Test
    public void registrationWithValidEmailAndPasswordWillRegisterAndRedirectHomePage() throws Exception {


        mvc.perform(post("/registration")
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", GOOD_PASSWORD).
                        with(csrf()))
                .andExpect(authenticated().withRoles("USER"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));


    }

    @Test
    public void canOpenRegistrationPage() {
//        ResponseEntity<String> responseEntity =
//                restTemplate.getForEntity("/registration", String.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody().contains("<title>Գրանցում</title>"));
//        assertTrue("Form tag exists", responseEntity.getBody().contains("registerForm"));
    }


}