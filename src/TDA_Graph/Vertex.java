package TDA_Graph;
import Genericidad.*;

public interface Vertex<V> extends Position<V>{
	public boolean getEstado();
	public void setEstado(boolean est);
}
