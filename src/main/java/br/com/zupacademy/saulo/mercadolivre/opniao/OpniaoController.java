package br.com.zupacademy.saulo.mercadolivre.opniao;

import br.com.zupacademy.saulo.mercadolivre.opniao.entidade.OpniaoRequest;
import br.com.zupacademy.saulo.mercadolivre.opniao.entidade.OpniaoResponse;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OpniaoController {

    private final RepositoryOpniaoJPA repositoryOpniaoJPA;
    private final RepositoryProdutoJPA repositoryProdutoJPA;

    public OpniaoController(final RepositoryOpniaoJPA repositoryOpniaoJPA,
                            final RepositoryProdutoJPA repositoryProdutoJPA) {
        this.repositoryOpniaoJPA = repositoryOpniaoJPA;
        this.repositoryProdutoJPA = repositoryProdutoJPA;
    }

    @PostMapping("/cadastro-opniao/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpniaoResponse cadastroDeOpniao(@RequestBody @Valid final OpniaoRequest opniaoRequest,
                                           @PathVariable Long id,
                                           @AuthenticationPrincipal Usuario userLogged) {
        /*Ideal seria fazer um repositoryProdutoJPA.getByIdAndUser(id, userLogged)
          mas para seguir o desafio fiz assim e verifico dentro do request se pertence ao userLogged*/
        return opniaoRequest.associarOpniaoProduto(
                repositoryOpniaoJPA,
                userLogged,
                repositoryProdutoJPA.getById(id));
    }
}
