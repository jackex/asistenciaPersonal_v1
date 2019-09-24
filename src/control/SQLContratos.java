/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import vista.CrearContratos;

/**
 *
 * @author jack
 */
public class SQLContratos {

    Boolean flag = false;
    String id;
    DBConnection conexion = new DBConnection();
    CrearContratos CONTRATO;
    
    public SQLContratos(CrearContratos contrato){
        this.CONTRATO = contrato;
    }
    
    public SQLContratos(){}
    
    
    
    public boolean filtroContratos(String nombre){
        String query = "SELECT ID FROM CONTRATOS WHERE NOMBRE =?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, nombre);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.id = RS.getString("ID");
                this.flag = true;
            }
            RS.close();
            PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        return this.flag;
    }
        
    public Boolean insertarContrato(String contrato, String fecha_inicial, String fecha_final, String descripcion){        
        Boolean flag = false;
        String query = "INSERT INTO CONTRATOS VALUES (?,?,?,?,?,?)";
        PreparedStatement PS;
        try{
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,null);
           PS.setString(2, contrato);
           PS.setString(3, fecha_inicial);
           PS.setString(4, fecha_final);
           PS.setString(5, descripcion);
           PS.setBoolean(6,true);
           flag = PS.execute();
           if(flag == false){
               flag = true;
           }
           PS.close();                      
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }    
    
    
    public void obtenerContratos(){    
        PreparedStatement PS;
        String query = "SELECT NOMBRE, FECHA_INICIO,FECHA_FIN_CONTRATO,DESCRIPCION_CONTRATO FROM CONTRATOS";
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();            
            while(RS.next()){
            this.CONTRATO.TABLACREARCONTRATOS.addRow(new Object []{RS.getString("NOMBRE"), RS.getString("FECHA_INICIO"),
                RS.getString("FECHA_FIN_CONTRATO"),RS.getString("DESCRIPCION_CONTRATO")});
            }
            RS.close();
            PS.close();            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public boolean actualizarContratos(String nombre_anterior, String nombrecontrato,String fecha_inicio,String fecha_terminacion,String descripcion){
        int tmp = 0;
        boolean status = false;
        if(this.filtroContratos(nombre_anterior)){
            PreparedStatement PS;
            String query = "UPDATE CONTRATOS SET NOMBRE = ?,FECHA_INICIO=?, FECHA_FIN_CONTRATO=?,DESCRIPCION_CONTRATO=? WHERE ID=?";
            try{
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, nombrecontrato);
                PS.setString(2, fecha_inicio);
                PS.setString(3, fecha_terminacion);
                PS.setString(4, descripcion);
                PS.setString(5, this.id);
                tmp = PS.executeUpdate();
                if(tmp == 1){status = true;}                
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
        }else{
            JOptionPane.showMessageDialog(null,"El contrato no existe", "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        
        return status;
    }
       
}