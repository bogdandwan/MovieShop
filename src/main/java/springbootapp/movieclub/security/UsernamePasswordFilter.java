package springbootapp.movieclub.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UsernamePasswordFilter extends GenericFilter {

    private final UsernamePasswordProvider usernamePasswordProvider;

    public UsernamePasswordFilter(@Lazy UsernamePasswordProvider usernamePasswordProvider) {
        this.usernamePasswordProvider = usernamePasswordProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final Authentication authentication = usernamePasswordProvider.authenticate(request,response);
        if (authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
