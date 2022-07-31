package Testers;
import TDAMap.*;

import java.util.Random;

import Genericidad.*;
import ImplementacionLista.*;

public class TesterMapeoHashCerrado {

	public static void main(String[] args) {
		Map<String, Integer> mapeo = new MapeoHashCerrado<String, Integer>();
		Entry<String, Integer> entrada1, entrada2, entrada3;
		Entry<String, Integer> entrada_aux;
		Iterable<String> lista_strings;
		Iterable<Integer> lista_enteros;
		Iterable<Entry<String, Integer>> lista_entradas;
		int index=0;
		Random rnd = new Random();
		String nombre_random = "";
		Integer num_random;
		
		//Prueba size()
		System.out.println("MapeoHC:: size(): "+mapeo.size());
		
		//Prueba isEmpty()
		System.out.println("MapeoHC:: isEmpty(): "+mapeo.isEmpty());
							
		//Prueba put()
		entrada1 = new Entrada<String, Integer>("Mateo", 22);
		entrada2 = new Entrada<String, Integer>("Catarina", 15);
		entrada3 = new Entrada<String, Integer>("Ambar", 32);
		
		System.out.println("---------------MAPEO:: PRUEBA PUT():-----------------");
		System.out.println("Se agrega 6 entradas. ENtre ellas:");
		System.out.println("entrada1: clave "+entrada1.getKey()+", valor: "+entrada1.getValue());
		System.out.println("entrada2: clave "+entrada2.getKey()+", valor: "+entrada2.getValue());
		System.out.println("entrada3: clave "+entrada3.getKey()+", valor: "+entrada3.getValue());
		try {
			mapeo.put("Marcelo", 15);
			mapeo.put("Estefania", 18);
			mapeo.put("Alonzo", 24);
			mapeo.put(entrada1.getKey(), entrada1.getValue());
			mapeo.put(entrada2.getKey(), entrada2.getValue());
			mapeo.put(entrada3.getKey(), entrada3.getValue());
		} catch (InvalidKeyException e) {			
			System.out.println("MapeoHC::"+e.getMessage());
		}
		
		//Prueba get()		
		System.out.println("----------MAPEO:: PRUEBA GET():------------------");
		try {
			System.out.println("Obteniendo valor de la entrada1: "+mapeo.get(entrada1.getKey()));
			System.out.println("Obteniendo valor de la entrada2: "+mapeo.get(entrada2.getKey()));
			System.out.println("Obteniendo valor de la entrada3: "+mapeo.get(entrada3.getKey()));
			System.out.println("Obteniendo valor de una entrada que no existe ('Kartuz'), deveria devolver NULL.");
			System.out.println("Se obtuvo: "+mapeo.get("Kartuz"));
		} catch (InvalidKeyException e) {			
			System.out.println("MapeoHC::"+e.getMessage());
		}
		
		//Prueba remove()
		System.out.println("----------MAPEO:: PRUEBA REMOVE():------------------");
		System.out.println("Removemos entrada 2 de clave: "+entrada2.getKey());
		try {
			System.out.println("Valor de la entrada removida: "+mapeo.remove(entrada2.getKey()));
			System.out.println("Intentamos usar GEt() con la clave de la entrada removida, deveria devolver NULL.");
			System.out.println("Valor de la clave removida: "+mapeo.get(entrada2.getKey()));
		} catch (InvalidKeyException e) {
			System.out.println("MapeoHC::"+e.getMessage());			
		}
		//Prueba Keys()
		System.out.println("-----------MAPEO:: PRUEBA KEYS():-----------------------");
		System.out.println("Recorremos la coleccion iterable que devuelve keys().");
		lista_strings = mapeo.keys();
		for (String clave : lista_strings) {
			index++;
			System.out.println("Clave N°"+index+": "+clave);
		}
		index=0;
		
		//Prueba Values()
		System.out.println("-----------MAPEO:: PRUEBA VALUES():-----------------------");
		System.out.println("Recorremos la coleccion iterable que devuelve values().");
		lista_enteros = mapeo.values();
		for (Integer valor : lista_enteros) {
			index++;
			System.out.println("Valor N°"+index+": "+valor);
		}
		index=0;
		
		//Prueba entries()
		System.out.println("-----------MAPEO:: PRUEBA ENTRIES():-----------------------");
		System.out.println("Recorremos la coleccion iterable que devuelve entries().");
		lista_entradas = mapeo.entries();
		for (Entry<String, Integer> entrada : lista_entradas) {
			index++;
			System.out.println("Entrada N°"+index+" de clave: "+entrada.getKey()+" y valor: "+entrada.getValue());
		}
		index=0;
		
		//Prueba resize()		
		System.out.println("-----------MAPEO:: PRUEBA RESIZE():-----------------------");
		System.out.println("Incertaremos 20 entradas nuevas hechas con Random() para triggerear el resize().");
		for (int i=0; i<20; i++)
		{
			nombre_random += ((char) (rnd.nextInt(20)+90));
			nombre_random += (char) (rnd.nextInt(15)+100);
			nombre_random += (char) (rnd.nextInt(50)+80);
			//System.out.println(nombre_random);
			num_random = rnd.nextInt(40)+10;
			try {
				mapeo.put(nombre_random, num_random);
			} catch (InvalidKeyException e) {			
				e.printStackTrace();
			}
			nombre_random = "";
		}
		System.out.println("Recorremos la lista iterable de entradas de entries():");
		lista_entradas = mapeo.entries();
		for (Entry<String, Integer> entrada : lista_entradas) {
			index++;
			System.out.println("Entrada N°"+index+" de clave: "+entrada.getKey()+" y valor: "+entrada.getValue());
		}
		index=0;
		//Prueba size()
		System.out.println("MapeoHC:: size(): "+mapeo.size());
		
		//Prueba get()		
		System.out.println("----------MAPEO:: PRUEBA GET():------------------");
		try {
			System.out.println("Obteniendo valor de la entrada1: "+mapeo.get(entrada1.getKey()));
			System.out.println("Obteniendo valor de la entrada2: "+mapeo.get(entrada2.getKey()));
			System.out.println("Obteniendo valor de la entrada3: "+mapeo.get(entrada3.getKey()));
			System.out.println("Obteniendo valor de una entrada que no existe ('Kartuz'), deveria devolver NULL.");
			System.out.println("Se obtuvo: "+mapeo.get("Kartuz"));
		} catch (InvalidKeyException e) {			
			System.out.println("MapeoHC::"+e.getMessage());
		}
	}
	
}
