package TDA_Graph;

import Genericidad.Position;
import ImplementacionLista.ListaDoblementeEnlazada;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.PositionList;
/**
 * Grafo dirigido(Digrafo) que utiliza una matriz de abyacencias y una lista de vertices y otra de arcos.
 * Las clases 'Vertice' y 'Arco' se encuentran anidadas en esta misma clase.
 * Este grafo NO esta preparado para busquedas BFS ni DFS; Aunque se tiene implementado un vertice con estado.
 * @author Jeremias
 *
 * @param <V>
 * @param <E>
 */
public class DigrafoMatrizAdyacencia<V,E> implements GraphD<V, E> {
	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E> [][] matriz;
	protected int cantVertices;
	
	//Clases anidadas (Vertice<V> y Arco<V,E>)
	private class Vertice<V> implements Vertex<V>{
		private V rotulo;
		private int indice;
		private Position<Vertex<V>> posEnVertices;
		//En este grafo NO se usa
		private boolean visitado;
		
		public Vertice(V rotulo, int indice, Position<Vertex<V>> pos) {
			this.rotulo = rotulo;
			this.indice = indice;
			posEnVertices = pos;
			visitado = false;
		}
		@Override
		public V element() {			
			return rotulo;
		}
		//Setters & Getters
		public void setElement(V rotulo) { this.rotulo = rotulo;}
		public void setIndice(int indice) { this.indice = indice; }
		public void setPosEnVertices(Position<Vertex<V>> pos) { posEnVertices = pos; }
		public int getIndice() { return indice; }
		public Position<Vertex<V>> getPosEnVertices() { return posEnVertices; }
		//Esta parte es para busquedas DFS y BFS, en este grafo no se aplican
		@Override
		public boolean getEstado() {return visitado;}				
		@Override
		public void setEstado(boolean est) { visitado = est;}				
	}
	private class Arco<V,E> implements Edge<E>{
		private E rotulo;
		//private Vertex<V> vertice1, vertice2; //PARA GRAFOS NO DIRIGIDOS.
		private Vertex<V> predecesor, sucesor; //PARA GRAFOS DIRIGIDOS
		private Position<Edge<E>> posicionEnArcos;
		
		public Arco(E rotulo, Vertex<V> predecesor, Vertex<V> sucesor, Position<Edge<E>> pos) {
			this.rotulo = rotulo;
			this.predecesor = predecesor;
			this.sucesor = sucesor;
			posicionEnArcos = pos;
		}
		@Override
		public E element() {			
			return rotulo;
		}
		//Setters & Getters
		public void setElement(E rotulo) { this.rotulo = rotulo; }
		public void setPredecesor(Vertex<V> v) { predecesor = v; }
		public void setSucesor(Vertex<V> v) { sucesor = v; }
		public void setPosEnArcos(Position<Edge<E>> pos) { posicionEnArcos = pos; }
		public Vertex<V> getPredecesor() { return predecesor; }
		public Vertex<V> getSucesor() { return sucesor; }
		public Position<Edge<E>> getPosEnArcos() { return posicionEnArcos; }
	}
	
	@SuppressWarnings("unchecked")
	public DigrafoMatrizAdyacencia(int n) {
		vertices = new ListaDoblementeEnlazada<Vertex<V>>();
		arcos = new ListaDoblementeEnlazada<Edge<E>>();
		matriz =(Edge<E>[][]) new Arco[n][n];
		cantVertices = 0;
	}
	
	@Override	
	public Iterable<Vertex<V>> vertices() {	
		Iterable<Vertex<V>> toReturn = new ListaDoblementeEnlazada<Vertex<V>>();
		for (Vertex<V> aux: vertices) {
			((PositionList<Vertex<V>>) toReturn).addLast(aux);
		}
		return toReturn;
	}
	
	@Override
	public Iterable<Edge<E>> edges() {
		Iterable<Edge<E>> toReturn = new ListaDoblementeEnlazada<Edge<E>>();
		for (Edge<E> aux: arcos) {
			((PositionList<Edge<E>>) toReturn).addLast(aux);
		}
		return toReturn;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vertice = checkVertex(v);
		Iterable<Edge<E>> toReturn = new ListaDoblementeEnlazada<Edge<E>>();
		int indexV = vertice.getIndice();
		for (int fil = 0; fil<cantVertices; fil++) {
			if (matriz[fil][indexV] != null) {
				((PositionList<Edge<E>>) toReturn).addLast(matriz[fil][indexV]);
			}
		}
		return toReturn;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vertice = checkVertex(v);
		Iterable<Edge<E>> toReturn = new ListaDoblementeEnlazada<Edge<E>>();
		int indexV = vertice.getIndice();
		for (int col = 0; col<cantVertices; col++) {
			if (matriz[indexV][col] != null)
				((PositionList<Edge<E>>) toReturn).addLast(matriz[indexV][col]);				
		}
		return toReturn;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V> vertice = checkVertex(v);
		Arco<V,E> arco = checkEdge(e);
		Vertex<V> toReturn = null;
		if (arco.getPredecesor() != v) throw new InvalidVertexException("ERROR el vertice no es el predecesor en el arco.");
		toReturn = arco.getSucesor();
		return toReturn;
	}

	@Override
	public Vertex<V>[] endVertices(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = checkEdge(e);
		Vertex<V>[] toReturn = new Vertex[2];
		toReturn[0] = arco.getPredecesor();
		toReturn[1] = arco.getSucesor();
		return toReturn ;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertice<V> sucesor = checkVertex(v);
		Vertice<V> predecesor = checkVertex(w);
		boolean toReturn = false;
		int col = sucesor.getIndice();
		int fil = predecesor.getIndice();
		if (matriz[fil][col]!=null)
			toReturn = true;
		return toReturn;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V> vertice = checkVertex(v);
		V toReturn = vertice.element();
		vertice.setElement(x);
		return toReturn;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertex<V> toReturn = new Vertice(x, cantVertices++, null);
		vertices.addLast(toReturn);
		try {
			((Vertice<V>) toReturn).setPosEnVertices(vertices.last());
		} catch (EmptyListException e) { /*No deberia ocurrir*/}
		if(cantVertices>=matriz.length) resize();
		return toReturn;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x) throws InvalidVertexException {
		Vertice<V> predecesor = checkVertex(v);
		Vertice<V> sucesor = checkVertex(w);
		int indiceP = predecesor.getIndice();
		int indiceS = sucesor.getIndice();
		Arco<V,E> toReturn = new Arco(x, predecesor, sucesor, null);		
		try {
			arcos.addLast(toReturn);
			toReturn.setPosEnArcos(arcos.last());
			matriz[indiceP][indiceS] = toReturn;
		} catch (EmptyListException e) {}
			
		return toReturn;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vertice = checkVertex(v);
		V toReturn = vertice.element();
		int indexV = vertice.getIndice();
		try {
			vertices.remove(vertice.getPosEnVertices());
			//Elimina los arcos incidentes y sucesores.
			for (int index = 0; index<cantVertices; index++) {
				if (matriz[index][indexV] != null) { 
					//No utilizo removeEdge() porque es menos eficiente que eliminar el arco a mano.
					arcos.remove(((Arco<V,E>) matriz[index][indexV]).getPosEnArcos());
					matriz[index][indexV] = null;
				}
				if (matriz[indexV][index] != null) {
					arcos.remove(((Arco<V,E>) matriz[indexV][index]).getPosEnArcos());
					matriz[indexV][index] = null;
				}
			}

		} catch (InvalidPositionException e) {throw new InvalidVertexException("ERROR, vertice/arco no encontrado en lista");}				
		return toReturn;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = checkEdge(e);
		E toReturn = arco.element();
		int indexP = ((Vertice<V>) arco.getPredecesor()).getIndice();
		int indexS = ((Vertice<V>) arco.getSucesor()).getIndice();
		try { 
			arcos.remove(arco.getPosEnArcos());
			matriz[indexP][indexS] = null;
		}catch(InvalidPositionException exc) { throw new InvalidEdgeException("ERROR arco no encontrado en lista.");  }
		return toReturn;
	}

	private void resize() {
		Edge<E>[][] aux = (Edge<E>[][]) new Arco[cantVertices*2][cantVertices*2];
		int col, fil;
		//Revisar la lista de arcos es igual o mas eficiente que revisar la matriz entera,
		// depende cuantos arcos por vertice haya.
		for (Edge<E> arco: arcos) {
			fil = ((Vertice<V>) ((Arco<V,E>) arco).getPredecesor()).getIndice();
			col = ((Vertice<V>) ((Arco<V,E>) arco).getSucesor()).getIndice();
			aux[fil][col] = arco;
		}
		matriz = aux;
	}
	private Vertice<V> checkVertex(Vertex<V> v) throws InvalidVertexException{
		Vertice<V> toReturn = null;
		try {
			toReturn = (Vertice<V>) v;			
			if (toReturn.element() == null) throw new InvalidVertexException("Error vertice de rotulo nulo.");
			if (toReturn.getIndice()<0 || toReturn.getIndice()>cantVertices) throw new InvalidVertexException("ERROR vertice con indice inadecuado.");
			if (toReturn.getPosEnVertices() == null) throw new InvalidVertexException("Error vertice de pocision en lista nulo.");
			//Podria probar si la posicion pertenece al de la lista de vertices del grafo.
		}catch(ClassCastException exc) { throw new InvalidVertexException("Error vertice no coincide con el utilizado en este grafo."); }
		return toReturn;
	}
	private Arco<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException{
		Arco<V,E> toReturn = null;
		try {
			toReturn = (Arco<V,E>) e;			
			if (toReturn.element() == null) throw new InvalidEdgeException("ERROR arco con rotulo nulo.");
			if (toReturn.getPredecesor() == null) throw new InvalidEdgeException("ERROR arco con predecesor nulo.");
			if (toReturn.getSucesor() == null) throw new InvalidEdgeException("ERROR arco con sucesor nulo.");
			if (toReturn.getPosEnArcos() == null) throw new InvalidEdgeException("ERROR arco con posicion en lista nulo.");
		}catch(ClassCastException exc) { throw new InvalidEdgeException("ERROR arco no coincide con el utilizado en este grafo."); } 
		return toReturn;
	}
}
