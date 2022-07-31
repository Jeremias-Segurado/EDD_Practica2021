package Testers;
import TDALista.*;
import Genericidad.Position;
import ImplementacionLista.*;

public class ListaDEnlazadaTESTER {
	public static void main(String[] args) {
		PositionList<Integer> lista = new ListaDoblementeEnlazada<Integer>();
		Position<Integer> aux = null;
		int num = 0;
		
		try {			
			aux = lista.first();
		} catch (EmptyListException e) {			
			e.printStackTrace();
		}
		if (aux == null) {
			System.out.println("Al llamar firs() de una lista vacia, el desarroyo del metodo se interrumpe por la excepcion lanzada sin necesidad de agregar un IF()-ELSE, se corta solo.");
		}else {
			System.out.println("Al llamar firs() de una lista vacia, el desarroyo no se corta si no se usa IF(){throws EXCEPTION} ELSE{[cuerpo]}");
		}		
		lista.addFirst(20);
	}
	
}
