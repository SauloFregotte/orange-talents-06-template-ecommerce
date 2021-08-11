package br.com.zupacademy.saulo.mercadolivre.produto;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CadastraProdutoTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_PRODUTO_ENDPOINT = "/cadastrar-produto";

    private String bearerToken;

    @BeforeEach
    void autenticacao() throws Exception {
        String endpoint = "/auth-login";
        String content = "{\"email\":\"lulo123@gmail.com\",\"senha\":\"123456\"}";

        bearerToken = JsonPath.read(mockMvc.perform(
                MockMvcRequestBuilders
                        .post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(), "$.token");
    }

    private String json(final String categoria) {
        return "{\n" +
                "    \"nome\":\"Produto Gasolina\",\n" +
                "    \"valor\":20.00,\n" +
                "    \"quantidade\":30,\n" +
                "    \"descricao\":\"Combustivel para automoveis\",\n" +
                "    \"categoria\":\"" + categoria + "\",\n" +
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
    }

    @Test
    @Sql(statements = {"Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into categoria values (1, 'testeCategoria', null)"})
    public void cadastrarProdutoComCaracteristicasCategoriasComSucesso() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json("testeCategoria"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Produto Gasolina"));
    }

    @Test
    @Sql(statements = {"Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into categoria values (1, 'testeCategoria', null)"})
    public void cadastrarProdutoComCategoriaInvalida() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json("CATEGORIA ERRADA"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }

    @Test
    @Sql(statements = "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')")
    public void cadastrarProdutoComCaracteristicasMenoresQue3() throws Exception {
        String jsonCom1Caracteristicas = "{\n" +
                "    \"nome\":\"Produto Gasolina\",\n" +
                "    \"valor\":20.00,\n" +
                "    \"quantidade\":30,\n" +
                "    \"descricao\":\"Combustivel para automoveis\",\n" +
                "    \"categoria\":\"testeCategoria\",\n" +
                "    \"caracteristicas\":[\n" +
                "        {\n" +
                "            \"nome\":\"Teste descricao1\",\n" +
                "            \"descricao\":\"uncduhrfbvuehfv\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(jsonCom1Caracteristicas)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }
}
