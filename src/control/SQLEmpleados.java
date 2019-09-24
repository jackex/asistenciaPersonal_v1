/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import vista.AdministrarSalidaPorSistema;
import vista.AgregarEmpleados;
import vista.ConsultarAsistencias;
import vista.EditarEmpleados;
import vista.IniciarSesion;
import vista.GenerarReportes;
import vista.AgregarTipoCargos;
import vista.LoginHuella;
import vista.RegistrarAsistencias;
import vista.VisualizarEmpleados;
/**
 *
 * @author jack
 */
public class SQLEmpleados {
    
    HuellaDigital HUELLA = new HuellaDigital();
    DBConnection conexion = new DBConnection();
    VisualizarEmpleados VISUALIZAREMPLEADOS;
    AgregarEmpleados EMPLEADOS;
    AgregarTipoCargos AGREGARCARGOS;
    EditarEmpleados EDITAREMPLEADOS;
    IniciarSesion NUEVASESION;
    ConsultarAsistencias ASISTENCIAS;
    RegistrarAsistencias REGISTRARASISTENCIAS;
    GenerarReportes REPORTES;
    AdministrarSalidaPorSistema SALIDASISTEMA;
    LoginHuella LOGINHUELLA;
    public String tituloReporte = "\t\t             FORMATO DE REPORTE PARA IMPRESION DEL NÚMERO DE HORAS PRESTADAS POR EMPLEADO\n" +
                             "\t\t\t\t\tINFORMACIÓN INDIVIDUAL\n\n";
    public String usuarioReporte = "";
    public String usuarioReporte2 = "";
    private String temp = this.tituloReporte;
    public String mensaje ="";
    public String mensaje2 ="";

    public SQLEmpleados() {}   

    public SQLEmpleados(ConsultarAsistencias asistencias){
        this.ASISTENCIAS = asistencias;
    }   

    public SQLEmpleados(AgregarEmpleados EMPLEADOS) {       
        this.EMPLEADOS = EMPLEADOS;
    }
    
    public SQLEmpleados(EditarEmpleados EDITAR){
        this.EDITAREMPLEADOS = EDITAR;
    }

    public SQLEmpleados(IniciarSesion sesion) {
        this.NUEVASESION = sesion;
    }   
    
    public SQLEmpleados(GenerarReportes reporte){
        this.REPORTES = reporte;
    }
    
    public SQLEmpleados(AgregarTipoCargos cargos){
    this.AGREGARCARGOS = cargos;
    }
    
    public SQLEmpleados(RegistrarAsistencias asistencias){
        this.REGISTRARASISTENCIAS = asistencias;
    }
    
    public SQLEmpleados(AdministrarSalidaPorSistema salidasistema){
        this.SALIDASISTEMA = salidasistema;
    }
    
    public SQLEmpleados(VisualizarEmpleados visualizarEmpleados){
        this.VISUALIZAREMPLEADOS = visualizarEmpleados;
    }
    
    public SQLEmpleados(LoginHuella loginhuella){
        this.LOGINHUELLA = loginhuella;
    }
    
    public Boolean ingresarEmpleados(String sexo,String tipodocumento,String documento,
    String primerNombre,String segundoNombre,String primerApellido,String segundoApellido
            ,String telefono,String direccion,String correo,String password,String cargo,
            HuellaDigital huella,File imagen,String municipio){
        Boolean flag = false;
        int valorSexo = this.IDSexo(sexo);
        int valorTipoDocumento = this.IDTipoDocumento(tipodocumento);
        int valorCargo = this.IDCargo(cargo);
        int valorMunicipio = this.obtenerIDMunicipio(municipio);
        String query = "INSERT INTO EMPLEADOS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement PS;
        try{
            File file = new File(imagen.getPath());
            FileInputStream IMAGENPERFIL = new FileInputStream(file);
            ByteArrayInputStream NUEVAHUELLA = new ByteArrayInputStream(huella.TEMPLATE.serialize());
            Integer TAMAÑOHUELLA =  huella.TEMPLATE.serialize().length;
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,null);
           PS.setString(2,documento);
           PS.setString(3, primerNombre);
           PS.setString(4, segundoNombre);
           PS.setString(5, primerApellido);
           PS.setString(6, segundoApellido);
           PS.setString(7, telefono);
           PS.setString(8, direccion);
           PS.setString(9, correo);
           PS.setString(10,password);
           PS.setBoolean(11,true);
           PS.setBinaryStream(12, NUEVAHUELLA,TAMAÑOHUELLA);
           PS.setBinaryStream(13, IMAGENPERFIL,file.length());
           PS.setInt(14,valorSexo);
           PS.setInt(15,valorTipoDocumento);
           PS.setInt(16, valorCargo);
           PS.setInt(17, 2);
           PS.setInt(18, valorMunicipio);
           flag = PS.execute();
           if(flag == false){
               flag = true;
           }
           PS.close();        
        }catch(SQLException | FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.getLocalizedMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }       
    
    public Boolean ActualizarEmpleados(String sexo,String tipodocumento,String documento,
    String primerNombre,String segundoNombre,String primerApellido,String segundoApellido
    ,String telefono,String direccion,String correo,String password,String cargo,String documentoAnterior,
    HuellaDigitalEditarEmpleados HDEditarEmpleado,File imagen){
        Boolean flag = false;
        int IDSexo = this.IDSexo(sexo);
        int IDTipoDocumento = this.IDTipoDocumento(tipodocumento);
        int IDCargo = this.IDCargo(cargo);
        String query = "UPDATE EMPLEADOS SET DOCUMENTO = ?, PRIMER_NOMBRE =?, SEGUNDO_NOMBRE =?, PRIMER_APELLIDO =?, SEGUNDO_APELLIDO =?, TELEFONO =?, DIRECCION =?, CORREO =?, PASSWORD =?, HUELLA =?, FOTO =?, FK_SEXO =?, FK_TIPO_DOCUMENTO =?, FK_CARGO =? WHERE EMPLEADOS.DOCUMENTO = ?";
        PreparedStatement PS;
        try{
          File file = new File(imagen.getPath());
          FileInputStream IMAGENPERFIL = new FileInputStream(file);
          ByteArrayInputStream NUEVAHUELLA = new ByteArrayInputStream(HDEditarEmpleado.TEMPLATE.serialize());
          Integer TAMAÑOHUELLA =  HDEditarEmpleado.TEMPLATE.serialize().length;
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,documento);
           PS.setString(2,primerNombre);
           PS.setString(3,segundoNombre);
           PS.setString(4,primerApellido);
           PS.setString(5,segundoApellido);
           PS.setString(6,telefono);
           PS.setString(7,direccion);
           PS.setString(8,correo);
           PS.setString(9,password);
           PS.setBinaryStream(10, NUEVAHUELLA, TAMAÑOHUELLA);
           PS.setBinaryStream(11,IMAGENPERFIL,file.length());
           PS.setInt(12,IDSexo);
           PS.setInt(13,IDTipoDocumento);
           PS.setInt(14,IDCargo);
           PS.setString(15,documentoAnterior);
           flag = PS.execute();
           if(flag == false){
               flag = true;
           }
           PS.close();                      
        }catch(SQLException | FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.getLocalizedMessage(), "Error al actualizar", JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }
    
    public Boolean ActualizarEmpleadosSinImagen(String sexo,String tipodocumento,String documento,
    String primerNombre,String segundoNombre,String primerApellido,String segundoApellido
    ,String telefono,String direccion,String correo,String password,String cargo,String documentoAnterior,
    HuellaDigitalEditarEmpleados HDEditarEmpleado){
        Boolean flag = false;
        int IDSexo = this.IDSexo(sexo);
        int IDTipoDocumento = this.IDTipoDocumento(tipodocumento);
        int IDCargo = this.IDCargo(cargo);
        String query = "UPDATE EMPLEADOS SET DOCUMENTO = ?, PRIMER_NOMBRE =?, SEGUNDO_NOMBRE =?, PRIMER_APELLIDO =?, SEGUNDO_APELLIDO =?, TELEFONO =?, DIRECCION =?, CORREO =?, PASSWORD =?, HUELLA =?, FK_SEXO =?, FK_TIPO_DOCUMENTO =?, FK_CARGO =? WHERE EMPLEADOS.DOCUMENTO = ?";
        PreparedStatement PS;
        try{
          ByteArrayInputStream NUEVAHUELLA = new ByteArrayInputStream(HDEditarEmpleado.TEMPLATE.serialize());
          Integer TAMAÑOHUELLA =  HDEditarEmpleado.TEMPLATE.serialize().length;
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,documento);
           PS.setString(2,primerNombre);
           PS.setString(3,segundoNombre);
           PS.setString(4,primerApellido);
           PS.setString(5,segundoApellido);
           PS.setString(6,telefono);
           PS.setString(7,direccion);
           PS.setString(8,correo);
           PS.setString(9,password);
           PS.setBinaryStream(10, NUEVAHUELLA, TAMAÑOHUELLA);
           PS.setInt(11,IDSexo);
           PS.setInt(12,IDTipoDocumento);
           PS.setInt(13,IDCargo);
           PS.setString(14,documentoAnterior);
           flag = PS.execute();
           if(flag == false){
               flag = true;
           }
           PS.close();                      
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getLocalizedMessage(), "Error al actualizar", JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }
    
    
    
    public Boolean ingresarEmpleadosConContratos(String sexo,String tipodocuemnto,String documento,
    String primerNombre,String segundoNombre,String primerApellido,String segundoApellido
            ,String telefono,String direccion,String correo,String password,String contrato, String cargo){
        int DOCUMENTO_EMPLEADO;
        int CONTRATO_VIGENTE;
        Boolean flag = false;
        int valorSexo = this.IDSexo(sexo);
        int valorTipoDocumento = this.IDTipoDocumento(tipodocuemnto);
        int valorCargo = this.IDCargo(cargo);
        String query = "INSERT INTO EMPLEADOS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement PS;
        try{
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,null);
           PS.setString(2,documento);
           PS.setString(3, primerNombre);
           PS.setString(4, segundoNombre);
           PS.setString(5, primerApellido);
           PS.setString(6, segundoApellido);
           PS.setString(7, telefono);
           PS.setString(8, direccion);
           PS.setString(9, correo);
           PS.setString(10,password);
           PS.setBoolean(11,true);
           PS.setInt(12,valorSexo);
           PS.setInt(13,valorTipoDocumento);  
           PS.setInt(14, valorCargo);
           PS.setInt(15, 2);
           flag = PS.execute();           
           if(flag == false){
               DOCUMENTO_EMPLEADO = Integer.parseInt(this.getIDDocumentoEmpleado(documento));
               CONTRATO_VIGENTE = Integer.parseInt(this.obtenerIDContrato(contrato));
               if(this.insertarContratoActual(CONTRATO_VIGENTE, DOCUMENTO_EMPLEADO)){
                   flag = true;
               }               
           }
           PS.close();                      
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }
    
    public boolean insertarContratoActual(int id_datos_laborales, int empleado){        
     boolean tmp = false;
     String query = "INSERT INTO CONTRATO_ACTUAL VALUES (?,?,?)";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, null);
         PS.setInt(2,id_datos_laborales);
         PS.setInt(3,empleado);
         tmp = PS.execute();         
         if(tmp == false){
             tmp = true;
         }
         PS.close();         
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
     }    
     return tmp;
    }        

    public boolean validarEmpleado(String documento){
     boolean tmp = false;
     String query = "SELECT ID FROM EMPLEADOS WHERE DOCUMENTO =?";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, documento);
         ResultSet RS = PS.executeQuery();
         while(RS.next()){
            if(RS.next() == false){
                 tmp = true;
            }
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
     }    
     return tmp;
    }        
    
    public void obtenerFotoEditarPerfil(String documento){
        String query = "SELECT FOTO FROM EMPLEADOS WHERE DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                ImageIcon imagen = new ImageIcon();
                if(RS.getBlob("FOTO") == null){
                   this.EDITAREMPLEADOS.LblfotoPerfil.setIcon(null); 
                }else{
                Blob blob = RS.getBlob("FOTO");
                byte Buffer[] = blob.getBytes(1, (int) blob.length());
                BufferedImage BI = null;
                try {
                    BI = ImageIO.read(new ByteArrayInputStream(Buffer));
                } catch (IOException e) {
                    Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
                }                
                imagen.setImage(BI);
                this.EDITAREMPLEADOS.LblfotoPerfil.setIcon(null); 
                this.EDITAREMPLEADOS.LblfotoPerfil.setIcon(new ImageIcon(BI.getScaledInstance(
                this.EDITAREMPLEADOS.LblfotoPerfil.getWidth(),
                this.EDITAREMPLEADOS.LblfotoPerfil.getHeight(),Image.SCALE_DEFAULT)));
            }
            }
            PS.close();
            RS.close();
        } catch (Exception e) {
            Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
        }
    }
    
        public void obtenerFotoRegistrarAsistencias(String documento){
        String query = "SELECT FOTO FROM EMPLEADOS WHERE DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                ImageIcon imagen = new ImageIcon();
                if(RS.getBlob("FOTO") == null){
                   this.REGISTRARASISTENCIAS.LblImagenPerfil.setIcon(null); 
                }else{
                Blob blob = RS.getBlob("FOTO");
                byte Buffer[] = blob.getBytes(1, (int) blob.length());
                BufferedImage BI = null;
                try {
                    BI = ImageIO.read(new ByteArrayInputStream(Buffer));
                } catch (IOException e) {
                    Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
                }                
                imagen.setImage(BI);
                this.REGISTRARASISTENCIAS.LblImagenPerfil.setIcon(null); 
                this.REGISTRARASISTENCIAS.LblImagenPerfil.setIcon(new ImageIcon(BI.getScaledInstance(
                this.REGISTRARASISTENCIAS.LblImagenPerfil.getWidth(),
                this.REGISTRARASISTENCIAS.LblImagenPerfil.getHeight(),Image.SCALE_DEFAULT)));
            }
            }
            PS.close();
            RS.close();
        } catch (Exception e) {
            Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
        }
    }
    
    
    public String getIDDocumentoEmpleado(String documento){
        String tmp = "";
     String query = "SELECT ID FROM EMPLEADOS WHERE DOCUMENTO =?";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, documento);
         ResultSet RS = PS.executeQuery();
         if(RS.next()){
             tmp = RS.getString("ID");
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
     }    
     return tmp;
    }        
    
    public int IDSexo(String sexo){
     int tmp = 0;
     String query = "SELECT ID FROM SEXO WHERE SEXO =?";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, sexo);
         ResultSet RS = PS.executeQuery();
         while(RS.next()){
             tmp = RS.getInt("ID");
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje SEXO", JOptionPane.ERROR_MESSAGE);
     }    
     return tmp;
    }    
    
    public int IDCargo(String cargo){
     int tmp = 0;
     String query = "SELECT ID FROM CARGOS WHERE CARGO = ?";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, cargo);
         ResultSet RS = PS.executeQuery();
         while(RS.next()){
             tmp = RS.getInt("ID");
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje ID CARGO", JOptionPane.ERROR_MESSAGE);
     }    
     return tmp;
    }      
    
    
    public int IDTipoDocumento(String tipodocumento){
     int tmp = 0;
     String query = "SELECT ID FROM TIPO_DOCUMENTO WHERE DOCUMENTO =?";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, tipodocumento);
         ResultSet RS = PS.executeQuery();
         while(RS.next()){
             tmp = RS.getInt("ID");
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje TIPO DOC", JOptionPane.ERROR_MESSAGE);
     }          
     return tmp;
    }    
    
    public void obtenerTipoDocumento(){
        String query = "SELECT DOCUMENTO FROM TIPO_DOCUMENTO ORDER BY DOCUMENTO ASC";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EMPLEADOS.tipoDocumento.addItem(RS.getString("DOCUMENTO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        public void obtenerTipoDocumentoListaEmpleados(){
        String query = "SELECT DOCUMENTO FROM TIPO_DOCUMENTO ORDER BY DOCUMENTO ASC";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.VISUALIZAREMPLEADOS.tipoDocumento.addItem(RS.getString("DOCUMENTO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void obtenerTipoDocumentoActualizar(){
        String query = "SELECT DOCUMENTO FROM TIPO_DOCUMENTO ORDER BY DOCUMENTO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EDITAREMPLEADOS.tipoDocumento.addItem(RS.getString("DOCUMENTO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void obtenerTipoDocumentoParaReporte(){
        String query = "SELECT DOCUMENTO FROM TIPO_DOCUMENTO ORDER BY DOCUMENTO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.REPORTES.tipo_documento.addItem(RS.getString("DOCUMENTO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void obtenerAsistencias(String tipo_documento, String documento, String fechaInicial, String fechaFinal){              
        String consulta = "SELECT DISTINCT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, CARGOS.CARGO,MUNICIPIOS.NOMBRE FROM EMPLEADOS,CARGOS,HORA_INGRESO,TIPO_DOCUMENTO,MUNICIPIOS WHERE EMPLEADOS.FK_CARGO = CARGOS.ID AND EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.FK_TIPO_DOCUMENTO = TIPO_DOCUMENTO.ID AND EMPLEADOS.FK_MUNICIPIO = MUNICIPIOS.ID AND EMPLEADOS.DOCUMENTO =? AND TIPO_DOCUMENTO.DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(consulta);
            PS.setString(1,documento);
            PS.setString(2, tipo_documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.Lbldocumento.setText(RS.getString("DOCUMENTO")); 
                this.ASISTENCIAS.Lblnombre.setText(RS.getString("PRIMER_NOMBRE")+ " "+RS.getString("SEGUNDO_NOMBRE")+ " "
                +RS.getString("PRIMER_APELLIDO")+ " "+RS.getString("SEGUNDO_APELLIDO"));
                this.ASISTENCIAS.Lbldireccion.setText(RS.getString("DIRECCION"));
                this.ASISTENCIAS.Lblcargo.setText(RS.getString("CARGOS.CARGO")); 
                this.ASISTENCIAS.Lblmunicipio.setText(RS.getString("MUNICIPIOS.NOMBRE"));
            }    
        //SELECT hora_ingreso, hora_salida, (MINUTE(time(hora_salida)) - MINUTE(time(hora_ingreso)))/60 as "horas" from hora_ingreso where minute(hora_salida) > 0   
            String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_PM, HORAS_TRABAJADAS_PM,COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            PS.setString(2, tipo_documento);
            RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.TABLA.addRow(new Object[]{RS.getString("HORA_INGRESO_AM"),
                    RS.getString("HORA_SALIDA_PM"), RS.getString("HORAS_TRABAJADAS_PM"), RS.getString("COMENTARIOS_PM")});
            }
          PS.close();
          RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Error obtenerAsistencias", JOptionPane.ERROR_MESSAGE);
        }
    }
    
public void obtenerAsistenciasAMDelSistema(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
        this.ASISTENCIAS.Lbldocumento.setText("");  
        this.ASISTENCIAS.Lblnombre.setText("");
        this.ASISTENCIAS.Lbldireccion.setText("");
        this.ASISTENCIAS.Lblcargo.setText("");      
        String consulta = "SELECT DISTINCT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, CARGOS.CARGO FROM EMPLEADOS,CARGOS,HORA_INGRESO,TIPO_DOCUMENTO WHERE EMPLEADOS.FK_CARGO = CARGOS.ID AND EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.FK_TIPO_DOCUMENTO = TIPO_DOCUMENTO.ID AND EMPLEADOS.DOCUMENTO =? AND TIPO_DOCUMENTO.DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(consulta);
            PS.setString(1,documento);
            PS.setString(2, tipo_documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.Lbldocumento.setText(RS.getString("DOCUMENTO"));
                this.ASISTENCIAS.Lblnombre.setText(RS.getString("PRIMER_NOMBRE")+ " "+RS.getString("SEGUNDO_NOMBRE")+ " "
                +RS.getString("PRIMER_APELLIDO")+ " "+RS.getString("SEGUNDO_APELLIDO"));
                this.ASISTENCIAS.Lbldireccion.setText(RS.getString("DIRECCION"));
                this.ASISTENCIAS.Lblcargo.setText(RS.getString("CARGOS.CARGO"));                
            }    
        //SELECT hora_ingreso, hora_salida, (MINUTE(time(hora_salida)) - MINUTE(time(hora_ingreso)))/60 as "horas" from hora_ingreso where minute(hora_salida) > 0   
            String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_AM, HORAS_TRABAJADAS_AM, HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\" from hora_ingreso,empleados,tipo_documento where REGISTRO_INGRESO_AM = 1 and empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            PS.setString(2, tipo_documento);
            RS = PS.executeQuery();
            while(RS.next()){
                System.out.println("hola");
                this.ASISTENCIAS.TABLA.addRow(new Object[]{RS.getString("HORA_INGRESO_AM"),
                    RS.getString("HORA_SALIDA_AM"),RS.getString("HORAS_TRABAJADAS_AM"),RS.getString("HORA_INGRESO_PM"),
                    RS.getString("HORA_SALIDA_PM"),RS.getString("HORAS_TRABAJADAS_PM"), RS.getString("HORAS")});
            }
          PS.close();
          RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }    

public void obtenerAsistenciasPMDelSistema(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
        this.ASISTENCIAS.Lbldocumento.setText("");
        this.ASISTENCIAS.Lblnombre.setText("");
        this.ASISTENCIAS.Lbldireccion.setText("");
        this.ASISTENCIAS.Lblcargo.setText("");      
        String consulta = "SELECT DISTINCT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, CARGOS.CARGO FROM EMPLEADOS,CARGOS,HORA_INGRESO,TIPO_DOCUMENTO WHERE EMPLEADOS.FK_CARGO = CARGOS.ID AND EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.FK_TIPO_DOCUMENTO = TIPO_DOCUMENTO.ID AND EMPLEADOS.DOCUMENTO =? AND TIPO_DOCUMENTO.DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(consulta);
            PS.setString(1,documento);
            PS.setString(2, tipo_documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.Lbldocumento.setText(RS.getString("DOCUMENTO"));
                this.ASISTENCIAS.Lblnombre.setText(RS.getString("PRIMER_NOMBRE")+ " "+RS.getString("SEGUNDO_NOMBRE")+ " "
                +RS.getString("PRIMER_APELLIDO")+ " "+RS.getString("SEGUNDO_APELLIDO"));
                this.ASISTENCIAS.Lbldireccion.setText(RS.getString("DIRECCION"));
                this.ASISTENCIAS.Lblcargo.setText(RS.getString("CARGOS.CARGO"));                
            }    
        //SELECT hora_ingreso, hora_salida, (MINUTE(time(hora_salida)) - MINUTE(time(hora_ingreso)))/60 as "horas" from hora_ingreso where minute(hora_salida) > 0   
            String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_AM, HORAS_TRABAJADAS_AM, HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\" from hora_ingreso,empleados,tipo_documento where REGISTRO_INGRESO_PM = 1 and empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            PS.setString(2, tipo_documento);
            RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.TABLA.addRow(new Object[]{RS.getString("HORA_INGRESO_AM"),
                    RS.getString("HORA_SALIDA_AM"),RS.getString("HORAS_TRABAJADAS_AM"),RS.getString("HORA_INGRESO_PM"),
                    RS.getString("HORA_SALIDA_PM"),RS.getString("HORAS_TRABAJADAS_PM"), RS.getString("HORAS")});
            }
          PS.close();
          RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }    

public void obtenerTodasLasAsistencias(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
        this.ASISTENCIAS.Lbldocumento.setText("");
        this.ASISTENCIAS.Lblnombre.setText("");
        this.ASISTENCIAS.Lbldireccion.setText("");
        this.ASISTENCIAS.Lblcargo.setText("");      
        String consulta = "SELECT DISTINCT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, CARGOS.CARGO FROM EMPLEADOS,CARGOS,HORA_INGRESO,TIPO_DOCUMENTO WHERE EMPLEADOS.FK_CARGO = CARGOS.ID AND EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.FK_TIPO_DOCUMENTO = TIPO_DOCUMENTO.ID AND EMPLEADOS.DOCUMENTO =? AND TIPO_DOCUMENTO.DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(consulta);
            PS.setString(1,documento);
            PS.setString(2, tipo_documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.Lbldocumento.setText(RS.getString("DOCUMENTO"));
                this.ASISTENCIAS.Lblnombre.setText(RS.getString("PRIMER_NOMBRE")+ " "+RS.getString("SEGUNDO_NOMBRE")+ " "
                +RS.getString("PRIMER_APELLIDO")+ " "+RS.getString("SEGUNDO_APELLIDO"));
                this.ASISTENCIAS.Lbldireccion.setText(RS.getString("DIRECCION"));
                this.ASISTENCIAS.Lblcargo.setText(RS.getString("CARGOS.CARGO"));                
            }    
        //SELECT hora_ingreso, hora_salida, (MINUTE(time(hora_salida)) - MINUTE(time(hora_ingreso)))/60 as "horas" from hora_ingreso where minute(hora_salida) > 0   
            String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_AM, HORAS_TRABAJADAS_AM, HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\" from hora_ingreso,empleados,tipo_documento where empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            PS.setString(2, tipo_documento);
            RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.TABLA.addRow(new Object[]{RS.getString("HORA_INGRESO_AM"),
                    RS.getString("HORA_SALIDA_AM"),RS.getString("HORAS_TRABAJADAS_AM"),RS.getString("HORA_INGRESO_PM"),
                    RS.getString("HORA_SALIDA_PM"),RS.getString("HORAS_TRABAJADAS_PM"), RS.getString("HORAS")});
            }
          PS.close();
          RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    public String ImprimirAsistencias(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
            this.tituloReporte = temp;            
            this.mensaje = "";
            try{
                PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT TIPOD.DOCUMENTO, empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){
                    this.usuarioReporte = "\t\tNOMBRE: " + RS.getString("empleados.PRIMER_NOMBRE") + " " 
                    + RS.getString("empleados.SEGUNDO_NOMBRE") + " " + RS.getString("empleados.PRIMER_APELLIDO") + " "
                    + RS.getString("empleados.SEGUNDO_APELLIDO") + "  " +RS.getString("TIPOD.DOCUMENTO") + ": " + RS.getString("empleados.DOCUMENTO") 
                    + "  CARGO: " + RS.getString("C.CARGO") + "\n\n";
                }
                PS.close();
                RS.close();
            /*}catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }*/
            String query = "SELECT empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO, HORA_INGRESO_AM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM as \"HORAS\",COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            //PreparedStatement PS;
           // try{
                mensaje += "\tHORA DE INGRESO\t HORA DE SALIDA\tTOTAL HORAS TRABAJADAS\tCOMENTARIOS\n";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    mensaje += "\t" + RS.getString("HORA_INGRESO_AM") + "\t  " +RS.getString( "HORA_SALIDA_PM") + "\t                 " 
                            +RS.getString( "HORAS")+"\t\t"+RS.getString( "COMENTARIOS_PM")+"\n";
                }
                RS.close();
                PS.close();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }        
            return this.tituloReporte + this.usuarioReporte + this.mensaje;
        }
    
    public String ImprimirAsistenciasPorSistemaAM(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
            this.tituloReporte = temp;            
            this.mensaje = "";
            try{
                PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT TIPOD.DOCUMENTO, empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){
                    this.usuarioReporte = "\t\tNOMBRE: " + RS.getString("empleados.PRIMER_NOMBRE") + " " 
                    + RS.getString("empleados.SEGUNDO_NOMBRE") + " " + RS.getString("empleados.PRIMER_APELLIDO") + " "
                    + RS.getString("empleados.SEGUNDO_APELLIDO") + "  " +RS.getString("TIPOD.DOCUMENTO") + ": " + RS.getString("empleados.DOCUMENTO") 
                    + "  CARGO: " + RS.getString("C.CARGO") + "\n\n";
                }
                PS.close();
                RS.close();
            /*}catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }*/
            String query = "SELECT empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO, HORA_INGRESO_AM, HORA_SALIDA_AM,HORAS_TRABAJADAS_AM,HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\",COMENTARIOS_AM,COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ?  AND REGISTRO_INGRESO_AM = 1 AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            //PreparedStatement PS;
           // try{
                mensaje += "     HORA DE INGRESO AM \t HORA DE SALIDA AM\t HORAS TRABAJADAS AM\t HORA DE INGRESO PM\t HORA DE SALIDA PM\tHORAS TRABAJADAS PM\t\tCOMENTARIOS AM\t\t\tCOMENTARIOS PM\n";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    mensaje += "       " + RS.getString("HORA_INGRESO_AM") + "\t  " + RS.getString("HORA_SALIDA_AM")+ "\t                 " 
                    + RS.getString( "HORAS_TRABAJADAS_AM")+ "\t  " + RS.getString( "HORA_INGRESO_PM")+ 
                    "\t  " +RS.getString( "HORA_SALIDA_PM") + "\t                 " +RS.getString( "HORAS_TRABAJADAS_PM")+"\t                 "
                    +RS.getString( "COMENTARIOS_AM")+"\t                 " +RS.getString( "COMENTARIOS_PM")+"\n";
                }
                RS.close();
                PS.close();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }        
            return this.tituloReporte + this.usuarioReporte + this.mensaje;
        }
    
        public String ImprimirAsistenciasPorSistemaPM(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
            this.tituloReporte = temp;            
            this.mensaje = "";
            try{
                PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT TIPOD.DOCUMENTO, empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){
                    this.usuarioReporte = "\t\tNOMBRE: " + RS.getString("empleados.PRIMER_NOMBRE") + " " 
                    + RS.getString("empleados.SEGUNDO_NOMBRE") + " " + RS.getString("empleados.PRIMER_APELLIDO") + " "
                    + RS.getString("empleados.SEGUNDO_APELLIDO") + "  " +RS.getString("TIPOD.DOCUMENTO") + ": " + RS.getString("empleados.DOCUMENTO") 
                    + "  CARGO: " + RS.getString("C.CARGO") + "\n\n";
                }
                PS.close();
                RS.close();
            /*}catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }*/
            String query = "SELECT empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO, HORA_INGRESO_AM, HORA_SALIDA_AM,HORAS_TRABAJADAS_AM,HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\",COMENTARIOS_AM,COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ?  AND REGISTRO_INGRESO_PM = 1 AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
            //PreparedStatement PS;
           // try{
                mensaje += "     HORA DE INGRESO AM \t HORA DE SALIDA AM\t HORAS TRABAJADAS AM\t HORA DE INGRESO PM\t HORA DE SALIDA PM\tHORAS TRABAJADAS PM\t\tCOMENTARIOS AM\t\t\tCOMENTARIOS PM\n";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    mensaje += "       " + RS.getString("HORA_INGRESO_AM") + "\t  " + RS.getString("HORA_SALIDA_AM")+ "\t                 " 
                    + RS.getString( "HORAS_TRABAJADAS_AM")+ "\t  " + RS.getString( "HORA_INGRESO_PM")+ 
                    "\t  " +RS.getString( "HORA_SALIDA_PM") + "\t                 " +RS.getString( "HORAS_TRABAJADAS_PM")+"\t                 "
                    +RS.getString( "COMENTARIOS_AM")+"\t                 " +RS.getString( "COMENTARIOS_PM")+"\n";
                }
                RS.close();
                PS.close();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }        
            return this.tituloReporte + this.usuarioReporte + this.mensaje;
        }
    
public void ImprimirAsistenciasReportes(String tipo_documento, String documento, String fechaInicial, String fechaFinal,String path){        
           // this.tituloReporte = temp;   
           boolean flag = true;
           boolean flagEmpleado = true;
            try{
               CrearPDF PDF = new CrearPDF();
               PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){                    
                    String empleado = "DOCUMENTO: " + RS.getString("empleados.DOCUMENTO")+ ";NOMBRE: " 
                    + RS.getString("empleados.PRIMER_NOMBRE") + " " + RS.getString("empleados.SEGUNDO_NOMBRE") + " " 
                    + RS.getString("empleados.PRIMER_APELLIDO") + " " + RS.getString("empleados.SEGUNDO_APELLIDO") 
                    + ";CARGO: " + RS.getString("C.CARGO")+ ";FECHA INICIAL: "+fechaInicial + ";FECHA FINAL: "+ fechaFinal;
                    if(flagEmpleado == true){
                        PDF.montarTablaDatosEmpleado(empleado);
                        flagEmpleado = false;
                    }
                }
                PS.close();
                RS.close();
                
                mensaje2 = "HORA DE INGRESO;HORA DE SALIDA;TOTAL HORAS TRABAJADAS;COMENTARIOS";
                String query = "SELECT HORA_INGRESO_AM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM as \"HORAS\", COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    if(flag == true){
                       PDF.montarPDF(path);
                       PDF.agregarDatos(PDF.tabla, mensaje2, PDF.helveticaBold, true);
                       flag = false;
                    }
                    String data = RS.getString("HORA_INGRESO_AM")+ ";" 
                            + RS.getString( "HORA_SALIDA_PM")+ ";" 
                            + RS.getString( "HORAS")+";"
                            + RS.getString("COMENTARIOS_PM");
                    PDF.agregarDatos(PDF.tabla, data, PDF.helvetica, false);
                }
                PDF.doc.add(PDF.TablaDatosEmpleado);
                PDF.doc.add(PDF.tabla);
                PDF.doc.close();
                RS.close();
                PS.close();
            }catch(SQLException | IOException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            }        
            //return this.tituloReporte + this.usuarioReporte + this.mensaje;
}

//public void ImprimirAsistenciasReportes(String tipo_documento, String documento, String fechaInicial, String fechaFinal){        
//           // this.tituloReporte = temp;   
//           mensaje2="";
//            try{
//               PreparedStatement PS;
//               ResultSet RS;
//                String query = "SELECT empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO, HORA_INGRESO_AM, HORA_SALIDA_AM,HORAS_TRABAJADAS_AM,HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\", COMENTARIOS_AM, COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
//                mensaje2 += "       HORA DE INGRESO AM      HORA DE SALIDA AM    HORAS TRABAJADAS AM   HORA DE INGRESO PM     HORA DE SALIDA PM   HORAS TRABAJADAS PM  TOTAL\tCOMENTARIOS_AM\t\tCOMENTARIOS_PM\n";
//                PS = conexion.getConnection().prepareStatement(query);
//                PS.setString(1, documento);
//                PS.setString(2, tipo_documento);
//                RS = PS.executeQuery();
//                while(RS.next()){
//                    mensaje2 += "        "+RS.getString("HORA_INGRESO_AM") + "  " + RS.getString("HORA_SALIDA_AM")+ "     " 
//                    + RS.getString( "HORAS_TRABAJADAS_AM")+ "        " + RS.getString( "HORA_INGRESO_PM")+ 
//                    "  " +RS.getString( "HORA_SALIDA_PM") + "      " +RS.getString( "HORAS_TRABAJADAS_PM")
//                            + "     " +RS.getString( "HORAS")+"\t\t"+ RS.getString("COMENTARIOS_AM") +"\t\t\t"+ RS.getString("COMENTARIOS_PM")+"\n\n";
//                }
//                RS.close();
//                PS.close();
//            }catch(SQLException ex){
//                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
//            }        
//            //return this.tituloReporte + this.usuarioReporte + this.mensaje;
//}

public void ImprimirAsistenciasReportesPorSistemaAM(String tipo_documento, String documento, String fechaInicial, String fechaFinal,String path){        
           // this.tituloReporte = temp; 
           String mensaje;
           boolean flag = true;
           boolean flagEmpleado = true;
            try{
               CrearPDF PDF = new CrearPDF();
               PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){                    
                    String empleado = "DOCUMENTO: " + RS.getString("empleados.DOCUMENTO")+ ";NOMBRE: " 
                    + RS.getString("empleados.PRIMER_NOMBRE") + " " + RS.getString("empleados.SEGUNDO_NOMBRE") + " " 
                    + RS.getString("empleados.PRIMER_APELLIDO") + " " + RS.getString("empleados.SEGUNDO_APELLIDO") 
                    + ";CARGO: " + RS.getString("C.CARGO")+ ";FECHA INICIAL: "+fechaInicial + ";FECHA FINAL: "+ fechaFinal;
                    if(flagEmpleado == true){
                        PDF.montarTablaDatosEmpleado(empleado);
                        flagEmpleado = false;
                    }
                }
                PS.close();
                RS.close();
                
                mensaje = "HORA DE INGRESO AM;HORA DE SALIDA AM;HORAS TRABAJADAS AM;HORA DE INGRESO PM;HORA DE SALIDA PM;HORAS TRABAJADAS PM;TOTAL HORAS;COMENTARIOS AM;COMENTARIOS PM";
                String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_AM,HORAS_TRABAJADAS_AM,HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\",COMENTARIOS_AM,COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ?  and HORA_INGRESO.REGISTRO_INGRESO_AM = 1 AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    if(flag == true){
                       PDF.montarPDF(path);
                       PDF.agregarDatos(PDF.tabla, mensaje, PDF.helveticaBold, true);
                       flag = false;
                    }
                    String data = RS.getString("HORA_INGRESO_AM")+ ";" 
                            + RS.getString("HORA_SALIDA_AM")+ ";" 
                            + RS.getString( "HORAS_TRABAJADAS_AM")+ ";" 
                            + RS.getString( "HORA_INGRESO_PM")+ ";" 
                            + RS.getString( "HORA_SALIDA_PM")+ ";" 
                            + RS.getString( "HORAS_TRABAJADAS_PM")+ ";"
                            + RS.getString( "HORAS")+ ";"
                            + RS.getString("COMENTARIOS_AM")+ ";"
                            + RS.getString("COMENTARIOS_PM")+ ";";
                    PDF.agregarDatos(PDF.tabla, data, PDF.helvetica, false);
                }
                PDF.doc.add(PDF.TablaDatosEmpleado);
                PDF.doc.add(PDF.tabla);
                PDF.doc.close();
                RS.close();
                PS.close();
            }catch(SQLException | IOException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            } 
}

public void ImprimirAsistenciasReportesPorSistemaPM(String tipo_documento, String documento, String fechaInicial, String fechaFinal,String path){        
           // this.tituloReporte = temp; 
           String mensaje;
           boolean flag = true;
           boolean flagEmpleado = true;
            try{
               CrearPDF PDF = new CrearPDF();
               PreparedStatement PS;            
                PS = conexion.getConnection().prepareStatement("SELECT DISTINCT empleados.DOCUMENTO, C.CARGO, empleados.PRIMER_NOMBRE, empleados.SEGUNDO_NOMBRE, empleados.PRIMER_APELLIDO,empleados.SEGUNDO_APELLIDO from empleados inner join hora_ingreso HORAIN on empleados.ID = HORAIN.FK_EMPLEADO inner join tipo_documento TIPOD on empleados.FK_TIPO_DOCUMENTO = TIPOD.ID inner join cargos C on empleados.FK_CARGO = C.ID  WHERE empleados.DOCUMENTO = ? and TIPOD.DOCUMENTO = ?");
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                ResultSet RS = PS.executeQuery();
                while(RS.next()){                    
                    String empleado = "DOCUMENTO: " + RS.getString("empleados.DOCUMENTO")+ ";NOMBRE: " 
                    + RS.getString("empleados.PRIMER_NOMBRE") + " " + RS.getString("empleados.SEGUNDO_NOMBRE") + " " 
                    + RS.getString("empleados.PRIMER_APELLIDO") + " " + RS.getString("empleados.SEGUNDO_APELLIDO") 
                    + ";CARGO: " + RS.getString("C.CARGO")+ ";FECHA INICIAL: "+fechaInicial + ";FECHA FINAL: "+ fechaFinal;
                    if(flagEmpleado == true){
                        PDF.montarTablaDatosEmpleado(empleado);
                        flagEmpleado = false;
                    }
                }
                PS.close();
                RS.close();
                
                mensaje = "HORA DE INGRESO AM;HORA DE SALIDA AM;HORAS TRABAJADAS AM;HORA DE INGRESO PM;HORA DE SALIDA PM;HORAS TRABAJADAS PM;TOTAL HORAS;COMENTARIOS AM;COMENTARIOS PM";
                String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_AM,HORAS_TRABAJADAS_AM,HORA_INGRESO_PM,HORA_SALIDA_PM,HORAS_TRABAJADAS_PM, ADDTIME(HORAS_TRABAJADAS_AM,HORAS_TRABAJADAS_PM) as \"HORAS\",COMENTARIOS_AM,COMENTARIOS_PM from hora_ingreso,empleados,tipo_documento where  empleados.id = hora_ingreso.FK_EMPLEADO and tipo_documento.ID = empleados.FK_TIPO_DOCUMENTO and empleados.DOCUMENTO = ? and tipo_documento.DOCUMENTO = ?  and HORA_INGRESO.REGISTRO_INGRESO_PM = 1 AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\")";
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                PS.setString(2, tipo_documento);
                RS = PS.executeQuery();
                while(RS.next()){
                    if(flag == true){
                       PDF.montarPDF(path);
                       PDF.agregarDatos(PDF.tabla, mensaje, PDF.helveticaBold, true);
                       flag = false;
                    }
                    String data = RS.getString("HORA_INGRESO_AM")+ ";" 
                            + RS.getString("HORA_SALIDA_AM")+ ";" 
                            + RS.getString( "HORAS_TRABAJADAS_AM")+ ";" 
                            + RS.getString( "HORA_INGRESO_PM")+ ";" 
                            + RS.getString( "HORA_SALIDA_PM")+ ";" 
                            + RS.getString( "HORAS_TRABAJADAS_PM")+ ";"
                            + RS.getString( "HORAS")+ ";"
                            + RS.getString("COMENTARIOS_AM")+ ";"
                            + RS.getString("COMENTARIOS_PM")+ ";";
                    PDF.agregarDatos(PDF.tabla, data, PDF.helvetica, false);
                }
                PDF.doc.add(PDF.TablaDatosEmpleado);
                PDF.doc.add(PDF.tabla);
                PDF.doc.close();
                RS.close();
                PS.close();
            }catch(SQLException | IOException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
            } 
}
 
    
    
    
    public void obtenerTipoDocumentoAsistencia(){
        String query = "SELECT DOCUMENTO FROM TIPO_DOCUMENTO ORDER BY DOCUMENTO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.ASISTENCIAS.tipo_documento.addItem(RS.getString("DOCUMENTO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String [] obtenerDatosUsuarioEditar(String documento){
        String datos [] = new String[15];
        String query = "SELECT TIPO_DOCUMENTO.DOCUMENTO, EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, TELEFONO, CORREO, CARGO, SEXO, PASSWORD, MUNICIPIOS.NOMBRE, DEPARTAMENTOS.NOMBRE \n" +
"FROM EMPLEADOS INNER JOIN TIPO_DOCUMENTO ON TIPO_DOCUMENTO.ID = EMPLEADOS.FK_TIPO_DOCUMENTO INNER JOIN SEXO ON EMPLEADOS.FK_SEXO = SEXO.ID INNER JOIN CARGOS ON EMPLEADOS.FK_CARGO = CARGOS.ID "
                + "INNER JOIN MUNICIPIOS ON MUNICIPIOS.ID = EMPLEADOS.FK_MUNICIPIO INNER JOIN DEPARTAMENTOS ON DEPARTAMENTOS.ID = MUNICIPIOS.FK_DEPTO AND EMPLEADOS.DOCUMENTO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();            
            while(RS.next()){
                datos[0] = RS.getString("TIPO_DOCUMENTO.DOCUMENTO");
                datos[1] = RS.getString("EMPLEADOS.DOCUMENTO");
                datos[2] = RS.getString("PRIMER_NOMBRE");
                datos[3] = RS.getString("SEGUNDO_NOMBRE");
                datos[4] = RS.getString("PRIMER_APELLIDO");
                datos[5] = RS.getString("SEGUNDO_APELLIDO");
                datos[6] = RS.getString("DIRECCION");
                datos[7] = RS.getString("TELEFONO");
                datos[8] = RS.getString("CORREO");
                datos[9] = RS.getString("CARGO");
                datos[10] = RS.getString("SEXO");
                datos[11] = RS.getString("PASSWORD");
                datos[12] = RS.getString("DEPARTAMENTOS.NOMBRE");
                datos[13] = RS.getString("MUNICIPIOS.NOMBRE");
            }
            PS.close();
            RS.close();
            this.EDITAREMPLEADOS.tipoDocumento.setSelectedItem(datos[0]);
            this.EDITAREMPLEADOS.cargo.setSelectedItem(datos[9]);
            this.EDITAREMPLEADOS.sexo.setSelectedItem(datos[10]);
            this.EDITAREMPLEADOS.DEPARTAMENTOS.setSelectedItem(datos[12]);
            this.EDITAREMPLEADOS.MUNICIPIOS.setSelectedItem(datos[13]);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }        
        return datos;
    }
    
    public String [] obtenerDatosUsuario(String documento){
        String datos [] = new String[10];
        String query = "SELECT DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, TELEFONO, CORREO, FK_ROL,ID FROM EMPLEADOS WHERE DOCUMENTO =?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();            
            while(RS.next()){
                datos[0] = RS.getString("DOCUMENTO");
                datos[1] = RS.getString("PRIMER_NOMBRE");
                datos[2] = RS.getString("SEGUNDO_NOMBRE");
                datos[3] = RS.getString("PRIMER_APELLIDO");
                datos[4] = RS.getString("SEGUNDO_APELLIDO");
                datos[5] = RS.getString("DIRECCION");
                datos[6] = RS.getString("TELEFONO");
                datos[7] = RS.getString("CORREO");
                datos[8] = String.valueOf(RS.getInt("FK_ROL"));
                datos[9] = String.valueOf(RS.getInt("ID"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        
        return datos;
    }
    
    public String [] obtenerDatosActualizarUsuario(String documento){
        String datos [] = new String[10];
        String query = "SELECT DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION, TELEFONO, CORREO, FK_ROL,ID FROM EMPLEADOS WHERE DOCUMENTO =?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();            
            while(RS.next()){
                datos[0] = RS.getString("DOCUMENTO");
                datos[1] = RS.getString("PRIMER_NOMBRE");
                datos[2] = RS.getString("SEGUNDO_NOMBRE");
                datos[3] = RS.getString("PRIMER_APELLIDO");
                datos[4] = RS.getString("SEGUNDO_APELLIDO");
                datos[5] = RS.getString("DIRECCION");
                datos[6] = RS.getString("TELEFONO");
                datos[7] = RS.getString("CORREO");
                datos[8] = String.valueOf(RS.getInt("FK_ROL"));
                datos[9] = String.valueOf(RS.getInt("ID"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        
        return datos;
    }
    
    public void obtenerSexo(){
        String query = "SELECT SEXO FROM SEXO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EMPLEADOS.sexo.addItem(RS.getString("SEXO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void obtenerSexoActualizacion(){
        String query = "SELECT SEXO FROM SEXO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EDITAREMPLEADOS.sexo.addItem(RS.getString("SEXO"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*public void obtenerContrato(){
        String query = "SELECT NOMBRE FROM CONTRATOS";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EMPLEADOS.contrato.addItem(RS.getString("NOMBRE"));
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    
    public String obtenerIDContrato(String nombreContrato){
        String id = "";
        String query = "SELECT ID FROM CONTRATOS WHERE NOMBRE =?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, nombreContrato);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                id = RS.getString("ID");
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }
    
    /*public void obtenerDatosContrato(String contrato){
        String query = "SELECT FECHA_INICIO, FECHA_FIN_CONTRATO, DESCRIPCION_CONTRATO FROM CONTRATOS WHERE NOMBRE =?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, contrato);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EMPLEADOS.fechainicio.setText(RS.getString("FECHA_INICIO"));
                this.EMPLEADOS.fechaterminacion.setText(RS.getString("FECHA_FIN_CONTRATO"));
                this.EMPLEADOS.descripcionContrato.setText(RS.getString("DESCRIPCION_CONTRATO"));                
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }  */ 
    
    public void obtenerCargos(){
        String query = "SELECT CARGO FROM CARGOS ORDER BY CARGO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);           
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EMPLEADOS.cargo.addItem(RS.getString("CARGO"));                
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    public void obtenerCargosActualizar(){
        String query = "SELECT CARGO FROM CARGOS ORDER BY CARGO";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);           
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.EDITAREMPLEADOS.cargo.addItem(RS.getString("CARGO"));                
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    } 
        
    public void obtenerCargosVentanaActualizar(){
        String query = "SELECT CARGO FROM CARGOS order by CARGO ASC;";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);           
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.AGREGARCARGOS.TABLA.addRow(new Object []{RS.getString("CARGO")});                
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    public boolean iniciarSesion(String documento, String password){
        boolean flag = false;
         String query = "SELECT ID FROM EMPLEADOS WHERE DOCUMENTO=? AND PASSWORD=?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            PS.setString(2,password);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                flag = true;
            }
            PS.close();
            RS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }   
    return flag;
    }
    
     public boolean consultarIngresoDeUsuariosAM(int documento){
        boolean flag = false;
        String query = "SELECT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND EMPLEADOS.DOCUMENTO = ?";
        try{
        PreparedStatement PS;
        PS = conexion.getConnection().prepareStatement(query);
        PS.setInt(1, documento);
        ResultSet RS = PS.executeQuery();
        while(RS.next()){
            if(RS.getString("EMPLEADOS.ID") != null){
                flag = true;
            }
        }
        RS.close();
        PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje 1", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }   
    
        public boolean consultarIngresoDeUsuariosAMID(int id){
        boolean flag = false;
        String query = "SELECT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T12:59:59\") AND EMPLEADOS.ID = ?";
        try{
        PreparedStatement PS;
        PS = conexion.getConnection().prepareStatement(query);
        PS.setInt(1, id);
        ResultSet RS = PS.executeQuery();
        while(RS.next()){
            if(RS.getString("EMPLEADOS.ID") != null){
                flag = true;
            }
        }
        RS.close();
        PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje 1", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }  
   
    public boolean consultarIngresoDeUsuariosPM(int id){
        boolean flag = false;
        String query = "SELECT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE, \"T23:59:59\") AND EMPLEADOS.DOCUMENTO = ?";
        try{
        PreparedStatement PS;
        PS = conexion.getConnection().prepareStatement(query);
        PS.setInt(1, id);
        ResultSet RS = PS.executeQuery();
        while(RS.next()){
            flag = true;             
        }
        RS.close();
        PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje 3", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }   
    
    
    public boolean consultarSalidaDeUsuariosAM(int id){
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = false;
        String query = "SELECT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_SALIDA_PM >= (\""+formato.format(fecha)+"T00:00:00\") AND HORA_INGRESO.HORA_SALIDA_PM <= (\""+formato.format(fecha)+"T23:59:59\") AND EMPLEADOS.DOCUMENTO = ?";
        try{
        PreparedStatement PS;
        PS = conexion.getConnection().prepareStatement(query);
        PS.setInt(1, id);
        ResultSet RS = PS.executeQuery();
        while(RS.next()){
            flag= true;
        }
        RS.close();
        PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje 2", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }  
    
    public void asignarSalidaDeUsuariosAMSistema(){
        //Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String query = "SELECT EMPLEADOS.ID, EMPLEADOS.DOCUMENTO FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(date(HORA_INGRESO_AM),\"T08:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\") AND HORA_SALIDA_PM IS NULL";
        try{
            int x = 0;
            ArrayList<String> arrayID = new ArrayList<String>();
            ArrayList<String> arrayDocumento = new ArrayList<String>();
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                arrayID.add(x,RS.getString("EMPLEADOS.ID"));
                arrayDocumento.add(x,RS.getString("EMPLEADOS.DOCUMENTO"));
                x+= 1;
            }            
            RS.close();
            PS.close();
            this.ejecutarSalidaDeUsuariosAMSistema(arrayID, arrayDocumento);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
    public void ejecutarSalidaDeUsuariosAMSistema(ArrayList<String> arrayID,ArrayList<String> arrayDocumento){        
        //Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for(int x = 0; x < arrayID.size(); x++){
                PreparedStatement PS;                
                String query = "UPDATE HORA_INGRESO,EMPLEADOS SET HORA_SALIDA_AM = CONCAT(date(HORA_INGRESO_AM),\"T12:30:00\"), HORAS_TRABAJADAS_AM = TIMEDIFF(CONCAT(date(HORA_INGRESO_AM),\"T12:30:00\"),HORA_INGRESO_AM), REGISTRO_INGRESO_AM =?, COMENTARIOS_AM =? WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.FK_EMPLEADO =? AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(date(HORA_INGRESO_AM),\"T08:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\")";
                PS = conexion.getConnection().prepareStatement(query);
                //PS.setString(1, formato.format(fecha)+"T12:30:00");
                PS.setInt(1,1);
                PS.setString(2,"Registro de asistencia cerrado por el sistema");
                PS.setInt(3,Integer.parseInt(arrayID.get(x)));
                PS.setInt(4,Integer.parseInt(arrayDocumento.get(x)));
                PS.execute();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    
    public void asignarSalidaDeUsuariosPMSistema(){
        String query = "SELECT EMPLEADOS.ID, EMPLEADOS.DOCUMENTO FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(date(HORA_INGRESO_AM),\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\") AND HORA_SALIDA_PM IS NULL";
        try{
            int x = 0;
            ArrayList<String> arrayID = new ArrayList<String>();
            ArrayList<String> arrayDocumento = new ArrayList<String>();
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                arrayID.add(x,RS.getString("EMPLEADOS.ID"));
                arrayDocumento.add(x,RS.getString("EMPLEADOS.DOCUMENTO"));
                x+= 1;
            }            
            RS.close();
            PS.close();
            this.ejecutarSalidaDeUsuariosPMSistema(arrayID, arrayDocumento);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ejecutarSalidaDeUsuariosPMSistema(ArrayList<String> arrayID,ArrayList<String> arrayDocumento){        
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for(int x = 0; x < arrayID.size(); x++){
                PreparedStatement PS;                
                String query = "UPDATE HORA_INGRESO,EMPLEADOS SET HORA_SALIDA_PM = CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\"), HORAS_TRABAJADAS_PM = TIMEDIFF(CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\"),HORA_INGRESO_AM), REGISTRO_INGRESO_PM =?, COMENTARIOS_PM =? WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.FK_EMPLEADO =? AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(date(HORA_INGRESO_AM),\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(date(HORA_INGRESO_AM),\"T23:59:59\")";
                PS = conexion.getConnection().prepareStatement(query);
                //PS.setString(1, formato.format(fecha)+"T18:00:00");
                PS.setInt(1,1);
                PS.setString(2,"Registro de asistencia cerrado por el sistema");
                PS.setInt(3,Integer.parseInt(arrayID.get(x)));
                PS.setInt(4,Integer.parseInt(arrayDocumento.get(x)));
                PS.execute();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje error", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    
    public boolean consultarSalidaDeUsuariosPM(int documento){
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = false;
        String query = "SELECT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND HORA_INGRESO.HORA_SALIDA_PM >= (\""+formato.format(fecha)+"T00:00:00\") AND HORA_INGRESO.HORA_SALIDA_PM <= (\""+formato.format(fecha)+"T23:59:59\") AND EMPLEADOS.DOCUMENTO = ?";
        try{
        PreparedStatement PS;
        PS = conexion.getConnection().prepareStatement(query);
        PS.setInt(1, documento);
        ResultSet RS = PS.executeQuery();
        while(RS.next()){
            flag= true;
        }
        RS.close();
        PS.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }  
    
    public boolean ingresarCargos(String cargo){
        String query = "INSERT INTO CARGOS VALUES(?,?)";
        PreparedStatement PS;
        boolean flag = false;
        try {
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, null);
            PS.setString(2,cargo);
            flag = PS.execute();
            if(flag == false){
                flag= true;
            }
            PS.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.WARNING_MESSAGE);
        }
        return flag;
    }
    
    public boolean actualizarCargos(String cargoViejo, String cargoNuevo){
        String query = "UPDATE CARGOS SET CARGO =? WHERE CARGO =?";
        boolean flag = false;
        try {
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, cargoNuevo);
            PS.setString(2, cargoViejo);
            flag = PS.execute();
            if(flag == false){
                flag = true;               
            }
            PS.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }        
        return flag;
    }
    
    
    public boolean consultarCargo(String cargo){
        String query = "SELECT CARGO FROM CARGOS WHERE CARGO =?";
        boolean flag = false;
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,cargo);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                if(RS.next() == false){
                    flag = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }
    
    
    //FUNCION QUE YA NO SE USA DEBIDO A QUE A LA TABLA HORA_INGRESO SE LE AGREGARON MAS COLUMNAS
    public boolean RegistrarHoraIngreso(int id, String hora_ingreso){
        boolean flag = false;
        String query = "INSERT INTO HORA_INGRESO VALUES(?,?,?,?,?)";
        try{
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,null);
            PS.setString(2,hora_ingreso);
            PS.setString(3,"1000-01-01 00:00:00");            
            PS.setInt(4,0);
            PS.setInt(5,id);
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
    
    
    //REGISTRA EL INGRESO DE LA LLEGADA DEL EMPLEADO EN EL SISTEMA
    public boolean RegistrarHoraIngresoAM(int id, String hora_ingreso_am){
        boolean flag = false;
        String query = "INSERT INTO HORA_INGRESO VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,null);
            PS.setString(2,hora_ingreso_am);
            PS.setString(3,null);
            PS.setString(4,null);  
            PS.setString(5,null);
            PS.setString(6,null);
            PS.setInt(7,id);
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
    
        //FUNCION QUE INGRESA EL REGISTRO DE ASISTENCIA EN UN HORARIO DETERMINADO EN LA CONSULTA SQL
    public boolean RegistrarHoraIngresoPM(int id, String hora_ingreso_pm){
        boolean flag = false;
        if(this.consultarIngresoDeUsuariosAMID(id)){
//        Date fecha = new Date();
//        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//        formato.format(fecha);
        String query = "UPDATE HORA_INGRESO SET HORA_SALIDA_PM = \""+hora_ingreso_pm+"\" WHERE HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND HORA_INGRESO.FK_EMPLEADO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);
            PS.setInt(1,id);                        
            flag = PS.execute();
            if(flag == false){
                flag = true;
            }
            PS.close();            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }  
        }
           return flag;
    } 
    
    //crear el calculo para que cuente las horas cuando se este ingresando la fecha de salida
    public boolean RegistrarHoraSalidaAM(int id, String hora_salida){
        boolean flag = false;
        //Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        //formato.format(fecha);
        String query = "UPDATE HORA_INGRESO SET HORA_SALIDA_AM = \""+hora_salida+"\", HORAS_TRABAJADAS_AM = TIMEDIFF(\""+hora_salida+"\",HORA_INGRESO.HORA_INGRESO_AM), REGISTRO_INGRESO_AM =?, COMENTARIOS_AM =?  WHERE HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND HORA_INGRESO.FK_EMPLEADO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);                      
            PS.setInt(1,2);
            PS.setString(2,"Registro de asistencia cerrado por el Empleado");
            PS.setInt(3,id);                        
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
    
    public boolean RegistrarHoraSalidaAMSistema(int id, String hora_salida){
        boolean flag = false;
        //Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        //formato.format(fecha);
        String query = "UPDATE HORA_INGRESO SET HORA_SALIDA_AM = \""+hora_salida+"\", HORAS_TRABAJADAS_AM = TIMEDIFF(\""+hora_salida+"\",HORA_INGRESO.HORA_INGRESO_AM), REGISTRO_INGRESO_AM =?  WHERE HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND HORA_INGRESO.FK_EMPLEADO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);                      
            PS.setInt(1,1);
            PS.setInt(2,id);                        
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
    
    public boolean RegistrarHoraSalidaPM(int id, String hora_salida_pm){
        boolean flag = false;
        Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        //formato.format(fecha);
        String query = "UPDATE HORA_INGRESO SET HORA_SALIDA_PM = \""+hora_salida_pm+"\", HORAS_TRABAJADAS_PM = TIMEDIFF(\""+hora_salida_pm+"\",HORA_INGRESO.HORA_INGRESO_AM), REGISTRO_INGRESO_PM =?, COMENTARIOS_PM =?  WHERE HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_AM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND HORA_INGRESO.FK_EMPLEADO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);                      
            PS.setInt(1,2);
            PS.setString(2,"Registro de asistencia cerrado por el Empleado");
            PS.setInt(3,id);                        
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
    
    public boolean RegistrarHoraSalidaPMSistema(int id, String hora_salida_pm){
        boolean flag = false;
        Date fecha = new Date();
        //DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        //formato.format(fecha);
        String query = "UPDATE HORA_INGRESO SET HORA_SALIDA_PM = \""+hora_salida_pm+"\", HORAS_TRABAJADAS_PM = TIMEDIFF(\""+hora_salida_pm+"\",HORA_INGRESO.HORA_INGRESO_PM), REGISTRO_INGRESO_PM =?  WHERE HORA_INGRESO.HORA_INGRESO_PM >= CONCAT(CURRENT_DATE,\"T00:00:00\") AND HORA_INGRESO.HORA_INGRESO_PM <= CONCAT(CURRENT_DATE,\"T23:59:59\") AND HORA_INGRESO.FK_EMPLEADO = ?";
        PreparedStatement PS;
        try{
            PS = conexion.getConnection().prepareStatement(query);                      
            PS.setInt(1,1);
            PS.setInt(2,id);                        
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
    
    public void validarComentarioUsuarioAMCerradoPorSistema(String documento){
        boolean flag= false;
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = new Date();        
        String query ="SELECT REGISTRO_INGRESO_AM FROM HORA_INGRESO,EMPLEADOS WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND REGISTRO_INGRESO_AM = 1 AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO.HORA_SALIDA_AM >= CONCAT(CURRENT_DATE,\"T08:00:00\") AND HORA_INGRESO.HORA_SALIDA_AM <= CONCAT(CURRENT_DATE,\"T12:46:00\") AND COMENTARIOS_AM = \"Registro de asistencia cerrado por el sistema\"";
        try {
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                if(RS.next() == false){
                    flag = true;
                }
            }
            PS.close();
            RS.close();
            if(flag == true){
                String respuesta;
                String comentario = JOptionPane.showInputDialog("¿Digita el motivo por el cual no pudiste registrar la salida del medio dia?");
                if(comentario != null){
                    if(comentario.equals("")){
                        respuesta = "El usuario no digito el motivo del registro de salida";
                    }else{
                        respuesta = comentario;
                    }                    
                }else{                   
                   respuesta = "El usuario no digito el motivo del registro de salida";
                }                
                 try {
                      query = "UPDATE HORA_INGRESO,EMPLEADOS SET COMENTARIOS_AM =? WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO.HORA_SALIDA_AM >= CONCAT(CURRENT_DATE,\"T08:00:00\") AND HORA_INGRESO.HORA_SALIDA_AM <= CONCAT(CURRENT_DATE,\"T12:46:00\") AND COMENTARIOS_AM = \"Registro de asistencia cerrado por el sistema\"";
                      PS = conexion.getConnection().prepareStatement(query);
                      PS.setString(1,respuesta);
                      PS.setString(2,documento);
                      PS.execute();
                 } catch (SQLException e){
                       JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
                 }               
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void validarComentarioUsuarioPMCerradoPorSistema(String fecha_inicial,String fecha_final){
        boolean flag = false;
        String query ="SELECT EMPLEADOS.DOCUMENTO, EMPLEADOS.PRIMER_NOMBRE,EMPLEADOS.SEGUNDO_NOMBRE,EMPLEADOS.PRIMER_APELLIDO,EMPLEADOS.SEGUNDO_APELLIDO,HORA_INGRESO_AM,HORA_SALIDA_PM,COMENTARIOS_PM FROM HORA_INGRESO,EMPLEADOS WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND REGISTRO_INGRESO_PM = 1 AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(?,\"T00:00:00\") AND HORA_INGRESO.HORA_SALIDA_PM <= CONCAT(?,\"T23:59:59\")";
        try {
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,fecha_inicial);
            PS.setString(2,fecha_final);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.SALIDASISTEMA.TABLA.addRow(new Object[]{RS.getString("EMPLEADOS.DOCUMENTO"),RS.getString("EMPLEADOS.PRIMER_NOMBRE")+" "
                        +RS.getString("EMPLEADOS.SEGUNDO_NOMBRE")+" "+RS.getString("EMPLEADOS.PRIMER_APELLIDO")
                        +" "+RS.getString("EMPLEADOS.SEGUNDO_APELLIDO"),RS.getString("HORA_INGRESO_AM")
                        ,RS.getString("HORA_SALIDA_PM"),RS.getString("COMENTARIOS_PM")});
            }
            PS.close();
            RS.close();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje de error",JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    
    public boolean actualizarComentariosdeUsuario(String comentario,String documento,String hora_ingreso_pm,String hora_salida_pm){
        boolean flag = false;
        String query = "UPDATE HORA_INGRESO,EMPLEADOS SET COMENTARIOS_PM =? WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO_PM =? AND HORA_SALIDA_PM =?";
        try {
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, comentario);
            PS.setString(2, documento);
            PS.setString(3, hora_ingreso_pm);
            PS.setString(4,hora_salida_pm);
            flag = PS.execute();
            if(flag == false){
                flag = true;
            }
            PS.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje de error",JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }
    
        public boolean actualizarComentariosPorAdmin(String comentario,String documento,String hora_ingreso_pm,String hora_salida_pm){
        boolean flag = false;
        String query = "UPDATE HORA_INGRESO,EMPLEADOS SET COMENTARIOS_PM =? WHERE HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND EMPLEADOS.DOCUMENTO =? AND HORA_INGRESO_AM =? AND HORA_SALIDA_PM =?";
        try {
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, comentario);
            PS.setString(2, documento);
            PS.setString(3, hora_ingreso_pm);
            PS.setString(4,hora_salida_pm);
            flag = PS.execute();
            if(flag == false){
                flag = true;
            }
            PS.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje de error",JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }
        
    public boolean validarEmpleadoParaReporte(String tipoDocumento, String documento,String fechaInicial,String fechaFinal){
     boolean flag = false;
     String query = "SELECT DISTINCT EMPLEADOS.ID FROM EMPLEADOS,HORA_INGRESO,TIPO_DOCUMENTO WHERE EMPLEADOS.DOCUMENTO =? AND EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO and TIPO_DOCUMENTO.ID = EMPLEADOS.FK_TIPO_DOCUMENTO AND tipo_documento.DOCUMENTO = ? AND hora_ingreso.HORA_INGRESO_AM >= concat(\""+fechaInicial+"\",\"T00:00:00\") and hora_ingreso.HORA_INGRESO_AM <= concat(\""+fechaFinal+"\",\"T23:59:59\") ";
     PreparedStatement PS;
     try{
         PS = conexion.getConnection().prepareStatement(query);
         PS.setString(1, documento);
         PS.setString(2, tipoDocumento);
         ResultSet RS = PS.executeQuery();
         if(RS.next()){
             if(RS.next() == false){
                 flag = true;
             }
         }
         PS.close();
         RS.close();
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
     }    
     return flag;
    }  
    
    public void obtenerListaEmpleados(){
        try {
            String query = "SELECT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, C.CARGO, DIRECCION,TELEFONO,CORREO FROM EMPLEADOS INNER JOIN TIPO_DOCUMENTO TD ON EMPLEADOS.FK_TIPO_DOCUMENTO = TD.ID INNER JOIN CARGOS C ON EMPLEADOS.FK_CARGO = C.ID";
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.VISUALIZAREMPLEADOS.TABLA.addRow(new Object[]{RS.getString("EMPLEADOS.DOCUMENTO"), RS.getString("PRIMER_NOMBRE")+" "+RS.getString("SEGUNDO_NOMBRE")+" "+RS.getString("PRIMER_APELLIDO")+" "+RS.getString("SEGUNDO_APELLIDO"),RS.getString("C.CARGO"),RS.getString("DIRECCION"),RS.getString("TELEFONO"),RS.getString("CORREO")});
            }
            PS.close();
            RS.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void obtenerUsuarioListaEmpleados(String documento,String tipoDocumento){
        try {
            String query = "SELECT EMPLEADOS.DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, C.CARGO, DIRECCION,TELEFONO,CORREO FROM EMPLEADOS INNER JOIN TIPO_DOCUMENTO TD ON EMPLEADOS.FK_TIPO_DOCUMENTO = TD.ID INNER JOIN CARGOS C ON EMPLEADOS.FK_CARGO = C.ID AND EMPLEADOS.DOCUMENTO = ? AND TD.DOCUMENTO =?";
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            PS.setString(2, tipoDocumento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.VISUALIZAREMPLEADOS.TABLA.addRow(new Object[]{RS.getString("EMPLEADOS.DOCUMENTO"), RS.getString("PRIMER_NOMBRE")+" "+RS.getString("SEGUNDO_NOMBRE")+" "+RS.getString("PRIMER_APELLIDO")+" "+RS.getString("SEGUNDO_APELLIDO"),RS.getString("C.CARGO"),RS.getString("DIRECCION"),RS.getString("TELEFONO"),RS.getString("CORREO")});
            }
            PS.close();
            RS.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
        public void obtenerTotalEmpleados(){
        try {
            String query = "SELECT COUNT(EMPLEADOS.DOCUMENTO) AS \"TOTAL\" FROM EMPLEADOS INNER JOIN TIPO_DOCUMENTO TD ON EMPLEADOS.FK_TIPO_DOCUMENTO = TD.ID INNER JOIN CARGOS C ON EMPLEADOS.FK_CARGO = C.ID";
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                this.VISUALIZAREMPLEADOS.LblTotalEmpleados.setText("TOTAL EMPLEADOS: "+RS.getString("TOTAL"));
            }
            PS.close();
            RS.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
        
            public void obtenerFotoListaEmpleados(String documento){
        String query = "SELECT FOTO FROM EMPLEADOS WHERE DOCUMENTO =?";
        try {
            PreparedStatement PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1,documento);
            ResultSet RS = PS.executeQuery();
            while(RS.next()){
                ImageIcon imagen = new ImageIcon();
                if(RS.getBlob("FOTO") == null){
                   this.VISUALIZAREMPLEADOS.imagenPerfil.setIcon(null); 
                }else{
                Blob blob = RS.getBlob("FOTO");
                byte Buffer[] = blob.getBytes(1, (int) blob.length());
                BufferedImage BI = null;
                try {
                    BI = ImageIO.read(new ByteArrayInputStream(Buffer));
                } catch (IOException e) {
                    Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
                }                
                imagen.setImage(BI);
                this.VISUALIZAREMPLEADOS.imagenPerfil.setIcon(new ImageIcon(BI.getScaledInstance(
                this.VISUALIZAREMPLEADOS.imagenPerfil.getWidth(),
                this.VISUALIZAREMPLEADOS.imagenPerfil.getHeight(),Image.SCALE_DEFAULT)));
            }
            }
            PS.close();
            RS.close();
        } catch (SQLException e) {
            Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
        }
    }
            
            
   public void obtenerDepartamentos(){
       String query = "SELECT NOMBRE FROM DEPARTAMENTOS ORDER BY NOMBRE ASC";
       PreparedStatement PS;
       ResultSet RS;
       try {
           PS = conexion.getConnection().prepareStatement(query);
           RS = PS.executeQuery();
           while(RS.next()){
               this.EMPLEADOS.DEPARTAMENTOS.addItem(RS.getString("NOMBRE"));
           }
           PS.close();
           RS.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       }
   }
   
      public void obtenerDepartamentosEditarEmpleados(){
       String query = "SELECT NOMBRE FROM DEPARTAMENTOS ORDER BY NOMBRE ASC";
       PreparedStatement PS;
       ResultSet RS;
       try {
           PS = conexion.getConnection().prepareStatement(query);
           RS = PS.executeQuery();
           while(RS.next()){
               this.EDITAREMPLEADOS.DEPARTAMENTOS.addItem(RS.getString("NOMBRE"));
           }
           PS.close();
           RS.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       }
   }
   
   public void obtenerMunicipios(String depto){
       String query = "SELECT MUNICIPIOS.NOMBRE FROM DEPARTAMENTOS, MUNICIPIOS WHERE DEPARTAMENTOS.ID = MUNICIPIOS.FK_DEPTO AND DEPARTAMENTOS.NOMBRE = ? ORDER BY MUNICIPIOS.NOMBRE ASC";
       PreparedStatement PS;
       ResultSet RS;
       try {
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,depto);
           RS = PS.executeQuery();
           while(RS.next()){
               this.EMPLEADOS.MUNICIPIOS.addItem(RS.getString("MUNICIPIOS.NOMBRE"));
           }
           PS.close();
           RS.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       }
   }
   
      public void obtenerMunicipiosEditarEmpleados(String depto){
       String query = "SELECT MUNICIPIOS.NOMBRE FROM DEPARTAMENTOS, MUNICIPIOS WHERE DEPARTAMENTOS.ID = MUNICIPIOS.FK_DEPTO AND DEPARTAMENTOS.NOMBRE = ? ORDER BY MUNICIPIOS.NOMBRE ASC";
       PreparedStatement PS;
       ResultSet RS;
       try {
           PS = conexion.getConnection().prepareStatement(query);
           PS.setString(1,depto);
           RS = PS.executeQuery();
           while(RS.next()){
               this.EDITAREMPLEADOS.MUNICIPIOS.addItem(RS.getString("MUNICIPIOS.NOMBRE"));
           }
           PS.close();
           RS.close();
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       }
   }
    
  public int obtenerIDMunicipio(String municipio){
      String query = "SELECT ID FROM MUNICIPIOS WHERE NOMBRE = ?";
      PreparedStatement PS;
      ResultSet RS;
      try {
          PS = conexion.getConnection().prepareStatement(query);
          PS.setString(1,municipio);
          RS = PS.executeQuery();
          while(RS.next()){
              return RS.getInt("ID");
          }
      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
      }
      return 0;
  }
   
}