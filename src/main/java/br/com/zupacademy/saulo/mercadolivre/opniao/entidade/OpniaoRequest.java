package br.com.zupacademy.saulo.mercadolivre.opniao.entidade;

import br.com.zupacademy.saulo.mercadolivre.opniao.RepositoryOpniaoJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class OpniaoRequest {

    @Min(1)
    @Max(5)
    private Double nota;
    @Size(max = 500)
    private String descricao;
    private String titulo;

    public OpniaoResponse associarOpniaoProduto(RepositoryOpniaoJPA repositoryOpniaoJPA,
                                                Usuario userLogged, Produto produto) {
        return new OpniaoResponse(
                new Opniao(nota, titulo, descricao, produto, userLogged)
                        .cadastrar(repositoryOpniaoJPA)
        );
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
