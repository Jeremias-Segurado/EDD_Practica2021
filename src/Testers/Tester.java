package Testers;
import TDAPila.*;

public class Tester {
	public static void main(String[] args) {
		Stack<Integer> pila1;
		pila1 = new PilaArreglo<Integer>();
		int aux = -1;
		
		try {
			aux = pila1.top();
		} catch (EmptyStackException e) {			
			e.printStackTrace();
		}
		
		System.out.println(aux);
		pila1.push(20);
		pila1.push(2);
		pila1.push(5);
		
		try {
			aux = pila1.top();
		} catch (EmptyStackException e) {			
			e.printStackTrace();
		}
		System.out.println(aux);
	}
}
