package br.com.zupacademy.saulo.mercadolivre.produto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
public class CadastraProdutoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarProdutoComCaracteristicasCategoriasComSucesso() throws Exception {
        URI uriCategoria = new URI("/cadastrar-categoria");
        String jsonCategoria = "{\n" +
                "    \"nome\":\"testeCategoria\",\n" +
                "    \"nomeCategoriaMae\":\"\"\n" +
                "}";
        URI uri = new URI("/cadastrar-produto");
        String json = "{\n" +
                "    \"nome\":\"Produto Gasolina\",\n" +
                "    \"valor\":20.00,\n" +
                "    \"quantidade\":30,\n" +
                "    \"descricao\":\"Combustivel para automoveis\",\n" +
                "    \"categoria\":\"testeCategoria\",\n" +
                "    \"caracteristicas\":[\n" +
                "        {\n" +
                "            \"nome\":\"Teste descricao1\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        },\n" +
                "         {\n" +
                "            \"nome\":\"Teste descricao2\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        },\n" +
                "          {\n" +
                "            \"nome\":\"Teste descricao3\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uriCategoria)
                        .content(jsonCategoria)
                        .contentType(MediaType.APPLICATION_JSON));
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void cadastrarProdutoComCategoriaInvalida()throws Exception{
        URI uri = new URI("/cadastrar-produto");
        String json = "{\n" +
                "    \"nome\":\"Produto Gasolina\",\n" +
                "    \"valor\":20.00,\n" +
                "    \"quantidade\":30,\n" +
                "    \"descricao\":\"Combustivel para automoveis\",\n" +
                "    \"categoria\":\"CATEGORIA ERRADA\",\n" +
                "    \"caracteristicas\":[\n" +
                "        {\n" +
                "            \"nome\":\"Teste descricao1\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        },\n" +
                "         {\n" +
                "            \"nome\":\"Teste descricao2\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        },\n" +
                "          {\n" +
                "            \"nome\":\"Teste descricao3\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }

    @Test
    public void cadastrarProdutoComCaracteristicasMenoresQue3()throws Exception{
        URI uri = new URI("/cadastrar-produto");
        String json = "{\n" +
                "    \"nome\":\"Produto Gasolina\",\n" +
                "    \"valor\":20.00,\n" +
                "    \"quantidade\":30,\n" +
                "    \"descricao\":\"Combustivel para automoveis\",\n" +
                "    \"categoria\":\"testeCategoria\",\n" +
                "    \"caracteristicas\":[\n" +
                "        {\n" +
                "            \"nome\":\"Teste descricao1\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        }\n"+
                "    ]\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }
}
