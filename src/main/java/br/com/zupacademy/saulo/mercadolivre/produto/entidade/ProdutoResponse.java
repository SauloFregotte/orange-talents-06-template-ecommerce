package br.com.zupacademy.saulo.mercadolivre.produto.entidade;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import br.com.zupacademy.saulo.mercadolivre.produto.caracteristicas.Caracteristicas;
import br.com.zupacademy.saulo.mercadolivre.produto.imagens.Imagem;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ProdutoResponse {

    public ProdutoResponse(Produto produto){
        id = produto.getId();
        nome = produto.getNome();
        valor = produto.getValor();
        quantidade = produto.getQuantidadeNoEstoque();
        descricao = produto.getDescricao();
        localDateTime = produto.getLocalDateTime();
        categoria = produto.getCategoria();
        caracteristicas = produto.getCaracteristicas();
        usuario = produto.getUsuario();
        listaImagens = produto.getListaImagens();
    }

    private Long id;
    private String nome;
    private double valor;
    private int quantidade;
    private String descricao;
    private LocalDateTime localDateTime;

    private Set<Imagem> listaImagens;

    private Categoria categoria;
    private List<Caracteristicas> caracteristicas;
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<Caracteristicas> getCaracteristicas() {
        return caracteristicas;
    }

    public Long getUsuario() {
        return usuario.getId();
    }

    public Set<Imagem> getListaImagens() {
        return listaImagens;
    }
}
