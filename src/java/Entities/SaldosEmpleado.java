/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class SaldosEmpleado implements Serializable {

    private String empresa;
    private String documento;
    private String nombre;
    private String fecha_final;
    private String ida_regreso;
    private int asignados;
    private int entregados;
    private String anulado;
    private String vigencia;
    private String entrega;
    private String seVeEnAgencia;

    public SaldosEmpleado() {
    }

    public int getAsignados() {
        return asignados;
    }

    public void setAsignados(int asignados) {
        this.asignados = asignados;
    }

    public int getEntregados() {
        return entregados;
    }

    public void setEntregados(int entregados) {
        this.entregados = entregados;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getIda_regreso() {
        return ida_regreso;
    }

    public void setIda_regreso(String ida_regreso) {
        this.ida_regreso = ida_regreso;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getSeVeEnAgencia() {
        return seVeEnAgencia;
    }

    public void setSeVeEnAgencia(String seVeEnAgencia) {
        this.seVeEnAgencia = seVeEnAgencia;
    }

}
