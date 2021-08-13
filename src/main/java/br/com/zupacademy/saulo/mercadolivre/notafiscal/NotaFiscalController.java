package br.com.zupacademy.saulo.mercadolivre.notafiscal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotaFiscalController {

    @PostMapping("/nf/{idCompra}/{idComprador}")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody
    String gerarNotaFiscal(@PathVariable("idCompra") final Long idCompra,
                           @PathVariable("idComprador") final Long idComprador){
        return "Criando NF para compra " + idCompra + " com comprador " + idComprador;
    }
}
