/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.util.model;

/**
 * Algo que possa ser traduzido.
 */
public interface Translatable {

  /**
   * Traduz o objeto.
   * @param translator objeto capaz de traduzir um string
   * @return o objeto resultante da tradu��o.
   */
  public Object translate (Translator translator);
  
  /**
   * @return o nome base do resource bundle, um nome de classe completamente 
   * qualificado. Se retornar null, nenhuma tradu��o � realizada.
   */
  public String getBaseName();
  
}
