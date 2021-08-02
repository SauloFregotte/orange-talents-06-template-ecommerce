package br.com.zupacademy.saulo.mercadolivre.security.autenticacaoendpoint;

import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServices {

    @Value("${mercado-livre.jwt.expiration}")
    private String expiration;

    @Value("${mercado-livre.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication){
        System.out.println("GERANDO TOKEN");
        final Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao= new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API do mercado livre")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        System.out.println("TOKEN = " + token);
        System.out.println("VERIFICA SE TOKEN É VALIDO");
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            System.out.println("TRUE");
            return true;
        }catch (Exception e){
            System.out.println("FALSE");
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        return Long.parseLong(
                Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
        );
    }
}
