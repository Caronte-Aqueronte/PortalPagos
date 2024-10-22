package ss1.api.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ss1.api.services.auth.JwtGeneratorService;

/**
 * El filtro JwtRequestFilter es responsable de interceptar las solicitudes HTTP
 * para verificar si existe un token JWT en los encabezados de la solicitud. Si
 * el token es válido, autentica al usuario y establece el contexto de seguridad
 * de la aplicación.
 *
 * @author luism
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Servicio para cargar detalles del usuario desde la base de datos
    @Autowired
    private UserDetailsService userDetailsService;

    // Servicio para generar y validar tokens JWT
    @Autowired
    private JwtGeneratorService jwtGeneratorService;

    /**
     * El método doFilterInternal es el encargado de procesar cada solicitud
     * entrante. Verifica el encabezado "Authorization" para extraer el token
     * JWT y luego lo valida. Si el token es válido, autentica al usuario y
     * establece la autenticación en el contexto de seguridad.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros que se sigue ejecutando.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    public void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Se obtiene el encabezado de autorización
        final String header = request.getHeader("Authorization");

        // Se verifica si el encabezado contiene un token JWT
        if (header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7); // Extrae el token después de "Bearer "
            String user = jwtGeneratorService.extractUserName(jwt); // Extrae el nombre de usuario del token

            // Verifica que el usuario no esté ya autenticado en el contexto de seguridad
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Carga los detalles del usuario desde el UserDetailsService
                UserDetails userDetails = userDetailsService.loadUserByUsername(user);

                // Verifica la validez del token JWT
                if (jwtGeneratorService.validateTOken(jwt, userDetails)) {

                    // Autentica al usuario y establece sus roles/autoridades
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities());

                    // Detalles adicionales de autenticación desde la solicitud
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    // Establece la autenticación en el contexto de seguridad de Spring
                    SecurityContextHolder.getContext().setAuthentication(
                            authenticationToken);

                    // Mensaje de depuración para confirmar la autenticación
                    System.out.println("User authenticated: " + user);

                    // Imprime los roles del usuario para depuración
                    Collection<? extends GrantedAuthority> authorities
                            = userDetails.getAuthorities();

                    System.out.println("User rol: " + authorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(", ")));
                } else {
                    // Mensaje de depuración si la validación del token falla
                    System.out.println("JWT validation failed.");
                }
            }
        }
        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

}
