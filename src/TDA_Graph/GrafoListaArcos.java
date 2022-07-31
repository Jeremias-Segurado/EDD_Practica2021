package TDA_Graph;

import java.util.Iterator;

import Genericidad.Position;
import ImplementacionLista.ListaDoblementeEnlazada;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.PositionList;

public class GrafoListaArcos<V,E> implements Graph<V,E> {
	protected PositionList<Vertice<V>> vertices;
	protected PositionList<Arco<V,E>> arcos;
	
	@Override
	public Iterable<Vertex<V>> vertices() {
		Iterable<Vertex<V>> toReturn = new ListaDoblementeEnlazada<Vertex<V>>();
		for (Vertex<V> aux : vertices) {
			((PositionList<Vertex<V>>) toReturn).addLast(aux);
		}
		return  toReturn;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		Iterable<Edge<E>> toReturn = new ListaDoblementeEnlazada<Edge<E>>();
		for (Edge<E> aux : arcos) {
			((PositionList<Edge<E>>) toReturn).addLast(aux);
		}
		return  toReturn;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		checkVertex(v);		
		Iterable<Edge<E>> toReturn = new ListaDoblementeEnlazada<Edge<E>>();
		for (Edge<E> aux: arcos) {
			if (((Arco<E,V>)aux).getCola() == v || ((Arco<E,V>)aux).getPunta() == v) {
				((PositionList<Edge<E>>) toReturn).addLast(aux);
			}
		}		
		return toReturn;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {		
		checkEdge(e);
		checkVertex(v);
		Vertex<V> toReturn;
		if (((Arco<V,E>) e).getCola() == v)
			toReturn = ((Arco<V,E>) e).getPunta();
		else 
			if(((Arco<V,E>) e).getPunta() == v)
				toReturn = ((Arco<V,E>) e).getCola();
			else 
				toReturn = null;			
		return toReturn;
	}

	@Override
	public Vertex<V>[] endVertices(Edge<E> e) throws InvalidEdgeException{
		checkEdge(e);
		Vertex<V>[] toReturn = new Vertex[2];
		toReturn[0] = ((Arco<V,E>) e).getCola();
		toReturn[1] = ((Arco<V,E>) e).getPunta();
		return toReturn;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException{
		checkVertex(v); 
		checkVertex(w);
		boolean sonAdj = false;
		Iterator<Arco<V,E>> lista =  arcos.iterator();
		Arco<V,E> cursor = null;
		while (lista.hasNext() && !sonAdj) {
			cursor = lista.next();
			if (cursor.getCola() == v) 
				if (cursor.getPunta() == w)
					sonAdj = true; 
			else 
				if (cursor.getCola() == w) 
					if (cursor.getPunta() == v)
						sonAdj = true;						
		}
		return sonAdj;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException{
		Vertice<V> vertice = checkVertex(v);
		V toReturn = vertice.element();
		vertice.setRotulo(x); //Asume que 'x' no es una referencia nula.
		return toReturn;
	}

	
	public E replace(Edge<E> e, E x) throws InvalidEdgeException{
		Arco<V,E> arco = checkEdge(e);
		E toReturn = arco.element();
		arco.setRotulo(x); //Asume que 'x' no es una referencia nula.
		return toReturn;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertex<V> toReturn = new Vertice(x);
		vertices.addLast((Vertice<V>)toReturn);
		try {
			((Vertice<V>) toReturn).setPosicionEnLista(vertices.last());
		} catch (EmptyListException e) { toReturn = null; }					
		return toReturn;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x) throws InvalidVertexException{
		Vertice<V> vertice1  = checkVertex(v);
		Vertice<V> vertice2 = checkVertex(w);
		Arco<V,E> toReturn = new Arco(x, vertice1, vertice2, null);
		arcos.addLast(toReturn);
		try {
			toReturn.setPosEnLista(arcos.last());
		} catch (EmptyListException e) { toReturn = null; }					
		return toReturn;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException{
		Vertice<V> vertice = checkVertex(v);
		V toReturn = vertice.element();
		boolean sinEliminar = true;
		//Como el vertice guarda la pos en la lista no se necesita recorrer el iterador de posiciones de la lista vertices.
		Iterator<Vertice<V>> lista_vertices = vertices.iterator();
		Vertice<V> cursor_vertices = null;		
		try {
			while (lista_vertices.hasNext() && sinEliminar) {
				cursor_vertices = lista_vertices.next();
				if (cursor_vertices == v) {					
					vertices.remove(vertice.getPosicionEnLista());
					sinEliminar = false;
				}				
			}
			if (!sinEliminar) {		
				//se debe recorrer toda la lista porque puede haber varios arcos con el vertice.
				for(Arco<V,E> cursor: arcos) {
					if (cursor.getCola() == v) {
						arcos.remove(cursor.getPosEnLista());
					}else {
						if (cursor.getPunta() == v) {
							arcos.remove(cursor.getPosEnLista());
						}
					}
				}
			}else { //Si no fue allado el vertice en la lista del grafo.
				toReturn = null;//Podria lanzar alguna excepcion.
			}
		}catch(InvalidPositionException e) {/*No deberia ocurrir nunca*/}
		return toReturn;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException{
		Arco<V,E> to_remove = checkEdge(e);
		E toReturn = to_remove.element();
		Iterator<Arco<V,E>> lista_arcos = arcos.iterator();
		Arco<V,E> cursor = null;
		boolean sinEliminar = true;
		try {
			while(lista_arcos.hasNext() && sinEliminar) {
				cursor = lista_arcos.next();
				if (cursor == e) {
					arcos.remove(to_remove.getPosEnLista());
					sinEliminar = false;
				}
			}
			if (sinEliminar) //Si no se encontro el arco
				toReturn = null;
		}catch(InvalidPositionException exc) { /*No deberia ocurrir.*/ }
		return toReturn;
	}
	/**
	 * Toma un vertex generico y prueba si es un vertive valido
	 * para un grafo no dirigido de lista de arcos.
	 * @param v El vertice e ser testeado.
	 * @return El vertice correspondiente, NULL si no cumple.
	 */
	private Vertice<V> checkVertex(Vertex<V> v) throws InvalidVertexException{
		Vertice<V> toReturn = null;		
		try {
			if (v != null) {
				toReturn = (Vertice<V>) v;
				if (toReturn.element() == null) throw new InvalidVertexException("Vertice sin rotulo.");					
				else
					if (toReturn.getPosicionEnLista() == null) throw new InvalidVertexException("Vertice o contiene su pos en lista.");						
			}else 
				throw new InvalidVertexException("Vertice nulo.");
			
		}catch(ClassCastException e) { throw new InvalidVertexException("El vertice no es el utilizado por este grafo."); }
		return toReturn;
	}
	/**
	 * Toma un Edge generico y prueba si es un Arco valido
	 * para un grafo no dirigido de lista de arcos.
	 * @param v El Arco e ser testeado.
	 * @return El Arco correspondiente, NULL si no cumple.
	 */
	private Arco<V,E> checkEdge(Edge<E> e){
		Arco<V,E> toReturn = null;
		try {
			if (e != null) {
				toReturn = (Arco<V,E>) e;
				if (toReturn.element() == null)
					toReturn = null;
				else
					if (toReturn.getPosEnLista() == null)
						toReturn = null;
					else 
						if (toReturn.getCola() == null)
							toReturn = null;
						else 
							if (toReturn.getPunta() == null)
								toReturn = null;
			}
		}catch(ClassCastException exc) { toReturn = null; }
		return toReturn;
	}
}
