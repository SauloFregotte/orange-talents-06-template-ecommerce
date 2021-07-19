package br.com.zupacademy.saulo.mercadolivre.categoria;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.CategoriaRequest;
import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.CategoriaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoriaController {

    private final RepositoryCategoriaJPA repositoryCategoriaJPA;

    public CategoriaController(RepositoryCategoriaJPA repositoryCategoriaJPA) {
        this.repositoryCategoriaJPA = repositoryCategoriaJPA;
    }

    @PostMapping(path = "/cadastrar-categoria")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    CategoriaResponse cadastrarUsuario(@RequestBody @Valid final CategoriaRequest categoriaRequest){
        return categoriaRequest.cadastrar(repositoryCategoriaJPA);
    }
}
