package br.com.zupacademy.saulo.mercadolivre.produto;

import br.com.zupacademy.saulo.mercadolivre.categoria.RepositoryCategoriaJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoRequest;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoResponse;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProdutoController {

    private final RepositoryCategoriaJPA repositoryCategoriaJPA;
    private final RepositoryProdutoJPA repositoryProdutoJPA;

    public ProdutoController(final RepositoryProdutoJPA repositoryProdutoJPA,
                             final RepositoryCategoriaJPA repositoryCategoriaJPA) {
        this.repositoryCategoriaJPA = repositoryCategoriaJPA;
        this.repositoryProdutoJPA = repositoryProdutoJPA;
    }

    @PostMapping(path = "/cadastrar-produto")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ProdutoResponse cadastrarUsuario(@RequestBody @Valid final ProdutoRequest produtoRequest,
                                     @AuthenticationPrincipal Usuario userLogged){
        return produtoRequest.cadastrar(repositoryProdutoJPA, repositoryCategoriaJPA, userLogged);
    }
}
