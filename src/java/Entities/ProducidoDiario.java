/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ProducidoDiario implements Serializable {

    private int idProd;
    private Date hora;
    private Date fecha;
    private String codBus;
    private String servicio;
    private String motivoTurnoPerdido;
    private int cantPasajeros;
    private int producido;
    private int viajeId;
    private Date fechaCreacion;
    private Date fechaMod;
    private String usuariocrea;
    private String usuariomod;
    private String codPostal;
    private String fechaDespacho;
    static SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
    private String registrado;

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }

    public ProducidoDiario() {
    }

    public Date getHora() {
//        String[] datos = fechaDespacho.split(" ");
//        hora = datos[1].substring(0, datos[1].length() - 4);
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodBus() {
        return codBus;
    }

    public void setCodBus(String codBus) {
        this.codBus = codBus;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getMotivoTurnoPerdido() {
        return motivoTurnoPerdido;
    }

    public void setMotivoTurnoPerdido(String motivoTurnoPerdido) {
        this.motivoTurnoPerdido = motivoTurnoPerdido;
    }

    public int getCantPasajeros() {
        return cantPasajeros;
    }

    public void setCantPasajeros(int cantPasajeros) {
        this.cantPasajeros = cantPasajeros;
    }

    public int getProducido() {
        return producido;
    }

    public void setProducido(int producido) {
        this.producido = producido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getUsuariocrea() {
        return usuariocrea;
    }

    public void setUsuariocrea(String usuariocrea) {
        this.usuariocrea = usuariocrea;
    }

    public String getUsuariomod() {
        return usuariomod;
    }

    public void setUsuariomod(String usuariomod) {
        this.usuariomod = usuariomod;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    @Override
    public String toString() {
        return "obj{" + "hora=" + hora + ",fecha=" + fecha + ",codBus=" + codBus +", motivoTurnoPerdido=" + motivoTurnoPerdido + ",cantPasajeros=" + cantPasajeros + ",producido=" + producido + ",fechaCreacion=" + fechaCreacion + ",codPostal=" + codPostal + ",fechaDespacho=" + fechaDespacho + ", registrado=" + registrado + '}';
    }

   

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(String fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public int getViajeId() {
        return viajeId;
    }

    public void setViajeId(int viajeId) {
        this.viajeId = viajeId;
    }

}
