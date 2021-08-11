package br.com.zupacademy.saulo.mercadolivre.categoria.entidade;

public class CategoriaResponse {

    private Categoria categoria;

    public CategoriaResponse(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return categoria.getId();
    }

    public String getNome() {
        return categoria.getNome();
    }

    public String getNomeCategoriaMae() {
        return categoria.getCategoriaMae();
    }
}
