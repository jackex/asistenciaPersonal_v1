/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import vista.AgregarEmpleados;
import vista.LoginHuella;
/**
 *
 * @author juliancc
 */
public class HuellaLogin {
    DBConnection conexion = new DBConnection();
    protected DPFPCapture LECTOR = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment RECLUTADOR = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    public DPFPVerification VERIFICADOR = DPFPGlobal.getVerificationFactory().createVerification();
    protected DPFPTemplate TEMPLATE;
    protected static String TEMPLATE_PROPERTY = "TEMPLATE";
    protected DPFPFeatureSet FEATURESINSCRIPCION;
    protected DPFPFeatureSet FEATUREVERIFICACION;
    public String documento;
    LoginHuella LOGINHUELLA;
 
    
    public HuellaLogin(LoginHuella huella){
    this.LOGINHUELLA = huella;
    }
    
    public HuellaLogin(){}
    
    public void start(){
        LECTOR.startCapture();
        mostrarMensaje("Lector de huella activado");
    }
    
    public void stop(){
    LECTOR.stopCapture();
    mostrarMensaje("Lector de huella desactivado");
    }
    
    public void mostrarMensaje(String texto){
        LOGINHUELLA.mensajeHuella.append(texto + "\n");     
    }
    
    public DPFPFeatureSet extraCatacteristicas(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException  e) {
            return null;
        }
    }
    
    public Image crearImagenHuella(DPFPSample sample){
           return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    
    public void dibujarHuella(Image image){
        LOGINHUELLA.LblMostrarHuella.setIcon(new ImageIcon(image.getScaledInstance(
                LOGINHUELLA.LblMostrarHuella.getWidth(), 
                LOGINHUELLA.LblMostrarHuella.getHeight(), Image.SCALE_DEFAULT)));
        //repaint();
    }
    
    public void estadoHuellas(){
        this.RECLUTADOR.getFeaturesNeeded();
    }
    
    public void Iniciar(){
        LECTOR.addDataListener(new DPFPDataAdapter(){
            @Override
            public void dataAcquired(final DPFPDataEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                       mostrarMensaje("La Huella digital ha sido capturada"); 
                       procesarCaptura(e.getSample());                       
                    }
                });}
        });
        
        LECTOR.addReaderStatusListener(new DPFPReaderStatusAdapter(){
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        mostrarMensaje("El sensor o lector de Huella esta activado o conectado");
                    }
                });
            }
            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        mostrarMensaje("El sensor de Huella digital esta desactivado o desconectado");
                    }
                });
            }            
        });
        
        LECTOR.addSensorListener(new DPFPSensorAdapter(){
            @Override
            public void fingerTouched(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        mostrarMensaje("El dedo ha sido puesto sobre el lector de huella");
                    }
                
                });
            }
            public void fingerGone(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        mostrarMensaje("El dedo ha sido retirado del lector de huella");
                    }
                });                
            }
        });
        LECTOR.addErrorListener(new DPFPErrorAdapter(){
            public void errorReader(final DPFPErrorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mostrarMensaje("Error " + e.getError());
                    }
                });
            }            
        });       
    }
    
      public DPFPTemplate getTemplate(){
            return this.TEMPLATE;
      }
      
      public void setTemplate(DPFPTemplate template){
          this.TEMPLATE = template;
//          firePropertyChange(TEMPLATE_PROPERTY, old, template);
      }
      
      public void procesarCaptura(DPFPSample sample){
          this.FEATURESINSCRIPCION = this.extraCatacteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
          this.FEATUREVERIFICACION = this.extraCatacteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
          
          if(this.FEATUREVERIFICACION  != null){
              try {
                  mostrarMensaje("Las caracteristicas de la huella han sido creadas");
                  this.RECLUTADOR.addFeatures(FEATURESINSCRIPCION);
//                  Image imagen  = this.crearImagenHuella(sample);
//                  this.dibujarHuella(imagen);                  
              } catch (DPFPImageQualityException e) {
                  //JOptionPane.showMessageDialog(null,e.getMessage());
              }
              finally{
                  this.estadoHuellas();
                  switch(this.RECLUTADOR.getTemplateStatus()){
                      case TEMPLATE_STATUS_READY:
                          this.stop();
                          this.setTemplate(this.RECLUTADOR.getTemplate());
                          mostrarMensaje("La plantilla de la huella ha sido creada".toUpperCase());
                          this.LOGINHUELLA.cerrarVentana();
                          this.LOGINHUELLA.BTNINICIARHUELLA.setEnabled(true);
                          this.consultarHuella();
                          break;
                          
                      case TEMPLATE_STATUS_FAILED:
                          this.RECLUTADOR.clear();
                          this.stop();
                          this.estadoHuellas();
                          this.setTemplate(null);
                          this.LOGINHUELLA.BTNINICIARHUELLA.setEnabled(true);
                          this.LOGINHUELLA.DOCUMENTOEMPLEADO.setText(null);
                          this.LOGINHUELLA.NOMBREEMPLEADO.setText(null);
                          this.LOGINHUELLA.CARGOEMPLEADO.setText(null);
                          this.LOGINHUELLA.mensajeHuella.setText("");
                          JOptionPane.showMessageDialog(null,"La plantilla de la huella no pudo ser creada, repita el proceso");
                          break;
                  }
              }
          }
      }      
      
     public void consultarHuella(){
            String query = "SELECT ID, DOCUMENTO, HUELLA FROM EMPLEADOS";
            PreparedStatement PS;
            ResultSet RS;
            try { 
                PS = conexion.getConnection().prepareStatement(query);
                RS = PS.executeQuery();
                while(RS.next()){
                    byte templateBuffer[] = RS.getBytes("HUELLA");
                    DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                    setTemplate(referenceTemplate);
                    DPFPVerificationResult result = VERIFICADOR.verify(FEATUREVERIFICACION, getTemplate());
                    if(result.isVerified()){
                        PreparedStatement PS2;
                        PS2 = conexion.getConnection().prepareStatement("SELECT PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, CARGOS.CARGO, MUNICIPIOS.NOMBRE FROM EMPLEADOS INNER JOIN CARGOS ON EMPLEADOS.FK_CARGO = CARGOS.ID "
                                + "INNER JOIN MUNICIPIOS ON MUNICIPIOS.ID = EMPLEADOS.FK_MUNICIPIO AND DOCUMENTO = ?");
                        this.LOGINHUELLA.ID = RS.getString("ID");
                        PS2.setString(1,RS.getString("DOCUMENTO"));
                        ResultSet RS2 = PS2.executeQuery();
                        while(RS2.next()){                      
                            this.LOGINHUELLA.DOCUMENTOEMPLEADO.setText(RS.getString("DOCUMENTO"));
                            this.LOGINHUELLA.NOMBREEMPLEADO.setText(RS2.getString("PRIMER_NOMBRE")+" "+RS2.getString("SEGUNDO_NOMBRE")
                            +" "+RS2.getString("PRIMER_APELLIDO")+" "+RS2.getString("SEGUNDO_APELLIDO"));
                            this.LOGINHUELLA.CARGOEMPLEADO.setText(RS2.getString("CARGOS.CARGO"));
                            this.obtenerFotoEditarPerfil(RS.getString("DOCUMENTO"));
                            this.LOGINHUELLA.verificarIngresos(Integer.parseInt(RS.getString("DOCUMENTO")));
                            this.LOGINHUELLA.MUNICIPIOEMPLEADO.setText(RS2.getString("MUNICIPIOS.NOMBRE"));
                            return;
                        }
                     PS2.close();
                     RS2.close();
                    }
                }
                PS.close();
                RS.close();
                JOptionPane.showMessageDialog(null,"No existe un usuario con esta huella","Mensaje", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {                
                JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
            }
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
                   this.LOGINHUELLA.LblMostrarHuella.setIcon(null); 
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
                this.LOGINHUELLA.LblMostrarHuella.setIcon(null); 
                this.LOGINHUELLA.LblMostrarHuella.setIcon(new ImageIcon(BI.getScaledInstance(
                this.LOGINHUELLA.LblMostrarHuella.getWidth(),
                this.LOGINHUELLA.LblMostrarHuella.getHeight(),Image.SCALE_DEFAULT)));
            }
            }
            PS.close();
            RS.close();
        } catch (Exception e) {
            Logger.getLogger(SQLEmpleados.class.getName()).log(Level.SEVERE,null, e);
        }
    }
}
