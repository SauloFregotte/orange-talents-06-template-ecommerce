package br.com.zupacademy.saulo.mercadolivre.produto;

import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;

public class ProdutoResponseDetalhado {

    private final Produto produto;

    public ProdutoResponseDetalhado(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public Double getMedia() {
        return produto.calcularMedia();
    }

    public Integer getTotalNotasProduto() {
        return produto.totalNotas();
    }
}
