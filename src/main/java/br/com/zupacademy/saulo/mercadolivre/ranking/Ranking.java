package br.com.zupacademy.saulo.mercadolivre.ranking;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8080", name = "ranking")
public interface Ranking {

    default void processa(final Compra compra){
        System.out.println( processa( compra.getId(), compra.getBuyer().getId() ) );
    }

    @PostMapping("/ranking/{idCompra}/{idComprador}")
    String processa(@PathVariable("idCompra") final Long idCompra,
                    @PathVariable("idComprador") final Long idComprador );

}
