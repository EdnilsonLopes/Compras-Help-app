package com.comprashelp.util.exceptions;

import com.comprashelp.util.model.Translator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Exceção utilizada para empacotar um conjunto de erros que invalidam um
 * determinado objeto.
 *
 * @author Walace Zloccowick Maia
 */
public class ValidationException
  extends TranslatableException
  implements Cloneable {

  @SuppressWarnings("compatibility:-4509404650456808128")
  private static final long serialVersionUID = 3208449254778602003L;

  private List<ValidationError> errors = new ArrayList<>();
  private String baseName = null;
  private boolean flgImpedir = true;

  public ValidationException() {
  }

  public ValidationException(Throwable t) {
    super(t);
  }

  public ValidationException(String msg, Throwable t) {
    super(msg, t);
    addError(new ValidationError(msg));
  }

  /**
   * @param msg mensagem de erro
   */
  public ValidationException(String msg) {
    super(msg);
    addError(new ValidationError(msg));
  }

  /**
   * @param msg mensagem de erro
   * @param replaceValues valores que ser�o substitu�dos na mensagem de erro.
   */
  public ValidationException(String msg, String[] replaceValues) {
    super(msg);
    addError(new ValidationError(msg, replaceValues));
  }

  /**
   * @param msg mensagem de erro
   * @param replaceValues valores que ser�o substitu�dos na mensagem de erro.
   */
  public ValidationException(String msg, String[] replaceValues,
                             Throwable cause) {
    super(msg, cause);
    addError(new ValidationError(msg, replaceValues));
  }


  /**
   * @param msg mensagem de erro
   * @param baseName o nome base do resource bundle que ser� usado para a
   * tradu��o da exception, um nome de classe completamentequalificado.
   */
  public ValidationException(String msg, String baseName) {
    super(msg);
    addError(new ValidationError(msg));
    this.baseName = baseName;
  }


  /**
   * Acrescenta os erros de uma outra exce��o.
   * @param ex
   */
  public void appendException(ValidationException ex) {
    if(ex.getErrors() != null) {
      for (ValidationError validationError : ex.getErrors()) {
        addError(validationError);
      }
    }
  }

  /**
   * Conjunto de erros que respons�veis pela gera��o da exce��o.
   * @return 
   */
  public List<ValidationError> getErrors() {
    return errors;
  }

  /**
   * Adiciona um novo erro � exce��o.
   * @param error erro a ser adicionado.
   */
  public void addError(ValidationError error) {

    if (error.getPosition() == 0)
      error.setPosition(this.errors.isEmpty()? 1: this.errors.size());

    if(!errors.contains(error)) {
      errors.add(error);
    }
    Collections.sort(errors);
  }

  /**
   * Retorna a maior severidade dentre todos os erros de uma exce��o.
   */
  public int getSeverity() {
    int severity = 0;
    Iterator it = errors.iterator();
    while (it.hasNext()) {
      ValidationError error = (ValidationError) it.next();
      if (severity < error.getSeverity())
        severity = error.getSeverity();
    }
    return severity;
  }

  /**
   * Retorna um erro dada a propriedade associada.
   * @param propertyName
   * @return 
   */
  public ValidationError getErrorByProperty(String propertyName) {
    Iterator it = errors.iterator();
    while (it.hasNext()) {
      ValidationError error = (ValidationError) it.next();
      if (error.getPropertyName().equals(propertyName))
        return error;
    }
    return null;
  }

  /**
   * @return a mensagem de todos os erros da exce��o sperados pelo caracter
   * de fim de linha.
   */
  @Override
  public String getMessage() {
    StringBuilder message = new StringBuilder();
    Iterator<ValidationError> it = errors.iterator();
    while (it.hasNext()) {
      ValidationError error = it.next();
      message.append(error.getMessage());
      if (it.hasNext()) {
        //Quebra a linha somente se ainda houver outra mensagem de erro.
        message.append("\n");
      }
    }
    return message.toString();
  }

  /**
   * Traduz a exce��o.
   * @param translator tradutor que far� a tradu��o.
   * @return objeto representando o resultado da tradu��o.
   */
  @Override
  public Object translate(Translator translator) {
    if (getBaseName() == null)
      return this;

    ValidationException result = this.clone();

    if (getBaseName() != null) {
      for (ValidationError error: result.getErrors()) {
        String msg =
          translator.translate(error.getMessage(), error.getReplaceValues());
        if (msg != null)
          error.setMessage(msg);
      }
    }
    return result;
  }

  /**
   * @return o nome base do resource bundle, um nome de classe completamente
   * qualificado. Se retornar null, nenhuma tradu��o � realizada.
   */
  @Override
  public String getBaseName() {
    return baseName;
  }

  /**
   * A clonagem da exce��o � necess�ria para tradu��es.
   * @return um clone da exce��o.
   */
  @Override
  public ValidationException clone() {
    ValidationException clone = null;

    try {

      clone = (ValidationException) super.clone();
      clone.errors = new ArrayList<>();

      for (ValidationError error: getErrors()) {
        clone.addError(error.clone());
      }
    }
    catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return clone;
  }

  /**
   * Retorna <code>true</code>
   * @return
   */
  public boolean isEmpty() {
    return this.errors.isEmpty();
  }

  public void setFlgImpedir(boolean flgImpedir) {
    this.flgImpedir = flgImpedir;
  }

  public boolean isFlgImpedir() {
    return flgImpedir;
  }
}
