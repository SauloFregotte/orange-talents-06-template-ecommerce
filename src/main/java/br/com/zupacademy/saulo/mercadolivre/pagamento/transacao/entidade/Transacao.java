package br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.entidade;

import br.com.zupacademy.saulo.mercadolivre.pagamento.transacao.StatusTransacao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
public class Transacao {

    public Transacao(){}

    public Transacao(final StatusTransacao statusTransacao, final String idTransacao) {
        this.statusTransacao = statusTransacao;
        this.idTransacao = idTransacao;
    }

    public boolean transacaoIgual( final Transacao transacao ){
        return this.idTransacao.equals(transacao.idTransacao);
    }

    public boolean concluidaComSucesso(){
        return statusTransacao.equals(StatusTransacao.SUCESSO);
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Enumerated( EnumType.STRING )
    private StatusTransacao statusTransacao;

    @NotBlank
    private String idTransacao;

    @NotNull
    private final Instant localDateTime = Instant.now();

    public Long getId() {
        return id;
    }

    public StatusTransacao getStatusTransacao() {
        return statusTransacao;
    }
}
