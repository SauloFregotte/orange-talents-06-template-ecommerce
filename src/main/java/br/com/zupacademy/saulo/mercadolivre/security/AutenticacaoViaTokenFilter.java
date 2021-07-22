package br.com.zupacademy.saulo.mercadolivre.security;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenServices tokenServices;

    private RepositoryUsuarioJPA repositoryUsuarioJPA;

    public AutenticacaoViaTokenFilter(TokenServices tokenServices, RepositoryUsuarioJPA repositoryUsuarioJPA) {
        this.tokenServices = tokenServices;
        this.repositoryUsuarioJPA = repositoryUsuarioJPA;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("FILTRO DE AUTENTICAÇÃO TOKEN");
        String token = recuperarToken(httpServletRequest);
        if(tokenServices.isValid(token))autenticar(token);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void autenticar(String token) {
        System.out.println("AUTENTICA O TOKEN");
        Long idUser = tokenServices.getIdUsuario(token);
        Usuario user = repositoryUsuarioJPA.findById(idUser)
                .orElseThrow(()->new UsernameNotFoundException("Usuario não existe!"));
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, null));
    }

    private String recuperarToken(HttpServletRequest httpServletRequest) {
        System.out.println("RECUPERA TOKEN USER");
        final String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")){
            return null;
        }
        return authorization.substring(7);
    }
}
