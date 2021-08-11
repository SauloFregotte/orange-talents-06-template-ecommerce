package br.com.zupacademy.saulo.mercadolivre.opniao;

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
public class CadastroOpniaoTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_OPNIAO_PRODUTO_ENDPOINT = "/cadastro-opniao/1";

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

    private String json(final double nota) {
        return "{\n" +
                "    \"nota\":" + nota + ",\n" +
                "    \"descricao\":\"Foi legal.\",\n" +
                "    \"titulo\":\"Bom\"\n" +
                "}";
    }

    @Test
    @Sql(statements = {"Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)"})
    public void opniaoCadastradaComSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_OPNIAO_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json(3.0))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Bom"));
    }

    @Test
    @Sql(statements = {"Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)"})
    public void opniaoComNotaAcimaDe5() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_OPNIAO_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json(9.0))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }

    @Test
    @Sql(statements = {"Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)"})
    public void opniaoComNotaAbaixoDe1() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_OPNIAO_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json(0.0))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }
}
