package br.com.zupacademy.saulo.mercadolivre.categoria;

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
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
public class CadastraCategoriaTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_CATEGORIA_ENDPOINT = "/cadastrar-categoria";

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

    private String json(final String categoriaMae) {
        return "{\n" +
                "    \"nome\":\"Hardware\",\n" +
                "    \"nomeCategoriaMae\":\""+ categoriaMae +"\"\n" +
                "}";
    }

    @Test
    @Sql(statements = {"Insert into categoria values (1, 'Placa-Mãe', null)",
            "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')"})
    public void cadastrarCategoriaComMaeComSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_CATEGORIA_ENDPOINT)
                        .header("Authorization", "Bearer " + bearerToken )
                        .content(json("Placa-Mãe"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo( MockMvcResultHandlers.print() )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Hardware") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nomeCategoriaMae").value("Placa-Mãe") );
    }

    @Test
    @Sql(statements = "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')")
    public void cadastrarCategoriaSemMaeComSucesso() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_CATEGORIA_ENDPOINT)
                        .content(json(""))
                        .header("Authorization", "Bearer " + bearerToken )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nome").value("Hardware") )
                .andExpect( MockMvcResultMatchers.jsonPath("$.nomeCategoriaMae").value("") );
    }

    @Test
    @Sql(statements = {"Insert into categoria values (1, 'Hardware', null)",
            "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')"})
    public void cadastrarCategoriaJaExistente() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_CATEGORIA_ENDPOINT)
                        .content(json(""))
                        .header("Authorization", "Bearer " + bearerToken )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(400));
    }
}
