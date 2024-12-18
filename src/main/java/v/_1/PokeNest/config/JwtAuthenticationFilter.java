package v._1.PokeNest.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import v._1.PokeNest.service.impl.JwtServiceImpl;

//Se ejecutará un filtro solo una vez por cada solicitud HTTP
//MODIFICAR CLASE PARA OBTENER TEMA DE ROLES Y AUTHORIZACIONES!
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtServiceImpl;
    private final UserDetailsService userDetailsService;

    //Método que va a hacer todos los filtros para los TOKENS
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token==null){
            filterChain.doFilter(request,response);
            return;
        }

        //Si todo va bien accede al username del token
        username= jwtServiceImpl.getUsernameFromToken(token);

        //Si no obtenemos el nombre del securityContextHolder lo va a buscar a la BDD
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

            UserDetails userDetails=userDetailsService.loadUserByUsername(username);

            //Validar si el token es valido
            if (jwtServiceImpl.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Comprobación del método - BORRAR PASADAS LAS PRUEBAS
        System.out.println("Token JWT encontrado: " + token);

        filterChain.doFilter(request,response);
    }

    //Obtener token a través de un request
    private String getTokenFromRequest(HttpServletRequest request){
        final String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7); // Extrae el token sin "Bearer "
        }
        return null;
    }
}
