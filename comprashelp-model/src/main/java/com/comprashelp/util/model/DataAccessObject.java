package com.comprashelp.util.model;

import com.comprashelp.util.ValueObject;
import com.comprashelp.util.exceptions.ValidationException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface que estabelece as operações básicas sobre um repositório de
 * objetos.
 *
 * @author Ednilson Brito Lopes
 * @param <T>
 */
public interface DataAccessObject<T extends ValueObject> {

  public abstract void insert(Connection con, T obj)
      throws SQLException, ValidationException;
  
  public abstract void update(Connection con, T obj)
    throws SQLException, ValidationException;
}
