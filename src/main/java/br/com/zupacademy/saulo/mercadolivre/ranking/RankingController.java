package br.com.zupacademy.saulo.mercadolivre.ranking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RankingController {

    @PostMapping("/ranking/{idCompra}/{idComprador}")
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody
    String gerarRanking(@PathVariable("idCompra") final Long idCompra, @PathVariable("idComprador") final Long idComprador){
        return "Criando Ranking para compra " + idCompra + " com comprador " + idComprador;
    }
}
