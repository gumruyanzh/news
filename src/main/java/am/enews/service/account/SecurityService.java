package am.enews.service.account;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface SecurityService {
    String findLoggedInUsername();

    boolean autoLogin(String username, String password);

    long activateAccount();

    boolean hasRole(String role);
}
