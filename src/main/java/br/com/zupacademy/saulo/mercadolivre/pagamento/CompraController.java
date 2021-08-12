package br.com.zupacademy.saulo.mercadolivre.pagamento;

import br.com.zupacademy.saulo.mercadolivre.pagamento.entidade.CompraRequest;
import br.com.zupacademy.saulo.mercadolivre.pagamento.entidade.CompraResponse;
import br.com.zupacademy.saulo.mercadolivre.config.EmailFakeVendedor;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CompraController {

    private final RepositoryProdutoJPA repositoryProdutoJPA;
    private final RepositoryCompraJPA repositoryCompraJPA;


    public CompraController(final RepositoryProdutoJPA repositoryProdutoJPA,
                            final RepositoryCompraJPA repositoryCompraJPA) {
        this.repositoryProdutoJPA = repositoryProdutoJPA;
        this.repositoryCompraJPA = repositoryCompraJPA;
    }

    @PostMapping(path = "/compra")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody
    CompraResponse cadastrarCompra(@RequestBody @Valid final CompraRequest compraRequest,
                                    @AuthenticationPrincipal Usuario userLogged) {
        final CompraResponse buy = compraRequest.buy(repositoryCompraJPA, repositoryProdutoJPA, userLogged);
        EmailFakeVendedor.sendTo(buy.emailDoVendedor(), "Compra do produto: "
                + buy.getCompra().getNomeProduto() + ", Registrada!", buy.emailDeQuemComprou());
        return buy;
    }
}
