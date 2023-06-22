package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final UsuarioRepository usuarioRepository;
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
			//obtener token del header
			String authHeader = request.getHeader("Authorization");
			if(authHeader!= null){
				var token = authHeader.replace("Bearer ", ""); // el prefijo por defecto siempre en Bearer,
				// para que solo lea el token toca reemplazar
				//el prefijo por vacio
				var subject = tokenService.getSubject(token);
				if (subject != null){
					//token valido
					var usuario = usuarioRepository.findByLogin(subject);
					var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication); //forzar autenticacion
					
				}
			}
			
			
			filterChain.doFilter(request, response);
		
	}
}
