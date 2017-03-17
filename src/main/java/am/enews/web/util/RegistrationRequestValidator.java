package am.enews.web.util;

import am.enews.service.account.UserService;
import am.enews.web.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by vazgent on 3/15/2017.
 */
@Component
public class RegistrationRequestValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        System.out.println(userService);
        RegistrationRequest request = (RegistrationRequest) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");


        if (request.getUsername().length() < 4 || request.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.username");
        }
        if (userService.findByUsername(request.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }



        if (request.getPassword().length() < 6 || request.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }



        if (!request.getConfirmPassword().equals(request.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");
        }
    }
}