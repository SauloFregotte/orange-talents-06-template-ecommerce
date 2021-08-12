package br.com.zupacademy.saulo.mercadolivre.produto;

import br.com.zupacademy.saulo.mercadolivre.categoria.RepositoryCategoriaJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoRequest;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoResponse;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoResponseDetalhado;
import br.com.zupacademy.saulo.mercadolivre.produto.imagens.ImagemRequest;
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
    ProdutoResponse cadastrarProduto(@RequestBody @Valid final ProdutoRequest produtoRequest,
                                     @AuthenticationPrincipal Usuario userLogged) {
        return produtoRequest.cadastrar(repositoryProdutoJPA, repositoryCategoriaJPA, userLogged);
    }

    @PostMapping("/cadastro-imagens/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponse cadastroDeImagensNoProduto(@RequestBody @Valid final ImagemRequest imagemRequest,
                                                      @PathVariable Long id,
                                                      @AuthenticationPrincipal Usuario userLogged) {
        /*Ideal seria fazer um repositoryProdutoJPA.getByIdAndUser(id, userLogged)
          mas para seguir o desafio fiz assim e verifico dentro do request se pertence ao userLogged**/
        return imagemRequest.associarImagemProduto(
                repositoryProdutoJPA,
                userLogged,
                repositoryProdutoJPA.getById(id));
    }

    @GetMapping("/produto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDetalhado obterInformacoesProdutoDetalhadas(@PathVariable Long id){
        return new ProdutoResponseDetalhado(Produto.obterInformacoes(id, repositoryProdutoJPA));
    }
}
