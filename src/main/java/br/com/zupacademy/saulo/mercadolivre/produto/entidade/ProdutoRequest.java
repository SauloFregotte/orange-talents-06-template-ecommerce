package br.com.zupacademy.saulo.mercadolivre.produto.entidade;

import br.com.zupacademy.saulo.mercadolivre.categoria.RepositoryCategoriaJPA;
import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import br.com.zupacademy.saulo.mercadolivre.produto.RepositoryProdutoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas.Caracteristicas;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

public class ProdutoRequest {

    private String nome;
    @Positive
    private double valor;
    @Positive
    private int quantidade;
    @Size(max = 1000)
    private String descricao;

    private String categoria;
    private List<Caracteristicas> caracteristicas;

    public ProdutoResponse cadastrar(final RepositoryProdutoJPA repositoryProdutoJPA, final RepositoryCategoriaJPA repositoryCategoriaJPA, final Usuario userLogged){

        return new ProdutoResponse(
                Produto.builder()
                        .comNome(nome)
                        .comValor(valor)
                        .comQuantidade(quantidade)
                        .comDescricao(descricao)
                        .comCategoria(Categoria.verifyIfCategoriaExists(repositoryCategoriaJPA, categoria))
                        .comCaracteristicas(caracteristicas)
                        .comUsuario(userLogged)
                        .build()
                        .cadastrar(repositoryProdutoJPA)
        );
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCaracteristicas(List<Caracteristicas> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
