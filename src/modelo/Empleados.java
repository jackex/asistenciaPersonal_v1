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
public class Empleados {
    
    public String TIPO_DOCUMENTO;
    public String DOCUMENTO;
    public String PRIMER_NOMBRE;
    public String SEGUNDO_NOMBRE;
    public String PRIMER_APELLIDO;
    public String SEGUNDO_APELLIDO;
    public String DIRECCION;
    public String TELEFONO;
    public String SEXO;
    public String CORREO;
    public String CONTRASENA;
    public String CARGO;
    public String ID;

    public Empleados() {
    }

    public void setID(String ID) {
        this.ID = ID;
    }    

    public void setTIPO_DOCUMENTO(String TIPO_DOCUMENTO) {
        this.TIPO_DOCUMENTO = TIPO_DOCUMENTO;
    }  

    public void setDOCUMENTO(String DOCUMENTO) {
        this.DOCUMENTO = DOCUMENTO;
    }

    public void setPRIMER_NOMBRE(String PRIMER_NOMBRE) {
        this.PRIMER_NOMBRE = PRIMER_NOMBRE;
    }

    public void setSEGUNDO_NOMBRE(String SEGUNDO_NOMBRE) {
        this.SEGUNDO_NOMBRE = SEGUNDO_NOMBRE;
    }

    public void setPRIMER_APELLIDO(String PRIMER_APELLIDO) {
        this.PRIMER_APELLIDO = PRIMER_APELLIDO;
    }

    public void setSEGUNDO_APELLIDO(String SEGUNDO_APELLIDO) {
        this.SEGUNDO_APELLIDO = SEGUNDO_APELLIDO;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }   

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public void setSEXO(String SEXO) {
        this.SEXO = SEXO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public void setCONTRASENA(String CONTRASENA) {
        this.CONTRASENA = CONTRASENA;
    }

    public void setCARGO(String CARGO) {
        this.CARGO = CARGO;
    }

    public String getID() {
        return ID;
    }
    
    public String getTIPO_DOCUMENTO() {
        return TIPO_DOCUMENTO;
    }      

    public String getDOCUMENTO() {
        return DOCUMENTO;
    }

    public String getPRIMER_NOMBRE() {
        return PRIMER_NOMBRE;
    }

    public String getSEGUNDO_NOMBRE() {
        return SEGUNDO_NOMBRE;
    }

    public String getPRIMER_APELLIDO() {
        return PRIMER_APELLIDO;
    }

    public String getSEGUNDO_APELLIDO() {
        return SEGUNDO_APELLIDO;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public String getSEXO() {
        return SEXO;
    }

    public String getCORREO() {
        return CORREO;
    }

    public String getCONTRASENA() {
        return CONTRASENA;
    }

    public String getCARGO() {
        return CARGO;
    }  
    
}
