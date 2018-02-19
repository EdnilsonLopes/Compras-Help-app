package com.comprashelp.util.viewcontroller;

import com.comprashelp.model.ComprasHelpFacede;
import com.comprashelp.model.ComprasHelpFacedeImpl;

/**
 * Classe responsável por retornar uma fachada de serviço para um cliente.
 *
 * Utilizar esta classe como uma factory de fachadas nos dá a opção de a
 * qualquer momento substituirmos a implementação da fachada sem impacto algun
 * no código cliente.
 *
 * @author Ednilson Brito Lopes
 */
public class ComprasHelpFacedeManager {

  public static ComprasHelpFacede getFacade() {
    return new ComprasHelpFacedeImpl();
  }
}
