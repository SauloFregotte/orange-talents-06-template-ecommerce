package br.com.zupacademy.saulo.mercadolivre.pagamento.compra;

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
public class CadastroCompraTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_COMPRA_PRODUTO_ENDPOINT = "/compra";

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

    private String json(String tipoDePagamento){
        return "{\n" +
                "    \"quantidade\":3,\n" +
                "    \"produto\":1,\n" +
                "    \"tipoDePagamento\":\"" + tipoDePagamento + "\"\n" +
                "}";
    }

    @Test
    @Sql(statements = {"Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)",
            "Insert into caracteristicas Values (1, 'Teste descricao1', 'nome1',1)",
            "Insert into caracteristicas Values (2, 'Teste descricao2', 'nome2',1)",
            "Insert into caracteristicas Values (3, 'Teste descricao3', 'nome3',1)"})
    public void cadastroCompraPaypalComSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_COMPRA_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json("PAY_PAL"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.compra.id").value(1L))
                .andExpect( MockMvcResultMatchers.jsonPath("$.gatewayPagamento").value("paypal.com?buyerId=1&redirectUrl=/paypal/1") );
    }

    @Test
    @Sql(statements = {"Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')",
            "Insert into categoria values (1, 'testeCategoria', null)",
            "Insert into produto values (1, 'descricao produto', '2017-01-16 00:00:00', 'nomeProduto', 23, 50.00, 1, 1)",
            "Insert into caracteristicas Values (1, 'Teste descricao1', 'nome1',1)",
            "Insert into caracteristicas Values (2, 'Teste descricao2', 'nome2',1)",
            "Insert into caracteristicas Values (3, 'Teste descricao3', 'nome3',1)"})
    public void cadastroCompraPagueseguroComSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_COMPRA_PRODUTO_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(json("PAG_SEGURO"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.compra.id").value(1L))
                .andExpect( MockMvcResultMatchers.jsonPath("$.gatewayPagamento").value("pagseguro.com?returnId=1&redirectUrl=/pagseguro/1") );
    }
}
