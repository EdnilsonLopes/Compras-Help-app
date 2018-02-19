package com.comprashelp.util.exceptions;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe que representa um erro de valida��o de um determinado objeto.
 */
public class ValidationError
  implements Cloneable, Serializable, Comparable<ValidationError> {

  /**
   * Apenas uma informa��o.
   */
  public static final int INFORMATION = 1;

  /**
   * Uma advert�ncia.
   */
  public static final int WARNING = 2;

  /**
   * Um erro que precisa ser remediado.
   */
  public static final int ERROR = 3;

  /**
   * Serial version <code>serialVersionUID</code>
   */
  @SuppressWarnings("compatibility:-4051008777514319844")
  private static final long serialVersionUID = -985260578979446692L;

  private String propertyName;
  private String message;
  private int severity;

  /**
   * A ordem dessa valida��o no conjunto.
   * Sempre um valor maior que zero, ou seja, a posi��o � �ndice 1.
   *
   * @since 03/08/2012
   */
  private int position;

  /**
   * Valores a serem substituidos na tradu��o do Bundles.
   */
  private String[] replaceValues;

  //----------------------------------------------------------------------------
  // Construtores
  //----------------------------------------------------------------------------

  /**
   * Construtor padr�o.
   */
  public ValidationError() {
  }

  /**
   * Construtor com mensagem de erro.
   * @param msg Mensagem de erro da valida��o.
   */
  public ValidationError(String msg) {
    setMessage(msg);
  }

  /**
   * Cria uma inst�ncia da classe com uma dada mensagem de erro e a sua posi��o.
   *
   * @param msg Mensagem de erro da valida��o.
   * @param position Posi��o da valida��o.
   */
  public ValidationError(String msg, int position) {
    setMessage(msg);
    setPosition(position);
  }

  /**
   * Construtor com mensagem de erro e valores a serem substitu�dos no Bundle.
   * @param msg Mensagem de erro da valida��o.
   * @param replaceValues Valores a serem substitu�dos no Bundle.
   */
  public ValidationError(String msg, String[] replaceValues) {
    setMessage(msg);
    setReplaceValues(replaceValues);
  }

  /**
   * Construtor com mensagem de erro, os valores a serem substitu�dos no Bundle e a posi��o.
   * @param msg Mensagem de erro da valida��o.
   * @param replaceValues Valores a serem substitu�dos no Bundle.
   * @param position Posi��o da valida��o.
   */
  public ValidationError(String msg, String[] replaceValues,
                         int position) {
    this(msg, replaceValues);
    this.setPosition(position);
  }

  /**
   * Construtor com mensagem de erro e propriedade associada.
   * @param msg Mensagem de erro da valida��o.
   * @param propName Nome da propriedade associada.
   */
  public ValidationError(String msg, String propName) {
    setMessage(msg);
    setPropertyName(propName);
  }

  /**
   * Retorna uma inst�ncia a partir de uma existente.
   * @param that Inst�ncia que deve ter o seu estado copiado.
   */
  public ValidationError(ValidationError that) {
    this(that.getMessage(), that.getReplaceValues());

    setPropertyName(that.getPropertyName());
    setSeverity(that.getSeverity());
    setPosition(that.getPosition());
  }

  //----------------------------------------------------------------------------
  // Getters e Setters
  //----------------------------------------------------------------------------

  /**
   * Nome da propriedade espec�fica de um objeto ao qual se relaciona o erro,
   * quando for o caso.
   *
   * @return nome da propriedade ou null quando o erro n�o estiver relacionado
   * a uma propriedade espec�fica.
   */
  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String newPropertyName) {
    propertyName = newPropertyName;
  }

  /**
   * Mensagem de erro.
   */
  public String getMessage() {
    return message;
  }

  public void setMessage(String newMessage) {
    message = newMessage;
  }

  /**
   * Severidade do erro.
   */
  public int getSeverity() {
    return severity;
  }

  public void setSeverity(int newSeverity) {
    severity = newSeverity;
  }

  public void setReplaceValues(String[] replaceValues) {
    this.replaceValues = replaceValues;
  }

  public String[] getReplaceValues() {
    return replaceValues;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  /**
   * Torna a interface {@link Cloneable} acess�vel.
   */
  @Override
  public ValidationError clone() {
    return new ValidationError(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(ValidationError anotherValidationError) {
    int thisVal = this.getPosition();
    int anotherVal = anotherValidationError.getPosition();
    return (thisVal < anotherVal? -1: (thisVal == anotherVal? 0: 1));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((message == null)? 0: message.hashCode());
    result =
      prime * result + ((propertyName == null)? 0: propertyName.hashCode());
    result = prime * result + Arrays.hashCode(replaceValues);
    result = prime * result + severity;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ValidationError other = (ValidationError) obj;
    if (message == null) {
      if (other.message != null)
        return false;
    }
    else if (!message.equals(other.message))
      return false;
    if (propertyName == null) {
      if (other.propertyName != null)
        return false;
    }
    else if (!propertyName.equals(other.propertyName))
      return false;
    if (!Arrays.equals(replaceValues, other.replaceValues))
      return false;
    if (severity != other.severity)
      return false;
    return true;
  }

}
