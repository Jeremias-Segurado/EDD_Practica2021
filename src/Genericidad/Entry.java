package Genericidad;

public interface Entry<K, V> {
	/**
	 * Obtiene la clave de la entrada.
	 * @return Clave asociada al valor.
	 */
	public K getKey();
	/**
	 * Obtiene el valor de la entrada.
	 * @return Valor asociado a la clave.
	 */ 
	public V getValue();
}
