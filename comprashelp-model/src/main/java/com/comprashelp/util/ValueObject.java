package com.comprashelp.util;

import com.comprashelp.util.exceptions.KeyOfNotSupportException;
import com.comprashelp.util.exceptions.ValidationException;
import java.io.Serializable;

/**
 * Classe que serve como base para todos os value objects.
 *
 * @author Ednilson Brito Lopes
 */
public class ValueObject implements Serializable, Cloneable {

  /**
   * Serial code version <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = -3064039631346061586L;

  private static final long INITIAL_VERSION = 1L;

  boolean persisted;
  int parentIndex = -1;
  boolean readOnly = false;
  long version = INITIAL_VERSION;

  private Object cacheKey = "";

  private boolean completed;

  public ValueObject() {
  }

  public ValueObject(ValueObject that) {

    this.persisted = that.getPersisted();
    this.parentIndex = that.getParentIndex();
    this.readOnly = that.isReadOnly();
    this.version = that.version;

    this.cacheKey = that.cacheKey();
    this.completed = that.isCompleted();
  }

  /**
   * Determina se o objeto j� foi persistido alguma vez no banco de dados.
   */
  public boolean isPersisted() {
    return persisted;
  }

  /**
   * Compatibilide com UIX.
   */
  public boolean getPersisted() {
    return persisted;
  }

  public void setPersisted(boolean newPersisted) {
    persisted = newPersisted;
  }

  /**
   * Quando o objeto for parte de um objeto maior, retorna o índice ocupado na
   * coleção.
   */
  public int getParentIndex() {
    return parentIndex;
  }

  public void setParentIndex(int newParentIndex) {
    parentIndex = newParentIndex;
  }

  /**
   * Permite o controle da alteração de um objeto. Esta propriedade é aqui
   * definida para fins de padronização, mas o controle de atualização do objeto
   * não é feito por esta classe.
   */
  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean newReadOnly) {
    readOnly = newReadOnly;
  }

  /**
   * Inteiro que representa a versão de um objeto.
   */
  public long getVersion() {
    return version;
  }

  /**
   * Inteiro que representa a próxima versão de um objeto.
   */
  public long getNextVersion() {
    return version + 1;
  }

  public void setVersion(long newVersion) {
    version = newVersion;
  }

  /**
   * Retorna um objeto que é a identificação do objeto no cache. O método é
   * private pois diz respeito apenas ao cache e trata-se de uma informação que
   * poderia ser adicionada em tempo de execução (AOP, CGLIB, etc.).
   *
   * @return A identificação do objeto no cache.
   */
  private Object cacheKey() {
    return this.cacheKey;
  }

  void setCompleted(boolean completed) {
    this.completed = completed;
  }

  boolean isCompleted() {
    return completed;
  }

  /**
   * Realiza a valida��es do objeto que n�o dependem de colabora��o com objetos
   * que n�o fa�am parte deste.
   *
   * @throws com.comprashelp.util.exceptions.ValidationException
   */
  public void validate()
      throws ValidationException {
  }

  /**
   * M�todo de aux�lio para implementa��o do m�todo equals. Esse m�todo verifica
   * a igualdade entre dois objetos quaiquer utilizando a implementa��o original
   * do m�todo equals de "obj1". Antes de realizar a compara��o, esse m�todo
   * verifica se os atributos s�o nulos.
   *
   * @param obj1
   * @param obj2
   * @return True se forem iguais. False se forem diferentes ou se pelo menos um
   * deles for nulo.
   */
  protected boolean smartCompare(Object obj1, Object obj2) {
    return obj1 == obj2 || (obj1 != null && obj1.equals(obj2));
  }

  /**
   * Retorna um array com os valores que comp�em a chave do objeto. Todo tipo
   * deve sobrescrever esse m�todo, pois a implementa��o padr�o propaga a
   * exce��o {@link KeyOfNotSupportException}.
   *
   * @return A identifica��o do objeto.
   * @throws KeyOfNotSupportException Exce��o propagada pela implementa��o
   * padr�o, uma vez que esse tipo n�o possui identificador.
   */
  public Object[] keyOf()
      throws KeyOfNotSupportException {
    throw new KeyOfNotSupportException("KeyOf not support. You must implement keyOf in your class.");
  }

  /**
   * Torna a interface Cloenable acess�vel.
   *
   * @return
   * @throws java.lang.CloneNotSupportedException
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    try {
      ValueObject clone = (ValueObject) super.clone();
      return clone;
    } catch (CloneNotSupportedException ignore) {
      ignore.printStackTrace();
      return null;
    }
  }
}
