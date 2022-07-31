package Genericidad;

import TDALista.*;

public interface BTPosition<E> extends Position<E> {
	public void setParent(BTPosition<E> p) throws InvalidPositionException;
	public void setLeft(BTPosition<E> i) throws InvalidPositionException;
	public void setRigth(BTPosition<E> d) throws InvalidPositionException;
	public BTPosition<E> getParent() throws BoundaryViolationException;
	public BTPosition<E> getRight() throws BoundaryViolationException;
	public BTPosition<E> getLeft() throws BoundaryViolationException;
}
