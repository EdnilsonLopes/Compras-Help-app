package com.comprashelp.model.persistence;

import com.comprashelp.model.persistence.interfaces.LoginDAOInterface;


/**
 * Esta classe tem duas importantes responsabilidades: servir como ponto de
 * partida para implementação de fábricas de DAO's e retornar a fábrica de
 * DAO's.
 *
 * @author Ednilson Brito Lopes
 */
public abstract class ComprasHelpDAOFactory {

  public ComprasHelpDAOFactory() {
  }
  
  /**
   * @return O DAO para notícias.
   */
  public abstract LoginDAOInterface getLoginDAO();
  
}
