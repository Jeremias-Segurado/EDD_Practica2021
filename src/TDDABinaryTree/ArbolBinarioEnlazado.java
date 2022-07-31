package TDDABinaryTree;

import java.util.Iterator;
import Genericidad.BTPosition;
import Genericidad.Position;
import ImplementacionLista.ListaDoblementeEnlazada;
import TDALista.BoundaryViolationException;
import TDALista.InvalidPositionException;
import TDALista.PositionList;
import TDATree.EmptyTreeException;
import TDATree.InvalidOperationException;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {

	protected BTPosition<E> raiz;
	protected int size;
	
	public ArbolBinarioEnlazado() {
		raiz = null;
		size = 0;
	}
	
	@Override
	public int size() {		
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {		
		return hasLeft(v) || hasRight(v);
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {			
		return !hasLeft(v) && !hasRight(v);
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> aux = checkPosition(v);
		return aux == raiz;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (!isEmpty()) throw new InvalidOperationException("Arbol::createRoot(): ERROR arbol no vacio.");
		raiz = new BTNode<E>(e, null, null, null);
		size = 1;
	}
	
	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if (isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR arbol vacio.");		
		if (!isExternal(p)) throw new InvalidPositionException("Arbol::remove: ERROR posicion no es un nodo externo.");
		BTPosition<E> toRemove = checkPosition(p);
		if (toRemove==raiz)
		{
			raiz = null;
			size = 0;
		}else		
			try 
			{
				BTNode<E> parent = (BTNode<E>) toRemove.getParent();
				//Se crea nuevo nodo padre para poder fijar los hijos sin saltar error (NO se puede hacer parent.setLeft(NULL)).
				BTNode<E> new_parent = new BTNode<E>(parent.element(), null, null, parent.getParent());
				if (parent.getLeft()==toRemove)
				{
					if (hasRight(parent)) 
					{
						new_parent.setLeft(parent.getRight());				
					}				
				}else 		
					if (parent.getRight()==toRemove)
					{
						new_parent.setLeft(parent.getLeft());;
					}else 
						throw new InvalidPositionException("Arbol::remove: ERROR posicon no encontrada en los hijos del padre, arbol corrupto??.");
				//Fijamos el nuevo nodo padre el en abuelo del nodo a remover, asumo que la posicion del padre no esta corrupta.
				BTNode<E> grandparent = (BTNode<E>) parent.getParent();
				if (grandparent.getLeft()==parent)
					grandparent.setLeft(new_parent);
				else 
					grandparent.setRigth(new_parent);		
				size--;
			}catch (BoundaryViolationException | InvalidPositionException e) {	throw new InvalidPositionException("Arbol::remove: ERROR ==> "+e.getMessage()); }
	}
		
	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if (isEmpty()) throw new InvalidPositionException("Arbol::remove: ERROR arbol vacio.");		
		if (!isInternal(p)) throw new InvalidPositionException("Arbol::remove: ERROR posicion no es un nodo interno.");
		BTPosition<E> toRemove = checkPosition(p);
		if (toRemove == raiz) throw new InvalidPositionException("Arbol::remove: ERROR posicion pertenece a la raiz.");
		try 
		{				
			BTNode<E> parent = (BTNode<E>) toRemove.getParent();						
			if (hasLeft(toRemove) && hasRight(toRemove)) throw new InvalidPositionException("Arbol::remove: ERROR nodo con mas de un hijo, no se puede remover en un arbol binario.");
			BTNode<E> hijo = (BTNode<E>) toRemove.getLeft();
			//Se crea nuevo nodo padre para poder fijar los hijos sin saltar error (NO se puede hacer parent.setLeft(NULL)).
			BTNode<E> new_parent = new BTNode<E>(parent.element(), null, null, parent.getParent());
			if (parent.getLeft()==toRemove)		
				new_parent.setLeft(hijo);				
			else 		
				if (parent.getRight()==toRemove)
				{
					new_parent.setLeft(parent.getLeft());
					new_parent.setRigth(hijo);
				}else 
					throw new InvalidPositionException("Arbol::remove: ERROR posicon no encontrada en los hijos del padre, arbol corrupto??.");			
			hijo.setParent(new_parent);
			//Insertar la referencia del nuevo padre en el abuelo del nodo eliminado. 
			//Se asume que la posicion del padre se encuentra correctamente en el arbol.
			BTNode<E> grandparent = (BTNode<E>) parent.getParent();
			if (grandparent.getLeft() == parent)		
				grandparent.setLeft(new_parent);
			else 
				grandparent.setRigth(new_parent);
			
			size--;
		}catch (BoundaryViolationException | InvalidPositionException e) { throw new InvalidPositionException("Arbol::remove: ERROR ==> "+e.getMessage());	}										
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {			
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
		BTPosition<E> nodo_aux = checkPosition(v);
		Position<E> resul = null;
		if (nodo_aux == raiz) throw new BoundaryViolationException("Arbol::parent(): ERROR posicion pertenece a la raiz.");
		resul = nodo_aux.getParent();
		return resul;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		BTPosition<E> parent = checkPosition(p);
		if (hasLeft(p) && hasRight(p)) throw new InvalidPositionException("Arbol::add: ERROR nodo con dos hijos.");
		BTNode<E> resul = new BTNode<E>(e, null, null, parent);
		BTNode<E> aux;
		try {					
			if(hasLeft(p)) {
				aux = (BTNode<E>) parent.getLeft();
				parent.setLeft(resul);
				parent.setRigth(aux);
			}
			else 
				parent.setLeft(resul);			
			size++;
		}catch(BoundaryViolationException | InvalidPositionException exc) { throw new InvalidPositionException("Arbol::add: ERROR ==>"+exc.getMessage()); }
		return resul;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		BTPosition<E> parent = checkPosition(p);
		if (hasLeft(p) && hasRight(p)) throw new InvalidPositionException("Arbol::add: ERROR nodo con dos hijos.");
		BTNode<E> resul = new BTNode<E>(e, null, null, parent);							
		if (hasLeft(p))
			parent.setRigth(resul);
		else 
			parent.setLeft(resul);			
		size++;		
		return resul;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		BTPosition<E> parent = checkPosition(p);
		BTNode<E> resul = null;
		BTNode<E> right_brother = checkPosition(rb);
		if (hasLeft(parent) && hasRight(parent)) throw new InvalidPositionException("Arbol::add: ERROR nodo del padre ya tiene dos hijos.");
		try {
			
			if (hasLeft(parent))
				if (parent.getLeft() != right_brother) throw new InvalidPositionException("Arbol:: addBefore: ERROR el hermano del nodo a insertar no pertenece al padre.");			
			resul = new BTNode<E>(e, null, null, parent);
			parent.setLeft(resul);
			parent.setRigth(right_brother);
			size++;
		}catch(BoundaryViolationException | InvalidPositionException exc) {  }		
		return resul;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		BTPosition<E> parent = checkPosition(p);
		BTNode<E> resul = null;
		BTNode<E> left_brother = checkPosition(lb);
		if (hasLeft(parent) && hasRight(parent)) throw new InvalidPositionException("Arbol::add: ERROR nodo del padre ya tiene dos hijos.");
		try {			
			if (hasLeft(parent))
				if (parent.getLeft() != left_brother) throw new InvalidPositionException("Arbol:: addBefore: ERROR el hermano del nodo a insertar no pertenece al padre.");			
			resul = new BTNode<E>(e, null, null, parent);			
			parent.setRigth(resul);
			size++;
		}catch(BoundaryViolationException | InvalidPositionException exc) {  }		
		return resul;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> aux = checkPosition(v);
		E resul = aux.element();
		BTNode<E> parent = null;
		BTNode<E> left = null;
		BTNode<E> right = null;
		BTNode<E> new_node = null;
		try 
		{			
			if (hasLeft(aux))
					left = (BTNode<E>) aux.getLeft();
				if (hasRight(aux))
					right = (BTNode<E>) aux.getRight();
			if (aux != raiz) // Si nodo no es el raiz se debe cambiar la referencia del padre.
			{
				parent = (BTNode<E>) aux.getParent();				
				new_node = new BTNode<E>(e, left, right, parent);
				if (hasLeft(parent))			
					if (parent.getLeft() == aux)
						parent.setLeft(new_node);			 
				if (hasRight(parent))			
					if (parent.getRight() == aux)
						parent.setRigth(new_node);
			}else //Si el nodo es la raiz, no existe padre.
			{
				new_node = new BTNode<E>(e, left, right, parent);
				raiz = new_node;
			}
		} catch (BoundaryViolationException | InvalidPositionException e2) {  }
		return resul;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> lista = new ListaDoblementeEnlazada<E>();
		if (!isEmpty()) 
			preordenIterator(lista,(BTNode<E>) raiz);
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new ListaDoblementeEnlazada<Position<E>>();
		if (!isEmpty())
			preorden(lista,(BTNode<E>) raiz);
		return lista;
	}
	/*
	Algoritmo preorden(arbol T, nodo v)
		Visitar v
		si T.hasLeft( v ) entonces
			preorden( T, T.left(v) )
		si T.hasRight( v ) entonces
			preorden( T, T.right(v) )
	*/
	private void preorden(PositionList<Position<E>> list, BTNode<E> p) {
		list.addLast(p);
		try
		{
			if (hasLeft(p))
				preorden(list, (BTNode<E>) p.getLeft());
			if (hasRight(p))
				preorden(list, (BTNode<E>) p.getRight());
		}catch (InvalidPositionException | BoundaryViolationException e) { }		
	}
	
	private void preordenIterator(PositionList<E> list, BTNode<E> p) {
		list.addLast(p.element());
		try 
		{				
			if (hasLeft(p))
				preordenIterator(list, (BTNode<E>) p.getLeft());
			if (hasRight(p))
				preordenIterator(list, (BTNode<E>) p.getRight());
		}catch (InvalidPositionException | BoundaryViolationException e) {  } 				
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTPosition<E> parent = checkPosition(v);		
		PositionList<Position<E>> resul = new ListaDoblementeEnlazada<Position<E>>();
		try 
		{			
			if (hasLeft(parent))
				resul.addLast(parent.getLeft());
			if (hasRight(v))
				resul.addLast(parent.getRight());			
		}catch (InvalidPositionException | BoundaryViolationException e) { throw new InvalidPositionException("Arbol::children: "+e.getMessage()); }
		return resul;
	}

	private BTNode<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if (p == null) throw new InvalidPositionException("BTNode:: ERROR posicion invalida.");
		BTNode<E> aux = null;
		try {
			aux = (BTNode<E>) p;
		}catch(ClassCastException e) { throw new InvalidPositionException("BTNode:: ERROR posicion invalida."); }	
		return aux;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNode<E> aux = checkPosition(v);
		Position<E> resul = null;
		if (hasLeft(v))
			resul = aux.getLeft();
		else 
			throw new BoundaryViolationException("Arbol::left: ERROR posicion sin hijo izquierdo.");		
		return resul;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNode<E> aux = checkPosition(v);
		Position<E> resul = null;
		if (hasRight(v))
			resul = aux.getRight();
		else 
			throw new BoundaryViolationException("Arbol::left: ERROR posicion sin hijo derecho.");		
		return resul;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNode<E> aux = checkPosition(v);
		boolean resul = false;
		try {
			aux.getLeft();
			resul = true;
		}catch(BoundaryViolationException e) { resul = false; }
		return resul;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNode<E> aux = checkPosition(v);
		boolean resul = false;
		try {
			aux.getRight();
			resul = true;
		}catch(BoundaryViolationException e) { resul = false; }
		return resul;
	}
	
	public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
		BTNode<E> raiz_local = checkPosition(v);
		if (hasLeft(raiz_local) || hasRight(raiz_local)) throw new InvalidPositionException("Arbol::attach: ERROR nodo no es una hoja.");		
		BTNode<E> raiz_t1 = null;
		BTNode<E> raiz_t2 = null;
		BTNode<E> hijo_raiz_local;		
		try {
			if (!t1.isEmpty()) {
				raiz_t1 = (BTNode<E>) t1.root();
				hijo_raiz_local = new BTNode<E>(raiz_t1.element(), null, null, raiz_local);
				raiz_local.setLeft(hijo_raiz_local);
				clonar(hijo_raiz_local, raiz_t1, t1);
			}
			if (!t2.isEmpty()) {
				raiz_t2 = (BTNode<E>) t2.root();
				hijo_raiz_local = new BTNode<E>(raiz_t2.element(), null, null, raiz_local);
				raiz_local.setRigth(hijo_raiz_local);
				clonar(hijo_raiz_local, raiz_t2, t2);
			}
			size += t1.size() + t2.size();
		}catch(EmptyTreeException e) {	}
	}
	/*
	 * Clona los nodos de un arblo uno por uno recorriendo los hijos de los nodos y 
	 * enlanzandolos entre ellos para formar un clon del subarbol que empieza en el
	 * nodo que pertenece al arbol.
	 */
	protected void clonar(BTPosition<E> padre_local, BTPosition<E> padre_arbol, BinaryTree<E> arbol) {
		BTPosition<E> hijo_padre_arbol;
		BTPosition<E> hijo_padre_local;
		try {
			if (arbol.hasLeft(padre_arbol)) {
				hijo_padre_arbol = (BTPosition<E>) arbol.left(padre_arbol);
				hijo_padre_local = new BTNode<E>(hijo_padre_arbol.element(), null, null, padre_local);
				padre_local.setLeft(hijo_padre_local);
				clonar(hijo_padre_local, hijo_padre_arbol, arbol);
			}
			if (arbol.hasRight(padre_arbol)) {
				hijo_padre_arbol = (BTPosition<E>) arbol.right(padre_arbol);
				hijo_padre_local = new BTNode<E>(hijo_padre_arbol.element(), null, null, padre_local);
				padre_local.setRigth(hijo_padre_local);
				clonar(hijo_padre_local, hijo_padre_arbol, arbol);
			}
		}catch(BoundaryViolationException | InvalidPositionException e) { 		}
	}
}
