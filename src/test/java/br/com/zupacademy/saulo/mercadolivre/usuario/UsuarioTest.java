package br.com.zupacademy.saulo.mercadolivre.usuario;

import br.com.zupacademy.saulo.mercadolivre.categoria.entidade.Categoria;
import br.com.zupacademy.saulo.mercadolivre.usuario.entidade.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;

public class UsuarioTest {

  @Test
    public void usuarioEmailNotNull(){
      String email = null;
      String senha = "123456";

      Assertions.assertThrows(NullPointerException.class, ()->new Usuario(email, senha));
  }

  @Test
  public void usuarioSenhaNotNull(){
    String email = "joaninha123@gmail.com";
    String senha = null;

    Assertions.assertThrows(NullPointerException.class, ()->new Usuario(email, senha));
  }

  @Test
  public void usuarioEmailNotEmpty(){
    String email = "";
    String senha = "123456";

    Assertions.assertThrows(IllegalArgumentException.class, ()->new Usuario(email, senha));
  }

  @Test
  public void usuarioSenhaNotEmpty(){
    String email = "joaninha123@gmail.com";
    String senha = "";

    Assertions.assertThrows(IllegalArgumentException.class, ()->new Usuario(email, senha));
  }
  /**
   * Este teste precisa existir aqui,
   * ja que um regex esta sendo aplicado no email, na classe Usuario.
  **/
  @Test
  public void usuarioEmailInvalidFormat(){
    String email = "vaer@brt";
    String senha = "123456";

    Assertions.assertThrows(IllegalArgumentException.class, ()->new Usuario(email, senha));
  }
// Teste nao pode ser realizado dado que a validacao de formato de senha esta no DTO de request
//  @Test
//  public void usuarioSenhaInvalidFormat(){
//    String email = "joaninha123@gmail.com";
//    String senha = "12345";
//
//    Assertions.assertThrows(IllegalArgumentException.class, ()->new Usuario(email, senha));

//  }

  @Test
  public void usuarioValidFormat(){
    String email = "joaninha123@gmail.com";
    String senha = "123456";

    Usuario usuario = new Usuario(email, senha);

    Assertions.assertEquals("joaninha123@gmail.com", usuario.getEmail());
    Assertions.assertEquals("123456", usuario.getSenha());
  }

}
