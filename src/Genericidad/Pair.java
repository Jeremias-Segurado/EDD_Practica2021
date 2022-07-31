package Genericidad;

/**
 * Clase Pair. Representa un par de valores (k,v)
 * @author Comisión 6: LOPEZ, SANDIUMENGE, SEGURADO NEGRIN.
 * @param <K> Tipo de dato de la clave
 * @param <V> Tipo de dato del valor
 */

public class Pair<K, V> {
	protected K key;
	protected V value;
	
	/**
	 * Contructor 1, utilizado en caso de querer asignar valores al crear la clase
	 * @param k Valor a asignar a la clave
	 * @param v Valor a asignar al valor
	 */
	public Pair(K k, V v) {
		key = k;
		value = v;
	}
	
	/**
	 * Constructor 2, utilizado en caso de querer crear la clase sin asignarle valores
	 */
	public Pair() {
		this(null, null);
	}
			
	//COMANDOS
	/**
	 * Asigna una nueva clave al par
	 * @param k Nueva clave a asignar al par
	 */
	public void setKey(K k) {
		key = k;
	}
	
	/**
	 * Asigna un nuevo valor al par
	 * @param v Nuevo valor a asignar al par
	 */
	public void setValue(V v) {
		value = v;
	}

	// CONSULTAS
	/**
	 * Retorna la clave del par
	 * @return La clave del par
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * Retorna el valor del par
	 * @return El valor del par
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Retora una cadena con la clave y el valor del Pair.
	 * @return cadena de caracteres.
	 */
	public String toString() {
		return "( " + key + " , " + value + " )";
	}
}
