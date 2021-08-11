package br.com.zupacademy.saulo.mercadolivre.produto.imagens;

import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.ProdutoResponse;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ImagemRequest {

    @Size(min = 1)
    @NotNull
    private List<String> listaImagens = new ArrayList<>();

    public ProdutoResponse associarImagemProduto(RepositoryProdutoJPA repositoryProdutoJPA,
                                                 Usuario userLogged, Produto produto) {
        verifyIfProdutoBelongsToUser(userLogged, produto);
        produto.associarImagens(listaImagens);
        return new ProdutoResponse(produto.cadastrar(repositoryProdutoJPA));
    }

    private void verifyIfProdutoBelongsToUser(Usuario userLogged, Produto produto) {
        if(!userLogged.equals(produto.getUsuario()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Esse produto não pertence ao usuario logado");
    }

    public void setListaImagens(List<String> listaImagens) {
        this.listaImagens = listaImagens;
    }
}
