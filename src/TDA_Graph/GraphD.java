package TDA_Graph;

public interface GraphD<V, E> extends Graph<V, E> {
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException;
}
