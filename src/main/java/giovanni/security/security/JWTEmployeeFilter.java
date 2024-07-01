package giovanni.security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTEmployeeFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            //1. Controlliamo se nella richiesta corrente ci sia un Authorization Header, se non c'è --> 401
             String autHeader = request.getHeader("Authorization");

             if (autHeader == null || autHeader.startsWith("Bearer") ) throw new UnauthorizedException(" inserisci correttamente il token nell'header");

           // 2. Se c'è estraiamo il token dall'header
             String accessToken = autHeader.substring(7); // con il substring taglio fuori bearer e ottengo il token

            //3. Verifichiamo se il token è stato manipolato (verifica della signature) e se non è scaduto (verifica dell'Expiration Date)

                jwtTools.verifyToken(accessToken);

           // 4. Se tutto è OK, proseguiamo con il prossimo elemento della Filter Chain, prima o poi arriveremo all'endpoint

              filterChain.doFilter(request , response); // questo codice manda al prossimo evento della filterChain

           // 5. Se il token non è OK --> 401 questa cosa si gestisce tramite try catch nel JWTTools


    }
        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            // Uso questo metodo per specificare in quali situazioni NON FILTRARE
            // Posso ad esempio escludere dal controllo del filtro tutti gli endpoint dentro il controller /auth
            return new AntPathMatcher().match("/auth/**", request.getServletPath());
        }


}
