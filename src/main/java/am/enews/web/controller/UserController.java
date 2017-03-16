package am.enews.web.controller;

import am.enews.service.account.SecurityService;
import am.enews.service.account.UserService;
import am.enews.service.dto.UserCreateDto;
import am.enews.web.model.LoginModel;
import am.enews.web.model.RegistrationRequest;
import am.enews.web.util.RegistrationRequestValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vazgent on 3/15/2017.
 */
@Controller
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RegistrationRequestValidator userValidator;


    @GetMapping("/registration")
    public String registrationGet(RegistrationRequest registrationRequest) {
        logger.info("Going registration page");
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(RegistrationRequest registrationRequest, BindingResult bindingResult) {
        userValidator.validate(registrationRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.format("Error registration page, count %s", bindingResult.getErrorCount()));
            return "registration";
        }

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUserName(registrationRequest.getUsername());
        userCreateDto.setPassword(registrationRequest.getPassword());

        long userId = userService.save(userCreateDto);
        securityService.autoLogin(registrationRequest.getUsername(), registrationRequest.getPassword());
        logger.info(String.format("Registration success for userName: %s, redirecting", registrationRequest.getUsername()));
        return "redirect:/";
    }


    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginModel", new LoginModel());
        model.addAttribute("loginError", true);
        logger.error("Can't login");
        return "login";
    }


    @GetMapping("/login")
    public String login(LoginModel loginModel) {
        return "login";
    }
}
