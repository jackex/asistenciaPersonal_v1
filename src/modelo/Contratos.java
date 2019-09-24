/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author jack
 */
public class Contratos {
    
public String NOMBRE;
public String FECHA_INICIAL;
public String FECHA_FINAL;
public String DESCRIPCION;


public void setNombreContrato(String contrato){
    this.NOMBRE = contrato;
}

public void setFechaInicial(String fechainicial){
    this.FECHA_INICIAL = fechainicial;
}

public void setFechaFinal(String fechafinal){
    this.FECHA_FINAL = fechafinal;
}

public void setDescripcion(String descripcion){
    this.DESCRIPCION = descripcion;
}


public String getNombreContrato(){
    return this.NOMBRE;
}


public String getFechaInicial(){
    return this.FECHA_INICIAL;
}


public String getFechaFinal(){
    return this.FECHA_FINAL;
}

public String  getDescripcion(){
    return this.DESCRIPCION;
}
}