package br.com.zupacademy.saulo.mercadolivre.pergunta;

import br.com.zupacademy.saulo.mercadolivre.pergunta.entidade.PerguntaRequest;
import br.com.zupacademy.saulo.mercadolivre.pergunta.entidade.PerguntaResponse;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PerguntaController {

    private final RepositoryPerguntaJPA repositoryPerguntaJPA;
    private final RepositoryProdutoJPA repositoryProdutoJPA;

    PerguntaController(final RepositoryPerguntaJPA repositoryPerguntaJPA,
                       final RepositoryProdutoJPA repositoryProdutoJPA) {
        this.repositoryPerguntaJPA = repositoryPerguntaJPA;
        this.repositoryProdutoJPA = repositoryProdutoJPA;
    }

    @PostMapping("/cadastro-pergunta-produto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    PerguntaResponse cadastrar(@RequestBody @Valid final PerguntaRequest perguntaRequest,
                               @PathVariable final Long id,
                               @AuthenticationPrincipal final Usuario userLogged) {
        final PerguntaResponse perguntaResponse = perguntaRequest
                .associarOpniaoProduto(
                        repositoryPerguntaJPA,
                        userLogged,
                        repositoryProdutoJPA.getById(id));

        EmailFakeVendedor.sendTo(
                perguntaResponse.emailDoVendedor(),
                perguntaResponse.pergunta(),
                perguntaResponse.emailDeQuemPerguntou()
        );
        return perguntaResponse;
    }
}
