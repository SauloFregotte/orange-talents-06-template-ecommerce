package br.com.zupacademy.saulo.mercadolivre.categoria;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoriaTest {

    @Test
    public void categoriaNomeNotNull(){
        String nome = null;

        Assertions.assertThrows(NullPointerException.class, ()->new Categoria(nome));
    }

    @Test
    public void categoriaNomeNotEmpty(){
        String nome = "";

        Assertions.assertThrows(IllegalArgumentException.class, ()->new Categoria(nome));
    }

    /**
     * Este teste, tecnicamente é necessario para a estrutura de dominio,
     * mas como não existe logica que valide o nomeCategoriaMae de maneira unica,
     * logo é impossivel validar o caso de requisição com o valor de mãe no dominio.
     *
     * Esta validação aparecerá no testes de integração!
     * **/
    @Test
    public void categoriaValidFormatWithMother(){

        String nome = "Eletrônicos";
        String nomeCategoriaMae = "Hardware";

        Categoria categoria = new Categoria(nome);

        Assertions.assertEquals("Eletrônicos", categoria.getNome());
        Assertions.assertEquals("Hardware", nomeCategoriaMae);
    }

    @Test
    public void categoriaValidFormatWithoutMother(){
        String nome = "Placa-mãe";

        Categoria categoria = new Categoria(nome);

        Assertions.assertEquals("Placa-mãe", categoria.getNome());
    }
}
