package am.enews.integration;

import am.enews.Application;
import org.junit.Assert;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static am.enews.integration.HelperConstant.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vazgent on 3/15/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canOpenLoginPage() {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/login", String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody().contains("<title>Մուտք գործել</title>"));
    }


    @Test
    public void loginWithValidUserWillRedirectHomePage() {

        // Create the request body as a MultiValueMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("username", EXISTING_USERNAME);
        body.add("password", VALID_USER_PASSWORD);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/login", body, String.class);
        //302
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals("/", responseEntity.getHeaders().getLocation().getPath());
    }

    @Test
    public void loginWithInvalidPasswordWillRedirectLoginErrorPage() {

        // Create the request body as a MultiValueMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("username", EXISTING_USERNAME);
        body.add("password", INVALID_USER_PASSWORD);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/login", body, String.class);
        //302
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals("/login-error", responseEntity.getHeaders().getLocation().getPath());
    }

    @Test
    public void loginWithInvalidPasswordAndInvalidUserNameWillRedirectLoginErrorPage() {

        // Create the request body as a MultiValueMap
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("username", NONE_EXISTING_USERNAME);
        body.add("password", INVALID_USER_PASSWORD);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/login", body, String.class);
        //302
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals("/login-error", responseEntity.getHeaders().getLocation().getPath());
    }

    @Test
    public void canOpenLoginErrorPage() {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("/login-error", String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody().contains("Սխալ հասցե կամ կամ գաղտանաբառ"));
    }

}
