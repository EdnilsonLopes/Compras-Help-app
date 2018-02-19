package com.comprashelp.util.model;

import com.comprashelp.util.ValueObject;
import com.comprashelp.util.connection.ConnectionFactory;
import com.comprashelp.util.exceptions.ValidationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Ponto de Partida para a criação de Data Access Objects.
 *
 * @author Ednilson Brito Lopes
 * @param <T>
 */
public abstract class AbstractDAO<T extends ValueObject> implements DataAccessObject<T>{

  /**
   * Tipo da entidade de neg�cio ({@link ValueObject}) manipulada por esse DAO.
   * Inicialmente esse tipo � obtido via reflex�o. Para isso, � necess�rio que a
   * classe que especializa esta informe o tipo que est� manipulando. Exemplo:
   * <br />
   * <code>
   *   public class PessoaDAO extends AbstractDAO<PessoaVO>{};
   * </code>
   */
  private Class<?> type;

  public AbstractDAO() {
    this.type = this.getClass();
  }

  public AbstractDAO(Class<T> type) {
    this();
    this.type = type;
  }

  /**
   * Carrega todos os objetos do banco de dados.
   *
   * @return
   * @throws java.sql.SQLException
   */
  protected abstract List<T> dbLoadAll()
      throws SQLException;

  /**
   * Carrega um objeto do banco de dados, dados seus identificadores.
   *
   * @param key identificadores do objeto que ser� carregado.
   * @return objeto carregado ou null caso um objeto com os identificadores
   * fornecidos n�o seja encontrado.
   * @throws java.sql.SQLException
   */
  protected abstract T dbLoad(Object[] key)
      throws SQLException;

  /**
   * Insere um objeto no banco de dados.
   *
   * @param con Conecção com o banco de dados.
   * @param obj objeto a ser inserido no banco.
   * @throws java.sql.SQLException
   */
  protected abstract void dbInsert(Connection con, T obj)
      throws SQLException;

  /**
   * Salva um objeto no banco de dados.
   *
   * @param con Conecção com o banco de dados.
   * @param obj objeto a ser salvo no banco.
   * @throws java.sql.SQLException
   */
  protected abstract void dbUpdate(Connection con, T obj)
      throws SQLException;

  /**
   * Exclui um objeto do banco de dados.
   *
   * @param con Conecção com o banco de dados.
   * @param key Chave do objeto a ser excluído do banco.
   * @throws java.sql.SQLException
   */
  protected abstract void dbDelete(Connection con, Object[] key)
      throws SQLException;

  /**
   * Gera uma exceção empacotando todos os erros encontrados caso um objeto não
   * seja válido para persistência.
   *
   * @param con Conexão com o banco de dados.
   * @param obj Objeto a ser validado.
   * @throws java.sql.SQLException
   * @throws com.comprashelp.util.exceptions.ValidationException
   */
  protected void validate(Connection con, T obj)
      throws SQLException, ValidationException {
  }

  /**
   * Retorna uma array a ser utilizado como complemento na identificação do
   * objeto no {@link Cache}. Por padrão esse método retorna um array vazio.
   *
   * @return Um array de {@link Object} vazio.
   */
  protected Object[] cacheKey() {
    return new Object[0];
  }

  /**
   * Define se o objeto possui a chave definida. Para isso, � analisado se todos
   * os elementos do array retornado por keyOf(obj) s�o diferentes de null.
   *
   * @param obj objeto cuja chave ser� analisada.
   * @return True se o objeto possuir a chave completa definida, False caso
   * contr�rio.
   */
  protected boolean hasKey(T obj) {
    Object[] keyArray = obj.keyOf();
    if (keyArray == null) {
      return true;
    }
    for (Object key : keyArray) {
      if (key == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * Conclui a carga de um objeto do banco de dados.
   *
   * @param obj objeto que tem a chave para ser carregado por completo.
   * @return Retorna um objeto totalmente carregado.
   * @throws java.sql.SQLException
   */
  protected T dbFullLoad(T obj)
      throws SQLException {
    // implementação não obrigatória.
    return obj;
  }

  /**
   * Cria um objeto (ValueObject) a partir da linha corrente do result set.
   *
   * @param rs result set cuja linha atual será usada para produzir o objeto.
   * @return Retorna um objeto.
   * @throws java.sql.SQLException
   */
  protected abstract T rsToObject(ResultSet rs)
      throws SQLException;

  protected List<T> loadListByStatement(String sqlStatement) throws SQLException {
    Connection con = null;
    try {
      con = takeConnection();
      return loadListByStatement(con, sqlStatement);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Constrói uma lista com todos os objetos associados a um sql statement.
   * Executa a query, constrói os objetos e completa a carga de cada um deles.
   *
   * @param con Conecção com o banco de dados que precisa ser iniciada.
   * @param sqlStatement Instrução sql de carga.
   * @return uma Lista de objetos carregados ou null se não tiver objetos.
   * @throws SQLException
   */
  protected List<T> loadListByStatement(Connection con, String sqlStatement) throws SQLException {
    List<T> result = new LinkedList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sqlStatement);

      rs = ps.executeQuery();
      while (rs.next()) {
        T obj = rsToObject(rs);
        obj.setPersisted(true);
        result.add(obj);
      }
    } catch (SQLException e) {
      throw e;
    } finally {
      ConnectionFactory.closeConection(con, ps, rs);
    }
    return result;
  }

  /**
   * Carrega um objeto de acordo com o sql.
   *
   * @param sqlStatement
   * @return
   */
  protected T loadByStatment(String sqlStatement) throws SQLException {
    List<T> list = loadListByStatement(sqlStatement);

    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  @Override
  public void insert(Connection con, T obj) throws SQLException, ValidationException {
    dbInsert(con, obj);
  }

  @Override
  public void update(Connection con, T obj) throws SQLException, ValidationException {
    dbUpdate(con, obj);
  }
  

  public Connection takeConnection() throws SQLException {
    return ConnectionFactory.getConnection();
  }

}
