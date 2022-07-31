package TDA_ColaConPrioridad;
import java.util.Comparator;


import Genericidad.Entry;
import TDA_Diccitionary_Martin.DefaultComparator;

/**
 * Arbol Binario que almacena una coleccion de entradas (clave, valor)
 * en el cual cada nodo N, su clave es mayor o igual a la clave del padre de N.
 *  Tambien el arbol es un arbol completo (Un arbol de altura H tiene sus nveles 
 *  H-1 completos y el ultimo estan incertados de izquierda a derecha)
 * @author Jeremias
 *
 * @param <K> clave a comparar de las entradas.
 * @param <V> valor que guardan las entradas.
 */
public class Heap<K,V> implements PriorityQueue<K,V> {

	protected Entrada<K,V>[] elems;
	protected Comparator<K> comp;
	protected int size;
	
	//Clase Anidada
	private class Entrada<K,V> implements Entry<K,V>{
		private K clave;
		private V valor;
		
		public Entrada(K clave, V valor){
			this.clave = clave;
			this.valor = valor;
		}
		@Override
		public K getKey() {			
			return clave;
		}

		@Override
		public V getValue() {			
			return valor;
		}
		public String toString() { return "(" + clave + ", " + valor + ")"; }			
	}
	
	public Heap(int maxElems, Comparator<K> comp) {
		elems = new Entrada[maxElems];
		this.comp = comp;
		size = 0;
	}
	
	@Override
	public int size() {		
		return size;
	}

	@Override
	public boolean isEmpty() {		
		return size == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		return elems[1];
	}

	/*
	 * INSERTAR(clave, valor):
	 * 	-primero se inserta el nodo
	 */
	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null) throw new InvalidKeyException("Heap::insert: ERROR clave nula.");
		int index = ++size;
		boolean seguir = true;
		Entry<K,V> toReturn = new Entrada(key, value);
		Entrada<K,V> hijo;
		Entrada<K,V> padre;
		Entrada<K,V> aux;
		elems[index] = (Entrada<K, V>) toReturn;		
		if (elems.length == size) resize();		
		while (index>1 && seguir) {
			hijo = elems[index];
			padre = elems[index / 2];
			if (comp.compare(hijo.getKey(), padre.getKey())<0) {
				aux = elems[index/2];
				elems[index/2] = hijo;
				elems[index] = aux;
				index /= 2;
			}else 
				seguir = false;						
		}
		return toReturn;
	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if (size==0) throw new EmptyPriorityQueueException("Heap::removeMin: ERROR, arbol vacio.");
		Entry<K,V> toReturn = elems[1];
		Entrada<K,V> hijo_izq, hijo_der;
		Entrada<K,V> padre, aux;
		int index = 1;
		int ind_der, ind_izq, ind_hijo;
		boolean seguir = true;
		
		elems[1] = elems[size]; 
		elems[size] = null;
		size--;
		while (seguir) {			
			ind_izq = index*2;
			ind_der = index*2+1;
			if (ind_izq <= size) {		
				if (ind_der <= size)
				{					
					if (comp.compare(elems[ind_izq].getKey(), elems[ind_der].getKey())<0)
						ind_hijo = ind_izq;
					else 
						ind_hijo = ind_der;
					
				}else 				
					ind_hijo = ind_izq;				
				if(comp.compare(elems[index].getKey(), elems[ind_hijo].getKey())>0) 
				{
					aux = elems[index];
					elems[index] = elems[ind_hijo];
					elems[ind_hijo] = aux;
					index = ind_hijo;
				}else 
					seguir = false;				
			}else 
				seguir = false;		
		}
		return toReturn;
	}

	private void resize() {
		
	}
	
	public void HeapSort(int[] arreglo, int cant) {
		
		Heap<Integer, Integer> aux = new Heap<Integer, Integer>(cant, new DefaultComparator<Integer>() );
		try 
		{
			for (int index = 0; index<cant; index++) {
				aux.insert(arreglo[index], arreglo[index]);
			}
			for (int index = 0; index<cant; index++) {
				arreglo[index] = aux.removeMin().getKey();
			}
		}catch(InvalidKeyException | EmptyPriorityQueueException e) { }
	}
	/*
	 * AYUDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	 * 
	 */
	public void HeadSortInPlace(int[] arreglo, int cant) {
		if (arreglo.length >= cant*2) 
		{
			//COMO CARAJO SE USA LA MISMA ESTRUCTURA DEL ARREGLO?!!!
		}
	}
}
