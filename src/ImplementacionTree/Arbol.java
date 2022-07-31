package ImplementacionTree;
import java.util.Iterator;

import Genericidad.Position;
import ImplementacionLista.ListaDoblementeEnlazada;
import TDALista.BoundaryViolationException;
import TDALista.InvalidPositionException;
import TDALista.PositionList;
import TDATree.*;

public class Arbol<E> implements Tree<E> {

	protected TNodo<E> raiz;
	protected int size;
	
	public Arbol(){
		raiz = null;
		size = 0;
	}
	
	@Override
	public int size() {		
		return size;
	}

	@Override
	public boolean isEmpty() {		
		return raiz == null;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> aux = checkPosition(v);
		return !aux.getHijos().isEmpty();
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> aux = checkPosition(v);		
		return aux.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> aux = checkPosition(v);
		return aux == raiz;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (!isEmpty()) throw new InvalidOperationException("Arbol::createRoot(): ERROR arbol no vacio.");
		raiz = new TNodo<E>(e);
		size = 1;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if (isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR arbol vacio.");		
		TNodo<E> toRemove = checkPosition(p);
		if (!toRemove.getHijos().isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR posicion no es un nodo externo.");
		TNodo<E> parent = toRemove.getPadre();	
		Iterator<Position<TNodo<E>>> lista_hermanos =  parent.getHijos().positions().iterator();
		Position<TNodo<E>> cursor = null;
		boolean encontrado = false;
		
		while (!encontrado && lista_hermanos.hasNext())
		{
			cursor = lista_hermanos.next();
			if (cursor.element() == toRemove)
			{
				encontrado = true;
			}
		}
		if (!encontrado) throw new InvalidPositionException("Arbol::remove: ERROR posicion no encontrada en la lista de hijos del padre, arbol corrupto??.");
		parent.getHijos().remove(cursor);
		size--;
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if (isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR arbol vacio.");
		TNodo<E> toRemove = checkPosition(p);
		if (toRemove.getHijos().isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR posicion no es un nodo interno.");
		TNodo<E> parent = toRemove.getPadre();
		Iterator<Position<TNodo<E>>> lista_hermanos = parent.getHijos().positions().iterator();
		boolean encontrado = false;
		Position<TNodo<E>> cursor = null;		
		//Comprueba que el nodo exista en la lista de hijos del padre
		while (!encontrado && lista_hermanos.hasNext())
		{
			cursor = lista_hermanos.next();
			if (cursor.element() == toRemove)
				encontrado = true;
		}
		if (!encontrado) throw new InvalidPositionException("Arbol::remove: ERROR posicion no encontrada en la lista de hijos del padre, arbol corrupto??.");		
		//Agregar los hijos del nodo a remover al padre del mismo.
		for (Position<TNodo<E>> hijo: toRemove.getHijos().positions())
		{	
			hijo.element().setPadre(parent);
			parent.getHijos().addLast(hijo.element());			
		}
		parent.getHijos().remove(cursor);
		size--;
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> aux = checkPosition(p);		
		if (isInternal(p))
		{
			removeInternalNode(p);
		}else //Si el nodo es externo.
		{
			removeExternalNode(p);
		}		
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if (isEmpty()) throw new EmptyTreeException("Arbol::root(): ERROR arbol vacio.");		
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo_aux = checkPosition(v);
		Position<E> resul = null;
		if (nodo_aux == raiz) throw new BoundaryViolationException("Arbol::parent(): ERROR posicion pertenece a la raiz.");
		resul = nodo_aux.getPadre();
		return resul;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> parent = checkPosition(p);
		TNodo<E> resul = new TNodo<E>(e, parent);
		PositionList<TNodo<E>> hijos = parent.getHijos();
		hijos.addFirst(resul);
		size++;
		return resul;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> parent = checkPosition(p);
		TNodo<E> resul = new TNodo<E>(e, parent);
		PositionList<TNodo<E>> hijos = parent.getHijos();
		hijos.addLast(resul);
		size++;
		return resul;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		TNodo<E> parent = checkPosition(p);
		TNodo<E> right_brother = checkPosition(rb);
		TNodo<E> resul = null;	
		Iterator<Position<TNodo<E>>> lista_hijos_recorrible = parent.getHijos().positions().iterator();
		boolean pertenece = false;
		Position<TNodo<E>> hermano_aux = null;
		while (!pertenece && lista_hijos_recorrible.hasNext())
		{
			hermano_aux = lista_hijos_recorrible.next();
			if (hermano_aux.element()==(right_brother))
			{
				pertenece = true;
			}			
		}
		if (pertenece) throw new InvalidPositionException("Arbol::addBefore: ERROR posicion no pertenece al arbol.");		
		resul = new TNodo<E>(e, parent);
		parent.getHijos().addBefore(hermano_aux, resul);		
		return resul;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		TNodo<E> parent = checkPosition(p);
		TNodo<E> left_brother = checkPosition(lb);
		TNodo<E> resul = null;	
		Iterator<Position<TNodo<E>>> lista_hijos_recorrible = parent.getHijos().positions().iterator();
		boolean pertenece = false;
		Position<TNodo<E>> hermano_aux = null;
		while (!pertenece && lista_hijos_recorrible.hasNext())
		{
			hermano_aux = lista_hijos_recorrible.next();
			if (hermano_aux.element()==(left_brother))
			{
				pertenece = true;
			}			
		}
		if (pertenece) throw new InvalidPositionException("Arbol::addBefore: ERROR posicion no pertenece al arbol.");		
		resul = new TNodo<E>(e, parent);
		parent.getHijos().addAfter(hermano_aux, resul);		
		return resul;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> aux = checkPosition(v);
		E resul = aux.element();
		aux.setElemento(e);
		return resul;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDoblementeEnlazada<E>();
		if (!isEmpty()) 
			preordenIterator(lista, raiz);
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new ListaDoblementeEnlazada<Position<E>>();
		if (!isEmpty())
			preorden(lista, raiz);
		return lista;
	}
	
	private void preorden(PositionList<Position<E>> list, TNodo<E> p) {
		list.addLast(p);
		for (TNodo<E> hijo: p.getHijos()) 
		{
			preorden(list, hijo);
		}
	}
	
	private void preordenIterator(PositionList<E> list, TNodo<E> p) {
		list.addLast(p.element());
		for (TNodo<E> hijo: p.getHijos())
		{
			preordenIterator(list, hijo);
		}
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> parent = checkPosition(v);		
		PositionList<Position<E>> resul = new ListaDoblementeEnlazada<Position<E>>();
		for (TNodo<E> hijo: parent.getHijos())
		{
			resul.addLast(hijo);;
		}
		return resul;
	}
	
	private TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if(p == null) throw new InvalidPositionException("Arbol:: ERROR posicion nula");
		TNodo<E> resul = null;
		try 
		{
			resul = (TNodo<E>) p;
		} catch (ClassCastException e) { throw new InvalidPositionException("Arbol:: ERROR posicion no pertenece al de un arbol."); }		
		return resul;
	}

}
