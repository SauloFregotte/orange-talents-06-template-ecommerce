package br.com.zupacademy.saulo.mercadolivre.security;

import br.com.zupacademy.saulo.mercadolivre.usuario.RepositoryUsuarioJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class Security extends WebSecurityConfigurerAdapter {

    Security(final AutenticacaoService autenticacaoService){
        this.autenticacaoService = autenticacaoService;
    }

    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenServices tokenServices;
    @Autowired
    private RepositoryUsuarioJPA repositoryUsuarioJPA;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager()throws Exception{
        return super.authenticationManager();
    }

    //Configurações de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("CONFIGURANDO AUTORIZAÇÃO");
        /**
         * .formLogin() é um método que cria uma Session guardando o estado dessa Session (StateFull),
         * que vai contra os princípios da arquitetura Rest (StateLess), logo foi trocado pelo método
         * sessionCreationPolicy(SessionCreationPolicy.STATELESS),
         * onde eu defino que não devem ser criadas sessões
         *
         * Lembrar de tirar os end-points do metodo abaixo,
         * pois estou deixando eles publicos para acesso,
         * invalidando assim a razao de ter feito o token de acesso.
         * **/
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cadastrar-usuario").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastrar-categoria").permitAll()
                .antMatchers(HttpMethod.POST, "/cadastrar-produto/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest()
                .authenticated()
        .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .addFilterBefore(
                        new AutenticacaoViaTokenFilter(tokenServices, repositoryUsuarioJPA),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    //Configuração de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("CONFIGURANDO AUTENTICAÇÃO");
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de recursos estáticos (js, css, imagens, etc...)
    @Override
    public void configure(WebSecurity web) throws Exception {
        System.out.println("CONFIGURANDO RECURSOS ESTÁTICOS");
    }
}
