package br.com.zupacademy.saulo.mercadolivre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping
    public ResponseEntity<TokenDto> autenticaUsuario(@RequestBody @Valid LoginRequest loginRequest){
        try {
            final String token = tokenServices
                    .gerarToken(
                            authenticationManager
                                    .authenticate(
                                            loginRequest.converter()
                                    )
                    );
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));

        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
