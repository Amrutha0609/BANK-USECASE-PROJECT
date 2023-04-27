package bank.project.app;

import bank.project.dao.Role;
import bank.project.dao.RoleService;
import com.sun.org.apache.xml.internal.utils.res.XResourceBundle;
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
                logger.info(" not successful");
                super.setDefaultTargetUrl("/logout");
                super.setTargetUrlParameter("login/logout?error="+bundle.getString("accInactive"));
            }
            else {
                roleService.resetAttempts(role.getRoleid());
                super.setDefaultTargetUrl("/web/dash");
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

