package br.com.zupacademy.saulo.mercadolivre.categoria.entidade;

import br.com.zupacademy.saulo.mercadolivre.categoria.RepositoryCategoriaJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class Categoria {

    public static Categoria verifyIfCategoriaExists(RepositoryCategoriaJPA repositoryCategoriaJPA, String nome) {
        return repositoryCategoriaJPA.findFirstCategoriaByNome(nome)
                .orElseThrow(() -> {throw new IllegalArgumentException("Categoria não existe!");});
    }

    public Categoria(final String nome) {
        Objects.requireNonNull(nome, "Nome não pode ser nulo!");
        if(nome.equals("")) throw new IllegalArgumentException("Nome não pode ser vazio!");
        this.nome = nome;
    }

    public Categoria() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    private String nome;

    @ManyToOne
    private Categoria categoriaMae;

    @OneToOne(mappedBy = "categoria", cascade = CascadeType.PERSIST)
    private Produto produtos;

    /**
     * 1-verificar se o nome de categoria é único
     * 2-verificar se a categoria mãe existe
     * 3-salvar
     * **/
    public Categoria cadastrar(final RepositoryCategoriaJPA repositoryCategoriaJPA,
                               final String nomeCategoriaMae){
        verifyIfNameIsUnique(repositoryCategoriaJPA);
        findCategoriaMae(nomeCategoriaMae, repositoryCategoriaJPA);
        return repositoryCategoriaJPA.save(this);
    }

    private void verifyIfNameIsUnique(final RepositoryCategoriaJPA repositoryCategoriaJPA){
        repositoryCategoriaJPA.findFirstCategoriaByNome(nome)
                .ifPresent(e->{throw new EntityExistsException("Categoria já cadastrada!");});
    }

    private void findCategoriaMae(final String nomeCategoriaMae, final RepositoryCategoriaJPA repositoryCategoriaJPA){
        repositoryCategoriaJPA.findFirstCategoriaByNome(nomeCategoriaMae)
                .ifPresent(categoria -> categoriaMae = categoria);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoriaMae() {
        if (categoriaMae == null)
            return "";
        return categoriaMae.getNome();
    }
}
