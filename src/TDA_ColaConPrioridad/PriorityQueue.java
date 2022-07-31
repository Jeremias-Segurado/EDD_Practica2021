package TDA_ColaConPrioridad;

import Genericidad.*;

public interface PriorityQueue<K,V> {
	/** Retorna el n�mero de �tems en la cola con prioridad. */
	public int size();
	/** Retorna si la cola con prioridad est� vac�a. */
	public boolean isEmpty();
	/** Retorna pero no elimina una entrada con minima prioridad. */
	public Entry<K,V> min() throws EmptyPriorityQueueException;
	/**Inserta un par clave-valor y retorna la entrada creada.*/
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException;
	/** Remueve y retorna una entrada con minima prioridad. */
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException;
}
