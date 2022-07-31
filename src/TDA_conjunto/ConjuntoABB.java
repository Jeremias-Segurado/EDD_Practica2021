package TDA_conjunto;



import ImplementacionLista.ListaDoblementeEnlazada;
import TDALista.PositionList;
import TDA_ABB.*; 

public class ConjuntoABB<E extends Comparable<E>> {
	protected ABB<E> arbol;
	protected int size;
	
	public PositionList<E> ejer2_final(E x, int H){
		PositionList<E> toReturn = new ListaDoblementeEnlazada<E>(); //c1
		int altura_total = arbol.alturaTotal();
		ejer2_final_aux( arbol.root(), x, H, altura_total, toReturn);
		return toReturn; //c2
	}
	
	private void ejer2_final_aux( NodoABB<E> v , E x, int H, int altura_acltual, PositionList<E> lista){
		if (v.getIzq() != null)
			ejer2_final_aux( v.getIzq(), x, H, altura_acltual--, lista);
		if(altura_acltual >= H && v.getRotulo().compareTo(x)<=0) { //c3 y c4
			lista.addLast(v.getRotulo());                          //c5 y c6
		}
		if (v.getIzq() != null)
			ejer2_final_aux( v.getIzq(), x, H, altura_acltual--, lista);
	}
	/*
	 * Tiempo de ejecucion de ejer2_final:
	 * 
	 * -la funcion "alturaTotal()" es O(n) al usar un recorrido inorder para sumar altura,
	 * por cada iteracion se ejecuta la asignacion de variables y Return que son de orden O(1);
	 * 
	 * 
	 * -el tiempo ejecucion ejer2_final_aux utiliza metodo Inorder para recorrer el arbol que es de orden O(n)
	 * 	mas getRoturlo(), compareTo() y addLast() son orden O(1);
	 * 
	 * -Se recorre el arbol entero en los dos casos.
	 * 
	 * T(n) = c1+ n + n*(c3 + c4+ c5 + c6) + c2 ==> T(n) = O(2n)
	 */
}
