package TDA_Graph;
import Genericidad.Position;

/**
 * Vertice utilizado para un Grafo implementado con lista de arcos.  
 * @author Jeremias
 *
 * @param <V>
 */
public class Vertice<V> implements Vertex<V> {
	private V rotulo;
	private Position<Vertice<V>> posicionEnListaVertices;
	private boolean visitado;
	
	public Vertice(V rotulo) {
		this.rotulo = rotulo;
		posicionEnListaVertices = null;
		visitado = false;
	}
	@Override
	public V element() {		
		return rotulo;
	}
	//setters y getters
	public void setRotulo(V rotulo) { this.rotulo = rotulo;	}	
	public Position<Vertice<V>> getPosicionEnLista(){ return posicionEnListaVertices; }			
	public void setPosicionEnLista(Position<Vertice<V>> pos) { posicionEnListaVertices = pos; }
	@Override
	public boolean getEstado() {return visitado;}				
	@Override
	public void setEstado(boolean est) { visitado = est;}						
}
