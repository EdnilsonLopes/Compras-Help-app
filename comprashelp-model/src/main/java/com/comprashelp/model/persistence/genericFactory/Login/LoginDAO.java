package com.comprashelp.model.persistence.genericFactory.Login;

import com.comprashelp.enumerations.TipoLogin;
import com.comprashelp.model.persistence.interfaces.LoginDAOInterface;
import com.comprashelp.security.model.LoginVO;
import com.comprashelp.util.connection.ConnectionFactory;
import com.comprashelp.util.exceptions.ValidationException;
import com.comprashelp.util.model.AbstractDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe responsável pela persistência de Login no banco. Tanto de Cadastro
 * como de carga e autenticação.
 *
 * @author Ednilson Brito Lopes.
 */
public class LoginDAO extends AbstractDAO<LoginVO> implements LoginDAOInterface{

  public static final String TABLE_PREFIX = "comprasHelpDB.";
  public static final String TABLE_NAME = "TB_LOGIN";

  public static final String COL_EMAIL = "EMAIL";
  public static final String COL_SENHA = "SENHA";
  public static final String COL_TELEFONTE = "TELEFONE";
  public static final String COL_TIPO = "TIPO";

  public static final String ALIAS = "login";

  @Override
  protected List<LoginVO> dbLoadAll() throws SQLException {
    String sql = sqlLoadAll();

    return loadListByStatement(sql);
  }

  private String sqlLoadAll() {
    StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_PREFIX
        + TABLE_NAME + " " + ALIAS);
    return sql.toString();
  }

  @Override
  protected LoginVO dbLoad(Object[] key) throws SQLException {
    String sql = sqlLoadAll();
    sql += " WHERE " + ALIAS + "." + COL_EMAIL + " = '" + key[0].toString() + "' ";

    return loadByStatment(sql);
  }

  private String sqlInsert() {
    StringBuilder sql = new StringBuilder("INSERT INTO "
        + TABLE_PREFIX + TABLE_NAME + " " + ALIAS);
    sql.append(" (")
        .append(ALIAS).append(".").append(COL_SENHA).append(", ")
        .append(ALIAS).append(".").append(COL_TELEFONTE).append(", ")
        .append(ALIAS).append(".").append(COL_TIPO).append(", ")
        .append(ALIAS).append(".").append(COL_EMAIL).append(") ")
        .append("VALUES (?, ?, ?, ?) ");

    return sql.toString();
  }

  @Override
  protected void dbInsert(Connection con, LoginVO obj) throws SQLException {
    String sql = sqlInsert();
    PreparedStatement ps = null;
    con = takeConnection();
    try {
      ps = con.prepareStatement(sql);
      setBindPreparedStatment(ps, obj);
      ps.executeQuery();
    } catch (SQLException e) {
      con.rollback();
      throw e;
    } finally {
      ConnectionFactory.closeConection(con, ps);
      con.commit();
    }
  }

  private String sqlUpdate() {
    StringBuilder sql = new StringBuilder("UPDATE " + TABLE_PREFIX + TABLE_NAME + " " + ALIAS);
    sql.append(" SET ")
        .append(ALIAS).append(".").append(COL_SENHA).append(" = ?, ")
        .append(ALIAS).append(".").append(COL_TELEFONTE).append(" = ?, ")
        .append(ALIAS).append(".").append(COL_TIPO).append(" = ? ")
        .append("WHERE ")
        .append(ALIAS).append(".").append(COL_EMAIL).append(" = ? ");

    return sql.toString();
  }

  @Override
  protected void dbUpdate(Connection con, LoginVO obj) throws SQLException {
    String sql = sqlUpdate();
    PreparedStatement ps = null;
    con = takeConnection();
    try {
      ps = con.prepareStatement(sql);
      setBindPreparedStatment(ps, obj);
      ps.executeUpdate();

    } catch (SQLException ex) {
      con.rollback();
      throw ex;
    } finally {
      ConnectionFactory.closeConection(con, ps);
      con.commit();
    }
  }

  @Override
  protected void dbDelete(Connection con, Object[] key) throws SQLException {
    String sql = "DELETE * FROM " + TABLE_PREFIX + TABLE_NAME + " " + ALIAS;
    sql += " WHERE " + ALIAS + "." + COL_EMAIL + " = " + key[0];
    PreparedStatement ps = null;
    con = takeConnection();
    try {
      ps = con.prepareStatement(sql);
      ps.executeQuery();
    } catch (SQLException e) {
      con.rollback();
      throw e;
    } finally {
      ConnectionFactory.closeConection(con, ps);
      con.commit();
    }
  }

  private int setBindPreparedStatment(PreparedStatement ps, LoginVO login) throws SQLException {
    int i = 1;

    ps.setString(i++, login.getSenha());
    ps.setString(i++, login.getTelefone());
    ps.setString(i++, login.getTipoLogin().getCodigo());
    ps.setString(i++, login.getEmail());

    return i;
  }

  @Override
  protected LoginVO rsToObject(ResultSet rs) throws SQLException {
    LoginVO login = new LoginVO();
    login.setEmail(rs.getString(COL_EMAIL));
    login.setSenha(rs.getString(COL_SENHA));
    login.setTelefone(rs.getString(COL_TELEFONTE));
    login.setTipoLogin(TipoLogin.getElementByCodigo(rs.getString(COL_TIPO)));

    return login;
  }

}
