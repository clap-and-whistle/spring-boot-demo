package page.clapandwhistle.uam.framework.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import page.clapandwhistle.uam.controller.UserOperation.LoginController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SpringAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e)
                throws IOException, ServletException {
        System.out.println("SpringAuthenticationFailureHandler::onAuthenticationFailure: " + e.toString());
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
                + "/" + LoginController.URL_PATH_PREFIX + LoginController.URL_PATH_LOGIN_FORM);
    }
}
