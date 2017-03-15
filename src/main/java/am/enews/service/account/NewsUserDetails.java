package am.enews.service.account;


import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface NewsUserDetails extends UserDetails {

    String getDisplayUserName();
    boolean isActive();
}