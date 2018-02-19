package com.comprashelp.model;

import com.comprashelp.security.model.LoginVO;
import com.comprashelp.util.exceptions.ValidationException;
import java.sql.SQLException;

/**
 *
 * @author Ednilson Brito Lopes
 */
public interface ComprasHelpFacede {
  //TODO Definir as funcionalidades existentes na app;
  
  public void insertLogin(LoginVO login) throws SQLException, ValidationException;
}
