package TDAMap;

import Genericidad.Entry;
import ImplementacionLista.*;

public class MapeoHashCerrado<K, V> implements Map<K, V> {

	private Entry<K, V>[] bucket;
	private final Entry<K, V> disponible = new Entrada<K,V>(null, null);
	private final float factor_carga = 0.5f;
	private int tamaño;
	
	public MapeoHashCerrado(){
		bucket = new Entry[13];
		tamaño = 0;
	}
	@Override
	public int size() {		
		return tamaño;
	}

	@Override
	public boolean isEmpty() {		
		return tamaño==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if (key == null) throw new InvalidKeyException("Mapeo::get(): ERROR clave incalida.");
		int index_aux = hash(key);
		V value_resul =null;
		boolean encontrado = false;
		while (bucket[index_aux]!=null && !encontrado)
		{
			if (bucket[index_aux] != disponible)
				if (bucket[index_aux].getKey().equals(key))				
					value_resul = bucket[index_aux].getValue();				
			index_aux++;
			if (index_aux == bucket.length)			
				index_aux = 0;			
			if (index_aux == hash(key))			
				encontrado = true;			
		}
		return value_resul;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if (key == null) throw new InvalidKeyException("Mapeo::put(): ERROR clave invalida.");
		//Redimensiono al principio porque redimensionar() cambia como se maneja el hash() y por lo tento
		//no podria recorrer el arreglo manteniendo guardado los index de manera efectiva.		
		if (tamaño/(float) bucket.length >= factor_carga)
			redimensionar();
		boolean encontrado = false;
		boolean aInsertar = false;
		int index_aux = hash(key);
		int index_disp = -1;
		V valor_resul = null;
		while(bucket[index_aux]!=null && !encontrado && !aInsertar)
		{
			if (bucket[index_aux]==disponible)
			{
				index_disp = index_aux; //Guardo laprimera posicion disponible por si necesito insertar nueva entrada
			} else
				if (bucket[index_aux].getKey().equals(key))
				{
					encontrado = true;
					valor_resul = bucket[index_aux].getValue();					
					((Entrada) bucket[index_aux]).setValue(value);
				}
			index_aux++;
			if (index_aux == bucket.length)
				index_aux = 0;
			if (index_aux==hash(key))
				aInsertar = true; //seria el "equivalente" al break(); en esta funcion.
		}
		if(!encontrado)
		{			
			if (index_disp!=-1) 
			{
				bucket[index_disp] = new Entrada<K, V>(key, value);
			}else 
				{
					bucket[index_aux] = new Entrada<K, V>(key, value);
				}	
			tamaño++;
		}
		return valor_resul;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		if (key == null) throw new InvalidKeyException("Mapeo::remove: ERROR calve invalida.");
		V valor_resul = null;
		boolean encontre = false;
		int index_aux = hash(key);
		while (bucket[index_aux]!=null && !encontre)
		{
			if(bucket[index_aux].getKey().equals(key))
			{
				encontre = true;
				valor_resul = bucket[index_aux].getValue();
				bucket[index_aux] = disponible;
				tamaño--;
			}
			index_aux++;
			if (index_aux==bucket.length)
				index_aux = 0;
			if (index_aux==hash(key))
				encontre = true; //suplanta funcion del Break();
		}
		return valor_resul;
	}

	@Override
	public Iterable<K> keys() {
		ListaDoblementeEnlazada<K> lista_resul = new ListaDoblementeEnlazada<K>();
		int tamaño_aux = tamaño;
		int index_aux = 0;
		while (tamaño_aux>0)
		{
			if (bucket[index_aux] != null && bucket[index_aux]!=disponible)
			{
				lista_resul.addLast(bucket[index_aux].getKey());
				tamaño_aux--;
			}
			index_aux++;
		}
		return lista_resul;
	}

	@Override
	public Iterable<V> values() {
		ListaDoblementeEnlazada<V> lista_resul = new ListaDoblementeEnlazada<V>();
		int tamaño_aux = tamaño;
		int index_aux = 0;
		while (tamaño_aux>0)
		{
			if (bucket[index_aux] != null && bucket[index_aux]!=disponible)
			{
				lista_resul.addLast(bucket[index_aux].getValue());
				tamaño_aux--;
			}
			index_aux++;
		}
		return lista_resul;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		ListaDoblementeEnlazada<Entry<K, V>> lista_resul = new ListaDoblementeEnlazada<Entry<K, V>>();
		int tamaño_aux = tamaño;
		int index_aux = 0;
		while (tamaño_aux>0)
		{
			if (bucket[index_aux] != null && bucket[index_aux]!=disponible)
			{
				lista_resul.addLast(bucket[index_aux]);
				tamaño_aux--;
			}
			index_aux++;
		}
		return lista_resul;
	}
	
	private int hash(K clave) {
		return Math.abs(clave.hashCode() % bucket.length);
	}
	
	private void redimensionar() {
		Entry<K, V>[] bucket_old = bucket;
		bucket = new Entry[proximoPrimo(tamaño*2)];		
		int tamaño_aux = tamaño;
		int index_aux = 0;
		int ind;
		int new_hash;
		while (tamaño_aux>0)
		{
			if (bucket_old[index_aux] != null && bucket_old[index_aux]!=disponible) 
			{
				new_hash = hash(bucket_old[index_aux].getKey());
				if (bucket[new_hash]!=null)
				{
					for (ind = index_aux; bucket[ind] != null; ind++)
					{
						if (ind == bucket.length)
							ind = 0;
					}
					new_hash = ind;
				}				
				bucket[new_hash] = bucket_old[index_aux];							
				tamaño_aux--;
			}
			index_aux++;
		}
	}
	
	/**
	 * Encuentra el siguiente numero primo al ingresado por parametro.
	 * @param n numero primo.
	 * @return numero primo siguiente a n.
	 */
	public int proximoPrimo(int n) {
		int ProximoP=n+1;
		while (esPrimo(ProximoP)==false) 
			ProximoP++;
		return ProximoP;	
	}
	
	/**
	 * Comprueba si el numero pasado por parametro es o no un numero primo.
	 * @param n numero entero.
	 * @return true si el numero es primo, false en caso contrario.
	 */
	protected boolean esPrimo(int n) {
		double techo = Math.ceil(Math.sqrt(n)); //techo de la raÃ³z cuadrada de n
		boolean es_primo = false;
		int i = 2;
		
		while(i<=techo && !es_primo) {
			es_primo = (n % i) == 0;
			i--;
		}		
		return es_primo;
	}		
}
