/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.model.persistence;

import com.comprashelp.model.persistence.genericFactory.Login.LoginDAO;
import com.comprashelp.model.persistence.interfaces.LoginDAOInterface;
import com.comprashelp.util.model.AbstractDAO;

/**
 *
 * @author ednilson-pc
 */
public class ComprasHelpGenericDAOFactory extends ComprasHelpDAOFactory{

  @Override
  public LoginDAOInterface getLoginDAO() {
    return prepareDAO(new LoginDAO());
  }
  /**
   * Inicializações gerais de um DAO.
   * @param <T> Tipo de DAO que será preparado.
   * @param dao dao que será preparado.
   * @return a instância do DAO devidamente preparada.
   */
  protected <T extends AbstractDAO> T prepareDAO(T dao) {
    
    return dao;
  }
  
}
