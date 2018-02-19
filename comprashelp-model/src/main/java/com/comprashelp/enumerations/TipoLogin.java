package com.comprashelp.enumerations;

/**
 * Enumeração do tipo de login ou cadastro.
 *
 * @author Ednilson Brito Lopes
 */
public enum TipoLogin {
  FORNECEDOR("F"),
  CLIENTE("C");

  private final String codigo;

  private TipoLogin(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

  /**
   * Recebe um código e retorna o tipo de detalhamento desse cógigo.
   *
   * @param codigo Código do tipo de detalhamento.
   * @return retorna o tipo de retalhamento.
   */
  public static TipoLogin getElementByCodigo(String codigo) {
    if (codigo != null) {
      for (TipoLogin detalhamento : values()) {
        if (codigo.equalsIgnoreCase(detalhamento.codigo)) {
          return detalhamento;
        }

      }
    }
    return null;
  }

}
