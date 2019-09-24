/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jack
 */
public class DBConnection {

private String USERNAME = "administrador";
private String PASSWORD = "administrador";
private String PATH = "jdbc:mysql://localhost/asistenciapersonal";
private String DRIVER ="com.mysql.jdbc.Driver";
Connection connect = null;
    
    
    public DBConnection(){
        
        try{
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(PATH, USERNAME,PASSWORD);
            if(connect  != null){}        
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error en la conexión", JOptionPane.ERROR_MESSAGE);
        }           
    }
    
    public Connection getConnection(){
        return this.connect;
    }
    
    
    public void disconnect(){
        this.connect = null;
    }
}
