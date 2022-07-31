package TDA_Graph;
import Genericidad.*;
/**
 * Arco utilizado en Grafos implementados con lista de arcos.
 * @author Jeremias
 *
 * @param <V>
 * @param <E>
 */
public class Arco<V, E> implements Edge<E> {
	private E rotulo;
	private Position<Arco<V,E>> posicionEnListaArco;
	private Vertice<V> cola, punta;
	
	public Arco(E rotulo, Vertice<V> cola, Vertice<V> punta, Position<Arco<V, E>> pos) {
		this.rotulo = rotulo;
		this.cola = cola;
		this.punta = punta;	
		posicionEnListaArco = pos;
	}
	@Override
	public E element() {		
		return rotulo;
	}
	//Setters and Getters
	public void setRotulo(E rotulo) { this.rotulo = rotulo; }
	public void setCola(Vertice<V> cola) { this.cola = cola; }
	public void setPunta(Vertice<V> punta) { this.punta = punta; }
	public void setPosEnLista(Position<Arco<V,E>> pos) { posicionEnListaArco = pos; }
	public Vertice<V> getCola(){ return cola; }
	public Vertice<V> getPunta(){ return punta; }
	public Position<Arco<V, E>> getPosEnLista(){ return posicionEnListaArco; }
}
