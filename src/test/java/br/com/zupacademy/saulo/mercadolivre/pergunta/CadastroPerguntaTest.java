package br.com.zupacademy.saulo.mercadolivre.pergunta;

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
public class CadastroPerguntaTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_PERGUNTA_PRODUTO_ENDPOINT = "/cadastro-pergunta-produto/1";

    private String bearerToken;

    @BeforeEach
    void autenticacao() throws Exception {
        String endpoint = "/auth-login";
        String content = "{\"email\":\"joaninha@gmail.com\",\"senha\":\"123456\"}";

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

    private String json() {
        return "{\n" +
                "    \"titulo\":\"Oque o seu produto faz?\"\n" +
                "}";
    }

    @Test
    @Sql(statements = {"Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into usuario Values (2,'joaninha@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)"})
    public void cadastroPerguntaSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_PERGUNTA_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }
}
