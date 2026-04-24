package giuseppetavella.D5_gestione_eventi.security;

// import giuseppetavella.demo_login_system.entities.User;
// import giuseppetavella.demo_login_system.exceptions.NotFoundException;
// import giuseppetavella.demo_login_system.exceptions.UnauthorizedException;
// import giuseppetavella.demo_login_system.services.UsersService;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonAutorizzatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.security.TokenTools;
import giuseppetavella.D5_gestione_eventi.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenTools tokenTools;

    @Autowired
    private UtentiService utentiService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, NonAutorizzatoException
    {

        // verify that header contains token etc.
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {

            response.sendError(401, "Token is missing in authorization header, or it's not in the right format.");
            return;
            // throwing exceptions in the security filter 
            // does have the expected behavior, meaning that
            // the exception does not get caught by 
            // an exception handler
            // throw new UnauthorizedException("Manca il token nell'authorization header, o non è nel formato corretto.");
        }



        // we get the token
        String accessToken = authHeader.replace("Bearer ", "");

        // if token verification fails
        try {
            // verify that token is valid etc.
            tokenTools.verifyToken(accessToken);

        } catch(NonAutorizzatoException ex) {
            response.sendError(401, "Invalid token.");
            return;
        }


        // ******** AUTHORIZATION ************

        // 1. extract user's ID from token
        UUID userId = this.tokenTools.extractIdFromToken(accessToken);

        Utente currentUser;

        // 2. find user in DB
        try {
            currentUser = this.utentiService.findById(userId);
        } catch (NonTrovatoException ex) {
            response.sendError(401, "Token was valid but the user associated to it no longer exists.");
            return;
        }

        // 3. now we need to make this user available to the Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        // we now set the current user of this request
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // System.out.println("do filter internal called!");

        filterChain.doFilter(request, response);

    }


    // we can specify which paths not to filter,
    // for example the paths starting with /auth   

    /**
     * In this method we specify in which cases
     * our security filter should not be called.
     * For example, we might want to disable this security filter
     * for all endpoints starting with /auth
     */
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher matcher = new AntPathMatcher();
        String path = request.getServletPath();

        boolean isAuthPath = matcher.match("/auth/**", path);
        boolean isRoot = matcher.match("/", path);

        return isAuthPath || isRoot;
    }

}