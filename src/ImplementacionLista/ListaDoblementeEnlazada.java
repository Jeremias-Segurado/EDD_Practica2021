package ImplementacionLista;
import java.util.Iterator;

import Genericidad.Position;
import TDALista.*;
/**
 * Clase ListaDoblementeEnlazada, utiliza una estructura de nodos doblemente enlazada
 * @author Jeremias Eloy, Segurado Negrin
 *
 * @param <E> Tipo de dato generico
 */

public class ListaDoblementeEnlazada<E> implements PositionList<E> {

	protected DNodo<E> cabeza, cola;
	protected int longitud; 
	
	public ListaDoblementeEnlazada() {
		cabeza = new DNodo<E>(null, null, null);
		cola = new DNodo<E>(null, null, cabeza);
		cabeza.setNext(cola);
		longitud = 0;
	}
	@Override
	public void addFirst(E e) {
		DNodo<E> aux = new DNodo<E>(e, cabeza.getNext(), cabeza);
		cabeza.getNext().setPrev(aux);
		cabeza.setNext(aux);	
		longitud++;
	}

	@Override
	public void addLast(E e) {
		DNodo<E> aux = new DNodo<E>(e, cola, cola.getPrev());
		cola.getPrev().setNext(aux);
		cabeza.setPrev(aux);	
		longitud++;
	}

	@Override
	public void addAfter(Position<E> p, E e) throws InvalidPositionException {
		DNodo<E> prev = checkPosition(p);
		DNodo<E> aux = new DNodo<E>(e, prev.getNext(), prev);
		prev.setNext(aux);
		aux.getNext().setPrev(aux);
		longitud++;
	}

	@Override
	public void addBefore(Position<E> p, E e) throws InvalidPositionException {
		DNodo<E> prev = checkPosition(p);
		DNodo<E> aux = new DNodo<E>(e, prev.getNext(), prev);
		prev.setNext(aux);
		aux.getNext().setPrev(aux);
		longitud++;		
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNodo<E> aux = checkPosition(p);
		E retorno = aux.element();
		aux.getPrev().setNext(aux.getNext());
		aux.getNext().setPrev(aux.getPrev());
		longitud--;
		return retorno;
	}

	@Override
	public E set(Position<E> p, E e) throws InvalidPositionException {
		DNodo<E> aux = checkPosition(p);
		E retorno = aux.element();
		aux.setElement(e);
		return retorno;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if (cabeza.getNext()==cola) throw new EmptyListException("ListaDElnlazada::first(): ERROR lista vacia.");		
		return cabeza.getNext();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if (cola.getPrev()==cabeza) throw new EmptyListException("ListaDEnlazada::last():ERROR lista vacia.");
		return cola.getPrev();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> aux = checkPosition(p);
		if (aux.getNext() == cola) throw new BoundaryViolationException("ListaDEnlazada::last():ERROR no existe elemento siguiente.");		
		return aux.getNext(); 
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> aux = checkPosition(p);
		if (aux.getPrev() == cola) throw new BoundaryViolationException("ListaDEnlazada::last():ERROR no existe elemento anterior.");
		return aux;
	}

	@Override
	public int size() {		
		return longitud;
	}

	@Override
	public boolean isEmpty() {	
		return longitud==0;
	}

	@Override
	public Iterator<E> iterator() {		
		return new ElementIterator<>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> resul = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if (longitud>0) {
				Position<E> aux = first();
				while (aux!=cola) {
					resul.addLast(aux);
					aux = ((DNodo<E>) aux).getNext();
				}
			}	
		}catch (EmptyListException e) {
			e.printStackTrace();
		}
 		return resul;
	}

	/**
	 * Chequea posicion pasada por parametro y comprueba que pertenesca a la estructura.
	 * @param p Posicion a chequear.
	 * @return nodo perteneciente a la estructura que representa la posicion, null si no existe.
	 * @throws InvalidPositionException si la posicion no pertenece a la estructura.
	 */
	protected DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		DNodo<E> aux = null;
		if (p==null) throw new InvalidPositionException("ERROR, posicion nula.");
		if (p==cabeza) throw new InvalidPositionException("ERROR, la posicion de la cabeza de la lista no es una posicion valida.");
		if (p==cola) throw new InvalidPositionException("ERROR, la posicion de la cola de la lista no es una posicion valida.");
		try {
			aux = (DNodo<E>) p;
			if (aux.getNext()==null || aux.getPrev()==null) 
				throw new InvalidPositionException("ERROR, la posicion no pertenece a una de lista doblemente enlazada.");
		}catch(ClassCastException e) {
			throw new InvalidPositionException("ERROR, la posicion no pertenece al de un nodo doblemente enlazado.");
		}
		return aux;
	}
}
