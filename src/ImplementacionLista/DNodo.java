package ImplementacionLista;
import Genericidad.Position;
import TDALista.*;

public class DNodo<E> implements Position<E> {

	protected DNodo<E> next, prev;
	protected E elemento;
	
	
	public DNodo(E elem, DNodo<E> next, DNodo<E> prev) {
		this.next = next;
		this.prev = prev;
		elemento = elem;
	}
	
	public void setElement(E elem) {
		this.elemento = elem;
	}
	public void setNext(DNodo<E> next) {
		this.next = next;
	}
	public void setPrev(DNodo<E> prev) {
		this.prev = prev;
	}
	public DNodo<E> getNext(){		
		return next;
	}
	public DNodo<E> getPrev(){
		return prev;
	}
	@Override
	public E element() {		
		return elemento;
	}
}
