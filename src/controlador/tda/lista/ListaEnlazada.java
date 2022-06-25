/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.lista;

import controlador.utiles.TipoOrdenacion;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 *
 * @author Usuario
 */
public class ListaEnlazada<E> implements Serializable {

    private NodoLista cabecera;
    private Class clazz;
    private Integer nro_elem;

    public static final Integer ASCENDENTE = 1;
    public static final Integer DESCENDENTE = 2;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean estaVacia() {
        return this.cabecera == null;
    }

    public boolean estaLlena() {
        return size() == nro_elem;
    }

    private void insertar(E dato) {
        NodoLista nodo = new NodoLista(dato, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(E dato) {
        insertar(dato, size());
    }

    /**
     * Insertar dato por pisicion
     *
     * @param dato El dato a insertr
     * @param pos La posicion a insertar
     */
    public void insertar(E dato, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato);
        } else {
            NodoLista iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            NodoLista tmp = new NodoLista(dato, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    /**
     * Agregar item a lista ascendente, queire decir que el primer elemento es
     * la cabecera
     *
     * @param dato El dato a agregar
     */
    public void insertarNodo(E dato) {
        if (size() > 0) {
            insertarFinal(dato);
        } else {
            insertar(dato);
        }
    }

    /**
     * Retorno el tama;o de la lista return numero de elementos
     */
    public int size() {
        int cont = 0;
        NodoLista tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }

    /**
     * Permite extraer el primer dato de la lista
     *
     * @return el dato
     */
    public E extraer() {
        E dato = null;
        if (!estaVacia()) {
            dato = (E) cabecera.getDato();
            cabecera = cabecera.getNodoSiguiente();
        }
        return dato;
    }

    /**
     *
     * @Permite consulta dato por pisicion
     * @return dato encontrado en la posicion
     */
    public E obtenerDato(int pos) {
        E dato = null;
        if (!estaVacia() && (pos >= 0 && pos <= size() - 1)) {
            NodoLista tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getNodoSiguiente();
                if (tmp == null) {
                    break;
                }
            }
            if (tmp != null) {
                dato = (E) tmp.getDato();
            }
        }
        return dato;
    }

    public void imprimir() {
        NodoLista tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println(tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public void eliminarDato(E dato) {
        NodoLista tmp = cabecera;
        NodoLista anterior = null;
        while (!estaVacia() && tmp != null) {
            if (tmp == dato) {
                boolean x = tmp == dato;
                if (tmp == cabecera) {
                    cabecera = cabecera.getNodoSiguiente();
                } else {
                    anterior.setNodoSiguiente(tmp.getNodoSiguiente());
                }
            }

            anterior = tmp;
            tmp = tmp.getNodoSiguiente();
        }
    }

    public boolean modificarDato(E dato, int pos) {
        if (!estaVacia() && (pos <= size() - 1) && pos >= 0) {
            NodoLista iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                iterador = iterador.getNodoSiguiente();
                if (iterador == null) {
                    break;
                }
            }
            if (iterador != null) {
                iterador.setDato(dato);
                return true;
            }
        }
        return false;
    }

    private Field getField(String nombre) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(nombre)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    private Object evaluarDato(E dato, String atributo) throws Exception {
        return getField(atributo).get(dato);
    }

    public ListaEnlazada<E> seleccion_clase(String atributo, Integer ordenacion) {
        try {
            int i, j, k = 0;
            E t = null;
            int n = size();
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = obtenerDato(i);

                for (j = i + 1; j < n; j++) {
                    boolean band = false;
                    Object datoT = evaluarDato(t, atributo);
                    Object datoJ = evaluarDato(obtenerDato(j), atributo);
                    if (datoT instanceof Number) {
                        Number aux = (Number) datoT;
                        Number numero = (Number) datoJ;
//                        band = (ordenacion.intValue() == ListaEnlazada.ASCENDENTE.intValue())
                        band = (ordenacion.intValue() == ListaEnlazada.ASCENDENTE.intValue())
                                ? numero.doubleValue() < aux.doubleValue()
                                : numero.doubleValue() > aux.doubleValue();
                    } else {
                        band = (ordenacion.intValue() == ListaEnlazada.ASCENDENTE.intValue())
                                ? datoT.toString().compareTo(datoJ.toString()) > 0
                                : datoT.toString().compareTo(datoJ.toString()) < 0;
                    }
                    if (band) {
                        t = obtenerDato(j);
                        k = j;
                    }
                }
                modificarDato(obtenerDato(i), k);
                modificarDato(t, i);
            }
        } catch (Exception e) {
            System.out.println("Error en ordenacion" + e);
        }
        return this;
    }

    public ListaEnlazada<E> QuisortClase(String atributo, int primero, int ultimo, TipoOrdenacion tipoOrdenacion) {
        try {
            int i, j, central;
            Object pivote;
            central = (primero + ultimo) / 2;
            pivote = obtenerDato(central);
            i = primero;
            j = ultimo;
            if (pivote instanceof Number) {
                do {
                    if (tipoOrdenacion.equals(tipoOrdenacion.ASCENDENTE)) {
                        while (((Number) evaluarDato(obtenerDato(i), atributo)).doubleValue() < ((Number) evaluarDato(obtenerDato(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) evaluarDato(obtenerDato(j), atributo)).doubleValue() > ((Number) evaluarDato(obtenerDato(central), atributo)).doubleValue()) {
                            j--;
                        }
                    } else {
                        while (((Number) evaluarDato(obtenerDato(i), atributo)).doubleValue() > ((Number) evaluarDato(obtenerDato(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) evaluarDato(obtenerDato(j), atributo)).doubleValue() < ((Number) evaluarDato(obtenerDato(central), atributo)).doubleValue()) {
                            j--;
                        }
                    }

                    if (i <= j) {
                        E auxiliar = obtenerDato(i);
                        modificarDato(obtenerDato(j), i);
                        modificarDato(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            } else {
                do {
                    if (tipoOrdenacion.equals(tipoOrdenacion.ASCENDENTE)) {
                        while (evaluarDato(obtenerDato(central), atributo).toString().compareTo(evaluarDato(obtenerDato(i), atributo).toString()) > 0) {
                            i++;
                        }
                        while (evaluarDato(obtenerDato(j), atributo).toString().compareTo(evaluarDato(obtenerDato(central), atributo).toString()) > 0) {
                            j--;
                        }
                    } else {
                        while (evaluarDato(obtenerDato(central), atributo).toString().compareTo(evaluarDato(obtenerDato(i), atributo).toString()) < 0) {
                            i++;
                        }
                        while (evaluarDato(obtenerDato(j), atributo).toString().compareTo(evaluarDato(obtenerDato(central), atributo).toString()) < 0) {
                            j--;
                        }
                    }
                    if (i <= j) {
                        E auxiliar = obtenerDato(i);
                        modificarDato(obtenerDato(j), i);
                        modificarDato(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            }
            if (primero < j) {
                QuisortClase(atributo, primero, j, tipoOrdenacion);
            }
            if (i < ultimo) {
                QuisortClase(atributo, i, ultimo, tipoOrdenacion);
            }
        } catch (Exception e) {
            System.out.println("Error quiscksort" + e);
        }
        return this;
    }

    public ListaEnlazada<E> ShellClase(TipoOrdenacion tipoOrdenacion, String atributo) {
        try {
            int intervalo, i, j, k;
            int n = size();
            intervalo = n / 2;
            while (intervalo > 0) {
                for (i = intervalo; i < n; i++) {
                    j = i - intervalo;
                    if (obtenerDato(intervalo) instanceof Number) {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (tipoOrdenacion.equals(tipoOrdenacion.ASCENDENTE)) {
                                if (((Number) evaluarDato(obtenerDato(j), atributo)).intValue() <= ((Number) evaluarDato(obtenerDato(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    E aux = obtenerDato(j);
                                    modificarDato(obtenerDato(j + 1), j);
                                    modificarDato(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (((Number) evaluarDato(obtenerDato(j), atributo)).intValue() >= ((Number) evaluarDato(obtenerDato(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    E aux = obtenerDato(j);
                                    modificarDato(obtenerDato(j + 1), j);
                                    modificarDato(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    } else {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (tipoOrdenacion.equals(tipoOrdenacion.ASCENDENTE)) {
                                if (evaluarDato(obtenerDato(k), atributo).toString().compareTo(evaluarDato(obtenerDato(j), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    E aux = obtenerDato(j);
                                    modificarDato(obtenerDato(j + 1), j);
                                    modificarDato(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (evaluarDato(obtenerDato(j), atributo).toString().compareTo(evaluarDato(obtenerDato(k), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    E aux = obtenerDato(j);
                                    modificarDato(obtenerDato(j + 1), j);
                                    modificarDato(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    }
                }
                intervalo = intervalo / 2;
            }
        } catch (Exception e) {
            System.out.println("Error en Shell: " + e);
        }
        return this;
    }

    public ListaEnlazada<E> BusquedaBinariaClase(Object elemento, String atributo) {
        try {
            ListaEnlazada auxiliar = new ListaEnlazada();
            int centro, primero, ultimo;
            primero = 0;
            ultimo = size() - 1;
            while (primero <= ultimo) {
                if (obtenerDato(0) instanceof Number) {
                    centro = (primero + ultimo) / 2;
                    if (((Number) evaluarDato((E) elemento, atributo)).intValue() == ((Number) evaluarDato(obtenerDato(centro), atributo)).intValue()) {
                        E po = obtenerDato(centro);
                        auxiliar.insertarNodo(po);
                        return auxiliar;
                    } else if (((Number) evaluarDato((E) elemento, atributo)).intValue() < ((Number) evaluarDato(obtenerDato(centro), atributo)).intValue()) {
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                } else {
                    centro = (primero + ultimo) / 2;
                    if (evaluarDato(obtenerDato(centro), atributo).toString().toLowerCase().contains(elemento.toString().toLowerCase())) {
                        auxiliar.insertarNodo(obtenerDato(centro));
                        return auxiliar;
                    } else if (evaluarDato(obtenerDato(centro), atributo).toString().toLowerCase().compareTo(elemento.toString().toLowerCase()) > 0) {
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Hay un error en busqueda: " + e);
        }
        return null;
    }
}
