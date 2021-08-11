package br.com.zupacademy.saulo.mercadolivre.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext( classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD )
public class CadastraUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String CADASTRAR_USUARIO_ENDPOINT = "/cadastrar-usuario";

    private String json(){
        return "{\n" +
                "    \"email\":\"lulo123@gmail.com\",\n" +
                "    \"senha\":\"123456\"\n" +
                "}";
    }

    @Test
    @Sql(statements = "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')")
    public void cadastrarUsuarioComSucesso() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(CADASTRAR_USUARIO_ENDPOINT)
                                .content(json())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.jsonPath("$.id").value(1L) );
    }

    @Test
    @Sql(statements = "Insert into usuario Values (1,'lulo123@gmail.com', '2017-01-16 00:00:00', '$2a$10$mCxKN/mh2ootRbx0PVnqye3mQhacpUebaQHm01YuIivKsug3ox7BS')")
    public void cadastrarUsuarioEmailDuplicado() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(CADASTRAR_USUARIO_ENDPOINT)
                        .content(json())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }
}
