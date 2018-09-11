/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class ParadasRutas implements Serializable {

    private String tracod;
    private String Parcod;
    private String parNomb;
    private String nombreRuta;
    private ProducidoDiario objecProducido;

    public ParadasRutas() {

    }

    public String getTracod() {
        return tracod;
    }

    public void setTracod(String tracod) {
        this.tracod = tracod;
    }

    public String getParcod() {
        return Parcod;
    }

    public void setParcod(String Parcod) {
        this.Parcod = Parcod;
    }

    public String getParNomb() {
        return parNomb;
    }

    public void setParNomb(String parNomb) {
        this.parNomb = parNomb;
    }

    public ProducidoDiario getObjecProducido() {
        if (objecProducido == null) {
            objecProducido = new ProducidoDiario();
        }
        return objecProducido;
    }

    public void setObjecProducido(ProducidoDiario objecProducido) {
        this.objecProducido = objecProducido;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

}
