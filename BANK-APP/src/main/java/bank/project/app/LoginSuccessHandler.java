package bank.project.app;

import bank.project.dao.Role;
import bank.project.dao.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    RoleService roleService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Role role= (Role) authentication.getPrincipal();
            ResourceBundle bundle=ResourceBundle.getBundle("role");
            if(role.getRolestatus().equalsIgnoreCase("inactive")) {
                logger.info(role + " in success handler");
                super.setDefaultTargetUrl("/logout");
            }
            else {
                roleService.resetAttempts(role.getRoleid());
                // super.setDefaultTargetUrl("/web/log");

// if(customer.getAttempts()==0)
// logger.info("deactivate");
                super.setDefaultTargetUrl("/web/dash");
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

