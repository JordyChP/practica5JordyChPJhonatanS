/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica6busqueda;

import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i = 0; i < 1; i++) {
            n = (char) (rnd.nextDouble() * 26.0 + 65.0);
            cadena += n;
        }
        System.out.println(cadena);
    }
}
// TODO code application logic here

    

