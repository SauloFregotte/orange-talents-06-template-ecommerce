package br.com.zupacademy.saulo.mercadolivre.categoria;

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
public class CadastraCategoriaTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarCategoriaComMaeComSucesso() throws Exception {
        URI uri = new URI("/cadastrar-categoria");
        String json = "{\n" +
                "    \"nome\":\"Placa-Mãe\",\n" +
                "    \"nomeCategoriaMae\":\"Hardware\"\n" +
                "}";

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
    public void cadastrarCategoriaSemMaeComSucesso() throws Exception {
        URI uri = new URI("/cadastrar-categoria");
        String json = "{\n" +
                "    \"nome\":\"Eletrônicos\",\n" +
                "    \"nomeCategoriaMae\":\"\"\n" +
                "}";

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
    public void cadastrarCategoriaJaExistente() throws Exception {
        URI uri = new URI("/cadastrar-categoria");
        String json = "{\n" +
                "    \"nome\":\"Eletrônicos\",\n" +
                "    \"nomeCategoriaMae\":\"\"\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));
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
