/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.util.exceptions;

import com.comprashelp.util.model.Bundles;
import com.comprashelp.util.model.Translatable;
import com.comprashelp.util.model.Translator;

/**
 * Exceções que possam ser traduzidas
 *
 * @author Walace Zloccowick Maia
 */
public abstract class TranslatableException
  extends Exception
  implements Translatable {

  @SuppressWarnings("compatibility:7540022736605801444")
  private static final long serialVersionUID = -6512617682439897365L;
  
  private String[] replaceValues;

  /**
   * Constructs a new exception with <code>null</code> as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  public TranslatableException() {
    super();
  }


  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public TranslatableException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   *
   * @param   replaceValues valores que ser�o substu�dos por ocasi�o da tradu��o.
   */
  public TranslatableException(String message, String[] replaceValues) {
    super(message);
    this.replaceValues = replaceValues;
  }


  /**
   * Constructs a new exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * <code>cause</code> is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param  message the detail message (which is saved for later retrieval
   *         by the {@link #getMessage()} method).
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   */
  public TranslatableException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * <code>cause</code> is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param  message the detail message (which is saved for later retrieval
   *         by the {@link #getMessage()} method).
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   * @param   replaceValues valores que ser�o substu�dos por ocasi�o da tradu��o.
   */
  public TranslatableException(String message, String[] replaceValues,
                               Throwable cause) {
    super(message, cause);
    this.replaceValues = replaceValues;
  }


  /**
   * Constructs a new exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>).
   * This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link
   * java.security.PrivilegedActionException}).
   *
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   * @since  1.4
   */
  public TranslatableException(Throwable cause) {
    super(cause);
  }


  public void setReplaceValues(String[] replaceValues) {
    this.replaceValues = replaceValues;
  }

  public String[] getReplaceValues() {
    return replaceValues;
  }
  
  @Override
  public String getLocalizedMessage() {
    TranslatableException translatable = (TranslatableException) this.translate(new Translator() {
        public String translate(String str, String[] replaceValues) {
          return Bundles.getValue(null, getBaseName(), str, replaceValues);
        }
      });
    return translatable.getMessage();
  }
  
  
}
