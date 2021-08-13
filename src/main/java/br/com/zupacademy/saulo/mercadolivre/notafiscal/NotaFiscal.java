package br.com.zupacademy.saulo.mercadolivre.notafiscal;

import br.com.zupacademy.saulo.mercadolivre.pagamento.compra.entidade.Compra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8080", name = "nf")
public interface NotaFiscal {

    default void processa(final Compra compra){
        System.out.println( processa( compra.getId(), compra.getBuyer().getId() ) );
    }

    @PostMapping("/nf/{idCompra}/{idComprador}")
    String processa(@PathVariable("idCompra") final Long idCompra, @PathVariable("idComprador") final Long idComprador );

}
