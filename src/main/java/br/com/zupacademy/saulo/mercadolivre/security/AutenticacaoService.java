package br.com.zupacademy.saulo.mercadolivre.security;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    public AutenticacaoService(RepositoryUsuarioJPA repositoryUsuarioJPA) {
        this.repositoryUsuarioJPA = repositoryUsuarioJPA;
    }

    private RepositoryUsuarioJPA repositoryUsuarioJPA;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("BUSCO USUARIO COMPATIVEL");
        return repositoryUsuarioJPA.findFirstUsuarioByEmail(username)
                .orElseThrow(()->{throw new UsernameNotFoundException("Usuário ou senha incorretos!");});
    }
}
