package br.com.zupacademy.saulo.mercadolivre.categoria.entidade;

public class CategoriaResponse {
    public CategoriaResponse(Categoria categoria) {
        this.categoria = categoria;
    }

    private Categoria categoria;


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
