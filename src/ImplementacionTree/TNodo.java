package ImplementacionTree;
import Genericidad.*;
import TDALista.*;
import ImplementacionLista.*;

public class TNodo<E> implements Position<E> {

	private E elemento;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;
	
	public  TNodo(E elemento, TNodo<E> padre) {
		this.elemento = elemento;
		this.padre = padre;
		hijos = new ListaDoblementeEnlazada<TNodo<E>>();
	}
	public TNodo(E elemento ) { 
		this( elemento, null);
	}	
	public PositionList<TNodo<E>> getHijos() { 
		return hijos; 
	}
	public void setElemento(E elemento) { 
		this.elemento = elemento; 
	}
	public TNodo<E> getPadre() { 
		return padre; 
	}
	public void setPadre(TNodo<E> padre) { 
		this.padre = padre; 
	}
	@Override
	public E element() {		
		return elemento;
	}

}
