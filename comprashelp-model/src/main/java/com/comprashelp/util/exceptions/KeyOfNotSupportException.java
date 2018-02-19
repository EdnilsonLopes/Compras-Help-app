package com.comprashelp.util.exceptions;

/**
 * Exce��o utilizada para informar que um tipo n�o implementou o m�todo que o
 * identifica (keyOf) adequadamente.
 *
 * @version 1.0, 18/05/2011
 * @since 18/05/2011
 */
public class KeyOfNotSupportException extends UnsupportedOperationException {

  @SuppressWarnings("compatibility:7905653147842541083")
  private static final long serialVersionUID = -887823032341752318L;

  public KeyOfNotSupportException(Throwable throwable) {
    super(throwable);
  }

  public KeyOfNotSupportException(String string, Throwable throwable) {
    super(string, throwable);
  }

  public KeyOfNotSupportException(String string) {
    super(string);
  }

  public KeyOfNotSupportException() {
    super();
  }
}
