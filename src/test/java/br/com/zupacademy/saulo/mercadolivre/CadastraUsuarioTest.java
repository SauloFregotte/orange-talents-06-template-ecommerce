package br.com.zupacademy.saulo.mercadolivre;

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
public class CadastraUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarUsuarioComSucesso() throws Exception {
        URI uri = new URI("/cadastrar-usuario");
        String json = "{\n" +
                "    \"email\":\"lulo123@gmail.com\",\n" +
                "    \"senha\":\"123456\"\n" +
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

//    @Test
//    public void cadastrarUsuarioEmailDuplicado() throws Exception {
//        URI uri = new URI("/cadastrar-usuario");
//        String json = "{\n" +
//                "    \"email\":\"lulo123@gmail.com\",\n" +
//                "    \"senha\":\"123456\"\n" +
//                "}";
//
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post(uri)
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers
//                        .status()
//                        .is(400));
//    }
}
