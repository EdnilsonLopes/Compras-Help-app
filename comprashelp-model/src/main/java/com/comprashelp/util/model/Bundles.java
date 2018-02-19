/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.util.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * Gerente para resource bundles indexados pelo locale e path.
 * O locale utilizado para encontrar o bundle adequado � extra�do do FacesContext.
 * Isto permite ao usu�rio trocar a linguagem do browser a qualquer
 * momento e a aplica��o responder� imediatamente com as devidas tradu��es.
 *
 * Eventualmente esta classe pode ser movida para um pacote de utilit�rios.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class Bundles {

  private static Map<String, ResourceBundle> _map =
    new HashMap<String, ResourceBundle>();
  private static Map<String, String> _mapBundles =
    new HashMap<String, String>();

  /**
   * Retorna o valor associado a uma chave dentro de um arquivo de propriedades.
   * Se a chave n�o existir no arquivo retorna a pr�pria chave.
   * Este m�todo n�o identifica o arquivo de recusos a partir da chave. Utiliza
   * o par�metro resourceLocation para isso.
   * @param context Contexto que d� acesso ao locale a ser utilizado.
   * @param resourceLocation A localiza��o do arquivo ResourceBundle
   * (pacote.NomeArquivo).
   * @param key A chave que permite identificar o valor no arquivo de propriedades.
   * @return o valor associado � chave
   */
  public static String getValue(Locale locale,
                                   String resourceLocation, String key) {

    if (locale == null) {
      locale = Locale.getDefault();
    }

    // Tenta buscar o recurso j� carregado no mapa
    String resourceMapKey =
      locale.getLanguage() + "_" + locale.getCountry() + "_" +
      resourceLocation;
    ResourceBundle res = _map.get(resourceMapKey);
    if (res == null) {
      res = ResourceBundle.getBundle(resourceLocation, locale);
      _map.put(resourceMapKey, res);
    }
    try {
      return res.getString(key);
    }
    catch (Exception e) {
      return key;
    }
  }

  public static String getValue(Locale locale,
                                   String resourceLocation, String key,
                                   String[] replaceValues) {
    String value = getValue(locale, resourceLocation, key);
    if (replaceValues != null) {
      for (int i = 0; i < replaceValues.length; i++) {
        if (replaceValues[i] != null) {
          value = StringUtils.replace(value, "{" + (i + 1) + "}", replaceValues[i]);
        }
      }
    }
    return value;
  }

  public static void setMapBundles(Map<String, String> _mapBundles) {
    Bundles._mapBundles = _mapBundles;
  }

  public static Map<String, String> getMapBundles() {
    return _mapBundles;
  }
  
}
