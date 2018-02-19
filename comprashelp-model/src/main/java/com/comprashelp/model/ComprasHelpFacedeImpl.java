package com.comprashelp.model;

import com.comprashelp.model.persistence.ComprasHelpDAOFactory;
import com.comprashelp.model.persistence.genericFactory.Login.LoginDAO;
import com.comprashelp.model.persistence.interfaces.LoginDAOInterface;
import com.comprashelp.security.model.LoginVO;
import com.comprashelp.util.connection.ConnectionFactory;
import com.comprashelp.util.exceptions.ValidationException;
import java.sql.SQLException;

/**
 *
 * @author Ednilson Brito Lopes
 */
public class ComprasHelpFacedeImpl implements ComprasHelpFacede {

  @Override
  public void insertLogin(LoginVO login) throws SQLException, ValidationException {
    getLoginDAO().insert(ConnectionFactory.getConnection(), login);
  }

  private LoginDAOInterface getLoginDAO() {
    return new LoginDAO();
  }

}
