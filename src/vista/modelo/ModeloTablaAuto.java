/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modelo;

import controlador.tda.lista.ListaEnlazada;
import modelo.Auto;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloTablaAuto extends AbstractTableModel{

    private ListaEnlazada<Auto> lista = new ListaEnlazada();

    public ListaEnlazada<Auto> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<Auto> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Auto es = lista.obtenerDato(i);
        switch(i1){
            case 0: return (i + 1);
            case 1: return es.getModelo();
            case 2: return es.getColor();
            case 3: return es.getPlaca();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "Nro";
            case 1: return "Modelo";
            case 2: return "Color";
            case 3: return "Placa";
            default: return null;
        }
    }
    
}
