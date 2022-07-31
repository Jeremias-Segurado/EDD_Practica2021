package TDAPila;

public interface Stack<E> {
	/**
	 * Inserta el elemento E en el tope de la pila.
	 * @param item Elemento a insertar.
	 */
	public void push(E item);
	/**
	 * Consulta si la pila tiene elmentos..
	 * @return Retorna True si la pila no contiene elementos, False en caso contrario.
	 */
	public boolean isEmpty();
	/**
	 *  Remueve el elemento que se encuentra en el tope de la pila.
	 * @return Elemento removido de la pila.
	 * @trows EmptyStackException si la pila esta vacia.
	 */
	public E pop() throws EmptyStackException;
	/**
	 * Consulta la cantidad de elementos de la pila.
	 * @return Cantidad de elementos.
	 */
	public int size();
	/**
	 * Consulta el elemento en el tope de la pila.
	 * @return Elemento en el tope de la pila.
	 * @throws EmptyStackException si la pila easta vacia.
	 */
	public E top() throws EmptyStackException;
}
