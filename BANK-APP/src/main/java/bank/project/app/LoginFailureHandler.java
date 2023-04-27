package bank.project.app;

import bank.project.dao.Role;
import bank.project.dao.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    RoleService roleService;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("role");
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        Role role = roleService.getByUsername(userName);
        if (role == null) {
            exception = new LockedException(bundle.getString("db_user"));
            super.setDefaultFailureUrl("/web/log/?error=" + bundle.getString("db_user"));
        }
        else {
            if (role.getRolestatus().equalsIgnoreCase("inactive")) {
                logger.info(bundle.getString("incorrect"));
                exception = new LockedException(bundle.getString("deactivated"));
                super.setDefaultFailureUrl("/web/log/?error="+bundle.getString("deactivated"));
            }
            else
                {
                roleService.incrementFailedAttempts(role.getRoleid());
                int attempts = roleService.getAttempts(role.getRoleid());
                logger.info("attempts: "+attempts);

                if (attempts == 1) {
                    logger.info(bundle.getString("db_incorrect_pw") + bundle.getString("attempt2"));
                    exception = new LockedException(bundle.getString("attempt2") + bundle.getString("db_incorrect_pw"));
                    super.setDefaultFailureUrl("/web/log/?error=" + bundle.getString("db_incorrect_pw") + bundle.getString("attempt2"));
                } else
                    if (attempts == 2) {
                    logger.info(bundle.getString("db_incorrect_pw") + bundle.getString("attempt1"));
                    exception = new LockedException(bundle.getString("attempt1") + bundle.getString("db_incorrect_pw"));
                    super.setDefaultFailureUrl("/web/log/?error=" + bundle.getString("db_incorrect_pw") + bundle.getString("attempt1"));
                } else {
                    logger.info(bundle.getString("db_unsuccessfull"));
                    exception = new LockedException(bundle.getString("db_unsuccessfull"));
                    roleService.updateStatus();
                    super.setDefaultFailureUrl("/web/log/?error=" + bundle.getString("db_unsuccessfull"));
                }
            }
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}


