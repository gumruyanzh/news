package am.enews.integration;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static am.enews.integration.HelperConstant.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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

    public static final String REGISTRATION_URL = "/registration";



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
                .andExpect(content().contentType(TEXT_HTML_UTF_8))
                .andExpect(view().name("login"));
    }


    @Test
    public void registrationWithMismatchPasswordWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", GOOD_PASSWORD + "x").with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Այս գաղտնաբառերը չեն համընկնում.")));
    }

    @Test
    public void registrationWithLowLengthPasswordWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", SHORT_STRING)
                .param("confirmPassword", SHORT_STRING).with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Գաղտնաբառը պետք է լինի առնվազն 6 նիշ ձեր անվտանգության համար․")));
    }

    @Test
    public void registrationWithEmptyUserNameWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", EMPTY_STRING)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", GOOD_PASSWORD).with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Այս դաշտը պարտադիր է")));
    }

    @Test
    public void registrationWithEmptyPasswordWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", EMPTY_STRING)
                .param("confirmPassword", EMPTY_STRING).with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Այս դաշտը պարտադիր է")));
    }

    @Test
    public void registrationWithExistingUserWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", EXISTING_USERNAME)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", GOOD_PASSWORD).with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Այս էլ․ հասցեով գրանցված օգտատեր արդեն գոյություն ունի:")));
    }

    @Test
    public void registrationWithEmptyConfirmPasswordWillShowErrorMessage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", EMPTY_STRING).with(csrf()))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(content().string(new Contains("Այս դաշտը պարտադիր է")));
    }

    @Test
    public void registrationWithLowLengthUserName() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", SHORT_STRING)
                .param("confirmPassword", SHORT_STRING).with(csrf()))
                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationWithValidEmailAndPasswordWillRegisterAndRedirectHomePage() throws Exception {
        mvc.perform(post(REGISTRATION_URL)
                .param("username", NONE_EXISTING_USERNAME)
                .param("password", GOOD_PASSWORD)
                .param("confirmPassword", GOOD_PASSWORD).
                        with(csrf()))
                .andExpect(authenticated().withRoles("USER"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void canOpenRegistrationPage() throws Exception {

        mvc.perform(get(REGISTRATION_URL))
                .andExpect(view().name("registration"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF_8));
    }
}