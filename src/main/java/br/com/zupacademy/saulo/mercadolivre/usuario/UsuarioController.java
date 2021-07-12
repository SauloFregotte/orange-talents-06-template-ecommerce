package br.com.zupacademy.saulo.mercadolivre.usuario;

import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.UsuarioRequest;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UsuarioController {

    private final RepositoryUsuarioJPA repositoryUsuarioJPA;

    public UsuarioController(RepositoryUsuarioJPA repositoryUsuarioJPA) {
        this.repositoryUsuarioJPA = repositoryUsuarioJPA;
    }

    @PostMapping(path = "/cadastrar-usuario")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody UsuarioResponse cadastrarUsuario(@RequestBody @Valid final UsuarioRequest usuarioRequest){
        return usuarioRequest.cadastrar(repositoryUsuarioJPA);
    }
}
