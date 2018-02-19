package com.comprashelp.viewcontroller.form;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Classe responsável pela criação de um Formulário. Obs.: Todas os Formulários
 * devem ser filhos desta classe, ou seja devem extende-la
 *
 * @author Ednilson Brito Lopes
 * @param <T>
 */
public class BeanForm<T> extends VerticalLayout {

  private static final long serialVersionUID = 1L;

  /**
   * Objeto a ser editado ou visualizado no formulário.
   */
  T object;

  /**
   * Objeto responsável por atualizar de forma automática as propriedades do
   * objeto alterado. Obs.: Tentando Fazer implementação altomática.
   */
  private Binder<T> binder;

  /**
   * Se {@code true} o objeto do formulário poderá apenas ser visualizado.
   */
  private boolean readOnly;

  private Class<T> type;

  /**
   * Construtor.
   *
   * @param type tipo do objeto que será editado.
   */
  public BeanForm(Class<T> type) {
    super();
    this.type = type;
    binder = new Binder<T>(type);
  }

  /**
   * Define o objeto que será alterado ou apresentado.
   *
   * @param object objeto a ser alterado ou apresentado.
   * @param readOnly define se o objeto será disponibilizado apenas para
   * leitura.
   */
  public void setObject(T object, boolean readOnly) {
    this.object = object;
    bind(object, false);
    setReadOnly(readOnly);
  }

  /**
   * Realiza o bind do objeto informado com campos referenciados por
   * propriedades do formulário cujos nomes coincidam com os nomes das
   * propriedades do objeto.
   *
   * @param object objeto com o qual será feito o bind.
   * @param clearPrevious refaz os binding previamente criados. É
   * responsabilidade de quem evocou este método refazer todos os bindings
   * previamente criados.
   *
   * @see Binder#bindInstanceFields(Object)
   */
  protected void bind(T object, boolean clearPrevious) {
    if (binder != null && clearPrevious) {
      binder.removeBean();
      binder = new Binder<T>(type);
    }
    try {
      binder.bindInstanceFields(this);
    } catch (IllegalStateException e) {
      // Se não há campos para o bind, sem problemas.
    }
    binder.setBean(object);
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    recursiveReadOnly(this, readOnly);
  }

  @Override
  public boolean isReadOnly() {
    return readOnly;
  }

  /**
   * Caminha recursivamente pelos componentes filhos do componente informando,
   * definindo sua propriedade readOnly.
   *
   * @param component componente cujos filhos serão percorridos.
   * @param readOnly valor a ser atribuído à propriedade readOnly.
   */
  private void recursiveReadOnly(Component component, boolean readOnly) {
    Iterator<Component> it = null;
    if (component instanceof AbstractComponentContainer) {
      it = ((AbstractComponentContainer) component).iterator();
    } else if (component instanceof AbstractSingleComponentContainer) {
      Component c = ((AbstractSingleComponentContainer) component)
          .getContent();
      setReadOnly(c, readOnly);
      recursiveReadOnly(c, readOnly);
    }
    if (it != null) {
      while (it.hasNext()) {
        Component c = it.next();
        setReadOnly(c, readOnly);
        recursiveReadOnly(c, readOnly);
      }
    }
  }

  /**
   * Caso o componente informado possua um campo com "readOnly", atualiza-o com
   * o valor informado.
   *
   * @param c component a ser atualizado.
   * @param readOnly valor que será assumido pela propriedade.
   */
  private void setReadOnly(Component c, boolean readOnly) {
    Class<?> current = c.getClass();
    while (current != null) {
      try {
        Method f = c.getClass().getMethod("setReadOnly", boolean.class);
        f.setAccessible(true);
        f.invoke(c, readOnly);
        break;
      } catch (NoSuchMethodException | SecurityException
          | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        // Nada a fazer se a classe não possui o método, busca a próxima
        // na hierarquia
      }
      current = current.getSuperclass();
    }
  }

  /**
   * @return {@link #object}.
   */
  public T getObject() {
    return object;
  }

  /**
   * Cria um {@link TextField} que permite definir valores nulos
   * programaticamente.
   *
   * @param caption título do campo.
   * @return o campo retornado.
   */
  protected TextField createTextField(String caption) {
    /**
     * Altera a classe permitindo valores nulos.
     */
    return new TextField(caption) {
      private static final long serialVersionUID = 1L;

      @Override
      public void setValue(String value) {
        setValue(value, false);
      }

    };
  }

  /**
   * Cria um campo para edição de E-mail.
   *
   * @param caption título do campo.
   * @param propertyName nome da propriedade do bean com o qual será realizado o
   * bind deste campo.
   * @return o campo criado.
   */
  protected TextField createEmailField(String caption,
      String propertyName) {
    TextField field = createTextField(caption);
    binder.forField(field)
        .withValidator(new EmailValidator("Endereço de e-mail inválido."))
        .bind(propertyName);
    return field;
  }
}
