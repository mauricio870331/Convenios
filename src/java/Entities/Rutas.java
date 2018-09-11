/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Rutas implements Serializable {

    private String nomRuta;
    private ArrayList<ParadasRutas> listaParadas;

    public Rutas() {

    }

    public String getNomRuta() {
        return nomRuta;
    }

    public void setNomRuta(String nomRuta) {
        this.nomRuta = nomRuta;
    }

    public ArrayList<ParadasRutas> getListaParadas() {
        return listaParadas;
    }

    public void setListaParadas(ArrayList<ParadasRutas> listaParadas) {
        this.listaParadas = listaParadas;
    }

}
