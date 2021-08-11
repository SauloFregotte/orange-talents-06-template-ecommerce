package br.com.zupacademy.saulo.mercadolivre.opniao.entidade;

public class OpniaoResponse {

    public OpniaoResponse(Opniao opniao) {
        this.id = opniao.getId();
        this.nota = opniao.getNota();
        this.titulo = opniao.getTitulo();
        this.descricao = opniao.getDescricao();
        this.produto = opniao.getProduto();
        this.usuario = opniao.getUsuario();
    }

    private Long id;
    private double nota;
    private String titulo;
    private String descricao;
    private Long produto;
    private Long usuario;

    public Long getId() {
        return id;
    }

    public double getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getProduto() {
        return produto;
    }

    public Long getUsuario() {
        return usuario;
    }
}
