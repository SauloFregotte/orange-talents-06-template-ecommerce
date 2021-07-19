package br.com.zupacademy.saulo.mercadolivre.categoria.entidade;

public class CategoriaResponse {
    public CategoriaResponse(final Long id, final String nome) {
        this.id = id;
        this.nome = nome;
    }

    private Long id;
    private String nome;
    private String nomeCategoriaMae;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
