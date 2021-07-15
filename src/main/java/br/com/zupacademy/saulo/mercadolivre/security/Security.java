package br.com.zupacademy.saulo.mercadolivre.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class Security extends WebSecurityConfigurerAdapter {

    Security(final AutenticacaoService autenticacaoService){
        this.autenticacaoService = autenticacaoService;
    }

    private AutenticacaoService autenticacaoService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * .formLogin() é um método que cria uma Session guardando o estado dessa Session (StateFull),
         * que vai contra os princípios da arquitetura Rest (StateLess), logo foi trocado pelo método
         * sessionCreationPolicy(SessionCreationPolicy.STATELESS),
         * onde eu defino que não devem ser criadas sessões
         * **/
        http.authorizeRequests()
        .antMatchers(HttpMethod.POST, "/cadastrar-usuario").permitAll()
                .anyRequest()
                .authenticated()
        .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
