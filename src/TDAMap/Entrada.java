package TDAMap;

import Genericidad.*;
/**
 * Clase Entrada
 * @author Comisión 6: LOPEZ, SANDIUMENGE, SEGURADO NEGRIN.
 * @param <K> Tipo de la calve.
 * @param <V> Tipo del valor.
 */

public class Entrada<K, V> implements Entry<K,V> {
	protected K clave;
	protected V valor;
	
	// CONSTRUCTOR
	/**
	 * Constructor
	 * @param k Clave de la entrada
	 * @param v Valor de la entrada
	 */
	public Entrada(K k, V v) {
		clave = k;
		valor = v;
	}
	
	@Override
	public V getValue() {
		return this.valor;
	}

	@Override
	public K getKey() {
		return this.clave;
	}

	/**
	 * Asigna un valor a la entrada
	 * @param val El valor a asignar
	 */
	public void setValue(V val) {
		valor = val;
	}
	
	/**
	 * Asigna una clave a la entrada
	 * @param key La clave a asignar
	 */
	public void setKey(K key) {
		clave = key;
	}
	
	@Override
	public String toString() {
		return "(" + this.getKey() + "," + this.getValue() + ")";
	}
}
