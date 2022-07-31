package TDAPila;
/**
 * Implementacion de la interfaz Stack usando un arreglo de elementos.
 * @author Jeremias
 *
 */
public class PilaArreglo<E> implements Stack<E> {

	protected E[] stack;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public PilaArreglo() {
		stack = (E[]) new Object[20];
		size = 0;
	}
	
	@Override
	public E top() throws EmptyStackException {		
		if (size == 0) 
		{ 
			throw new EmptyStackException("PilaArreglo::top(): ERROR pila vacia.");
		}else
			return stack[size-1];		
	}
	@Override
	public E pop() throws EmptyStackException {		
		E aux = null;
		if (size==0)
		{
			throw new EmptyStackException("PilaArreglo::pop(): ERROR pila vacia.");
		}
		else
		{
			aux = stack[size-1];
			stack[size-1] = null;
			size--;
		}
		return aux;
	}
	@Override
	public void push(E item) {
		if (size==stack.length)
		{
			resize();
		}
		if (item != null)
		{
			stack[size] = item;
			size++;
		}					
	}
	@Override
	public boolean isEmpty() {		
		return size==0;
	}
	@Override
	public int size() {		
		return size;
	}
	
	private void resize() {		
		@SuppressWarnings("unchecked")
		E[] aux = (E[]) new Object[stack.length*2];		
		for (int i=0; i<size; i++) 
		{
			aux[i] = stack[i]; 
		}
		stack = aux;
	}
}
