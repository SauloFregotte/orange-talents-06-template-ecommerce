package br.com.zupacademy.saulo.mercadolivre.categoria.entidade;

import br.com.zupacademy.saulo.mercadolivre.categoria.RepositoryCategoriaJPA;

public class CategoriaRequest {

    private String nome;

    private String nomeCategoriaMae;

    public CategoriaResponse cadastrar(final RepositoryCategoriaJPA repositoryCategoriaJPA){
        Categoria categoria = new Categoria(nome).cadastrar(repositoryCategoriaJPA, nomeCategoriaMae);
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome()
        );
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public void setNomeCategoriaMae(String nomeCategoriaMae) {
        this.nomeCategoriaMae = nomeCategoriaMae;
    }
}
