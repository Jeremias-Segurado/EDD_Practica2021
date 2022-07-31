package TDALista;
import java.util.Iterator;
import Genericidad.Position;


public interface PositionList<E> extends Iterable<E> {
	
	//-----------METODOS DE ACTUALIZACION---------------
	
	/**
	 * Inserta un nuevo elemento al principio de la lista.
	 * @param e Elemento a insertar.
	 */
	public void addFirst(E e);
	/**
	 * Inserta un nuevo elemento al final de la lista.
	 * @param e Elemento a insertar.
	 */
	public void addLast(E e);
	/**
	 * Inserta un nuevo elemento luego de la posicion pasada por parametro.
	 * @param p Posicion anterior al nuevo elemento a insertar.
	 * @param e Elemento a insertar.
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 */
	public void addAfter(Position<E> p, E e) throws InvalidPositionException;
	/**
	 * Inserta un nuevo elemento antes de la posicion pasada por parametro.
	 * @param p Posicion siguiente al nuevo elemento insertado.
	 * @param e Elemento a insertar.
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 */
	public void addBefore(Position<E> p, E e) throws InvalidPositionException;
	/**
	 * Elimina y retorna el elemento en la posicion pasada por parametro.
	 * @param p Posicion del elemento a eliminar.
	 * @return Elemento eliminado. 
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 */
	public E remove(Position<E> p) throws InvalidPositionException;
	/**
	 * Reemplaza al elemento de la posicion pasada por parametro con el elemento pasado por parametro.
	 * @param p Posicion del elemento a reemplazar.
	 * @param e Elemento que sustituye al anterior.
	 * @return Elemento reemplazado.
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 */
	public E set(Position<E> p, E e) throws InvalidPositionException;
	
	//----------OPERACIONES DE ITERACION/RECORRIDO-------------
	
	/**
	 * Retorna la posicion del primer elemento de la lista.
	 * @return La posicion del elemento.
	 * @throws EmptyListException si la lista que recibe el mensaje esta vacia.
	 */
	public Position<E> first() throws EmptyListException;
	/**
	 * Retorna la posicion del ultimo elemento en la lista.
	 * @return La posicion del elemento.
	 * @throws EmptyListException si la lista que recibe el mensaje esta vacia.
	 */
	public Position<E> last() throws EmptyListException;
	/**
	 * Retorna la posicion del elemento que sigue al elmento de la posicion pasada por parametro,
	 * @param p Posicion del elemento anterior al buscado.
	 * @return La posicion del lemento buscado.
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 * @throws BoundaryViolationException si la posicion pasada por parametro es la ultima en la lista. 
	 */
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	/**
	 * Retorna la posicion del elemento que procede al elemento de la posicion pasada por parametro.
	 * @param p Posicion del elemento que esta delante del buscado.
	 * @return Posicion del elemento buscado.
	 * @throws InvalidPositionException si la posicion pasada por parametro no pertenece a la lista.
	 * @throws BoundaryViolationException si la posicion pasada por parametro es la primera en la lista.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	/**
	 * Chequea la cantidad de elementos de la lista.
	 * @return Cantidad de elementos.
	 */
	
	//------------METODOS DE CONSULTA----------------
	
	public int size();
	/**
	 * Chequea si la lista contiene elementos.
	 * @return TRUE si la lista esta vacia, FALSE en caso contrario.
	 */
	public boolean isEmpty();
	
	//----------ITERADORES-------------
	
	/**
	 * Devuelve un un iterador de todos los elementos de la lista.
	 * @return Un iterador de todos los elementos de la lista.
	 */
	public Iterator<E> iterator();
	
	/**
	 * Devuelve una colección iterable de posiciones.
	 * @return Una colección iterable de posiciones.
	 */
	public Iterable<Position<E>> positions();
}
