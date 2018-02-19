package com.comprashelp.security.model;

import com.comprashelp.enumerations.TipoLogin;
import com.comprashelp.util.ValueObject;
import com.comprashelp.util.exceptions.ValidationError;
import com.comprashelp.util.exceptions.ValidationException;

/**
 * Classe responsável por tratar um Login.
 *
 * @author Ednilson Brito Lopes
 */
public class LoginVO extends ValueObject {

  private String email;

  private String senha;

  private String telefone;

  private TipoLogin tipoLogin;

  private String emailConfirm;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public TipoLogin getTipoLogin() {
    return tipoLogin;
  }

  public void setTipoLogin(TipoLogin tipoLogin) {
    this.tipoLogin = tipoLogin;
  }

  public String getEmailConfirm() {
    return emailConfirm;
  }

  public void setEmailConfirm(String emailConfirm) {
    this.emailConfirm = emailConfirm;
  }

  @Override
  public Object[] keyOf() {
    return new Object[]{getEmail()};
  }

  @Override
  public int hashCode() {
    if (email.isEmpty()) {
      return super.hashCode();
    }
    return email.length();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final LoginVO other = new LoginVO();
    if (this.email != other.email) {
      return false;
    }
    return true;
  }

  @Override
  public void validate() throws ValidationException {
    ValidationException ve = new ValidationException();
    if (email.isEmpty()) {
      ve.addError(new ValidationError("Preencha o campo de E-mail!"));
    } else if (!email.equals(emailConfirm)) {
      ve.addError(new ValidationError("O E-mail de confirmação não está idéntico!"));
    }
    if (senha.isEmpty()) {
      ve.addError(new ValidationError("Campo de senha não preenchido!"));
    }
    if (telefone.isEmpty()) {
      ve.addError(new ValidationError("Campo de Telefonte não preenchido!"));
    }
    if (tipoLogin == null) {
      ve.addError(new ValidationError("Escolha seu tipo de Usuário!"));
    }
  }

}
