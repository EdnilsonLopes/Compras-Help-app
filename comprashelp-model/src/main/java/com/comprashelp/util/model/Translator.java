package com.comprashelp.util.model;

/**
 * Algo que tenha a capacidade de traduzir.
 */
public interface Translator {

  /**
   * @param str string a ser traduzido.
   * @param replaceValues valores a serem substitu�dos na tradu��o. Quando null,
   * nenhuma subsittui��o � realizada.
   * @return o string traduzido.
   */
  public String translate(String str, String... replaceValues);

}
