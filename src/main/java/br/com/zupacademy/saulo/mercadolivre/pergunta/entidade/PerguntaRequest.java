package br.com.zupacademy.saulo.mercadolivre.pergunta.entidade;

import br.com.zupacademy.saulo.mercadolivre.pergunta.RepositoryPerguntaJPA;
import br.com.zupacademy.saulo.mercadolivre.produto.entidade.Produto;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;

public class PerguntaRequest {

    private String titulo;

    public PerguntaResponse associarOpniaoProduto(final RepositoryPerguntaJPA repositoryPerguntaJPA,
                                                  final Usuario userLogged, final Produto produto){
        return new PerguntaResponse(
                new Pergunta(titulo, produto, userLogged)
                        .cadastrar(repositoryPerguntaJPA)
        );
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
