package TDALista;
import java.util.Iterator;
import java.util.NoSuchElementException;

import Genericidad.Position;


public class ElementIterator<E> implements Iterator<E> {

	protected PositionList<E> lista; //Lista a iterar
	protected Position<E> cursor; //Posicion del elemento corriente
	
	
	public ElementIterator(PositionList<E> l){
		lista = l; //referencia a la lista a recorrer.
		try {
			cursor = l.first();
		} catch (EmptyListException e) {
			cursor = null;
		}
	}
	@Override
	public boolean hasNext() {		
		return cursor != null;
	}

	@Override
	public E next() throws NoSuchElementException{
		if (cursor == null)	 throw new NoSuchElementException("ElementIterator::next(): ERROR no hay siguiente elemento.");
		E retorno = cursor.element();
		try {
			if (cursor == lista.last()) 
			{
				cursor = null;
			} 
			else 
			{
				cursor = lista.next(cursor);
			}
		} catch (EmptyListException e) {			
			e.printStackTrace();
		} catch (InvalidPositionException e) {			
			e.printStackTrace();
		} catch (BoundaryViolationException e) {			
			e.printStackTrace();
		}
		return retorno;
	}

}
