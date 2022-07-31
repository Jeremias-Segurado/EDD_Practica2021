package TDDABinaryTree;

import Genericidad.*;
import TDALista.BoundaryViolationException;
import TDALista.InvalidPositionException;

public class BTNode<E> implements BTPosition<E> {

	private E element;
	private BTPosition<E> left, right, parent;
	
	public BTNode(E element, BTPosition<E> left, BTPosition<E> right, BTPosition<E> parent) {
		this.element = element;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	@Override
	public E element() {		
		return element;
	}

	@Override
	public void setParent(BTPosition<E> p) throws InvalidPositionException {		
		if (p == null) throw new InvalidPositionException("BTNode:: setParent(): ERROR posicion nula.");
		parent = p;
	}

	@Override
	public void setLeft(BTPosition<E> i) throws InvalidPositionException {
		if (i == null) throw new InvalidPositionException("BTNode:: setParent(): ERROR posicion nula."); 
		left = i;
	}

	@Override
	public void setRigth(BTPosition<E> d) throws InvalidPositionException {
		if (d == null) throw new InvalidPositionException("BTNode:: setParent(): ERROR posicion nula.");
		right = d;
	}

	@Override
	public BTPosition<E> getParent() throws BoundaryViolationException {
		if (parent==null) throw new BoundaryViolationException("BTNode::getParent: ERROR nodo sin padre.");
		return parent;
	}

	@Override
	public BTPosition<E> getRight() throws BoundaryViolationException {
		if (right == null) throw new BoundaryViolationException("BTNode::getParent: ERROR nodo sin hermano a la derecha.");
		return right;
	}

	@Override
	public BTPosition<E> getLeft() throws BoundaryViolationException {
		if (left == null) throw new BoundaryViolationException("BTNode::getParent: ERROR nodo sin hermano a la izquierda.");
		return left;
	}

}
