/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.DAO;

import modelo.Auto;

/**
 *
 * @author Usuario
 */
public class AutoDao extends AdaptadorDao<Auto> {
    private Auto estudiante;

    public AutoDao() {
        super(Auto.class);
    }

    public Auto getEstudiante() {
        if(estudiante==null)
            estudiante = new Auto();
        return estudiante;
    }

    public void setEstudiante(Auto estudiante) {
        this.estudiante = estudiante;
    }
    
    public boolean guardar(){
        return this.guardar(estudiante);
    }
}
