package TDAPila;

/**
 * Clase Excepcion que utiliza las clases que implementen la interfaz Stack.
 * @author Jeremias
 *
 */
public class EmptyStackException extends Exception {
	/**
	 * Constructor de la excepcion.
	 * @param msg mensaje de error.
	 */
	public EmptyStackException(String msg) {
		super(msg);
	}
}
