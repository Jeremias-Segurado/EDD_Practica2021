package Final;

import java.util.Iterator;

import ImplementacionLista.ListaDoblementeEnlazada;
import TDA_Graph.*;

public class Final2021_prueba<V> {

	public Iterable<Vertex<V>> RecorrerYComputar(GraphD<V, Integer> g, V r, Integer k) throws InvalidVertexException{
		Iterable<Vertex<V>> toReturn = new ListaDoblementeEnlazada<Vertex<V>>();
		Iterator<Vertex<V>> lista = g.vertices().iterator();
		Vertex<V> cursor = null;
		boolean encontrado = false;
		while(lista.hasNext() && !encontrado) {
			cursor = lista.next();
			if (cursor.element().equals(r)) {
				encontrado = true;
			}
		}
		if (!encontrado) throw new InvalidVertexException("Error vertice no hallado.");
		
		return toReturn;
	}
	/*
	 * Algoritmo: DFSshell(grafo g)
	 * 	para cada vertice v de g hacer
	 * 		marcar v como NO visitado
	 * 	para cada vertice v de g hacer
	 * 		si v no esta visitado entonces
	 * 			DFS(g, v) 
	 */
	
	private Vertex<V> DFSshell(GraphD<V,Integer> g, int k) {
		Vertex<V> toReturn = null;
		for (Vertex<V> cursor: g.vertices()) {
			cursor.setEstado(false);
		}
		for (Vertex<V> cursor: g.vertices()) {
			if (cursor.getEstado() == false)
				toReturn = DFS(g, cursor, k);
			if (toReturn != null)
				return toReturn;
		}
		return toReturn;
	}
	/*
	 * Algoritmo: DFS(grafo g, vertice v)
	 * 	[procesamiento de v previo al recorrido]
	 * 	marcar a v como visitado
	 * 	para cada vertice w adyacente a v en g hacer
	 * 		si w no esta visitado entonces
	 * 			DFS(g, w)
	 * 	[procesamiento de v posterior al recorrido]
	 */
	private Vertex<V> DFS(GraphD<V, Integer> g, Vertex<V> v, int k) {
		
		//Procesamento PREVIO al recorrido
		Vertex<V> toReturn = null;
		v.setEstado(true);
		try {
			for (Edge<Integer> arco: g.succesorEdges(v)) {
				Vertex<V> sucesor = g.endVertices(arco)[1];
				int cant_vertices = 0;
				for (Edge<Integer> aux: g.succesorEdges(sucesor)) {
					cant_vertices++;
				}
				if (cant_vertices == k) 
					return sucesor; //El return quiebra el FOR()
				else { 
					if (sucesor.getEstado() == false)
						toReturn = DFS(g, sucesor, k);
					if (toReturn != null)
						return toReturn;
				}
			}
		} catch (InvalidVertexException | InvalidEdgeException e) { toReturn = null;}				
		//Procesamiento POSTERIOR al recorrido
		return toReturn;
	}
}
