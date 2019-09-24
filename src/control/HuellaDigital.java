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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import vista.AgregarEmpleados;

/**
 *
 * @author juliancc
 */
public class HuellaDigital {
    
    DBConnection conexion = new DBConnection();
    //SQLEmpleados SQL = new SQLEmpleados();
    protected DPFPCapture LECTOR = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment RECLUTADOR = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    public DPFPVerification VERIFICADOR = DPFPGlobal.getVerificationFactory().createVerification();
    protected DPFPTemplate TEMPLATE;
    protected static String TEMPLATE_PROPERTY = "TEMPLATE";
    protected DPFPFeatureSet FEATURESINSCRIPCION;
    protected DPFPFeatureSet FEATUREVERIFICACION;
    public String documento;
    AgregarEmpleados EMPLEADOS;
 
    
    public HuellaDigital(AgregarEmpleados empleado){
    this.EMPLEADOS = empleado;
    }
    
    public HuellaDigital(){}
    
    public void start(){
        LECTOR.startCapture();
        mostrarMensaje("Lector de huella activado");
    }
    
    public void stop(){
    LECTOR.stopCapture();
    mostrarMensaje("Lector de huella desactivado");
    }
    
    public void mostrarMensaje(String texto){
        EMPLEADOS.mensajeHuella.append(texto + "\n");     
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
        EMPLEADOS.LblMostrarHuella.setIcon(new ImageIcon(image.getScaledInstance(
                EMPLEADOS.LblMostrarHuella.getWidth(), 
                EMPLEADOS.LblMostrarHuella.getHeight(), Image.SCALE_DEFAULT)));
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
                  Image imagen  = this.crearImagenHuella(sample);
                  this.dibujarHuella(imagen);
                  
              } catch (DPFPImageQualityException e) {
                  JOptionPane.showMessageDialog(null,e.getMessage());
              }
              finally{
                  this.estadoHuellas();
                  switch(this.RECLUTADOR.getTemplateStatus()){
                      case TEMPLATE_STATUS_READY:
                          this.stop();
                          this.setTemplate(this.RECLUTADOR.getTemplate());
                          mostrarMensaje("La plantilla de la huella ha sido creada".toUpperCase());
                          this.EMPLEADOS.HUELLA2 = this;
                          this.EMPLEADOS.ACTIVARHUELLA.setEnabled(true);
//                          if(this.consultarHuellaNuevoEmpleado() == false){
                            this.EMPLEADOS.botonAgregarEmpleado.setEnabled(true);
                            this.EMPLEADOS.BotonFoto.setEnabled(true);
                            this.EMPLEADOS.cerrarVentana();
//                          }
                          break;
                          
                      case TEMPLATE_STATUS_FAILED:
                          this.RECLUTADOR.clear();
                          this.stop();
                          this.estadoHuellas();
                          this.setTemplate(null);
                          this.start();
                          JOptionPane.showMessageDialog(null,"La plantilla de la huella no pudo ser creada, repita el proceso");
                          break;
                  }
              }
          }
      }   
      
      
     public void consultarHuella(String documento){  
            String query = "SELECT HUELLA FROM EMPLEADOS WHERE DOCUMENTO = ?";
            PreparedStatement PS;
            ResultSet RS;
            try { 
                PS = conexion.getConnection().prepareStatement(query);
                PS.setString(1, documento);
                RS = PS.executeQuery();
                while(RS.next()){                    
                    byte templateBuffer[] = RS.getBytes("HUELLA");
                    DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                    setTemplate(referenceTemplate);
                    DPFPVerificationResult result = VERIFICADOR.verify(FEATUREVERIFICACION, getTemplate());
                    if(result.isVerified()){
                            JOptionPane.showMessageDialog(null,"Ya existe un registro de huella para este usuario","Mensaje",JOptionPane.ERROR_MESSAGE);
                        //return true;
                    }else{                        
                        this.EMPLEADOS.botonAgregarEmpleado.setEnabled(true);
                        this.EMPLEADOS.BotonFoto.setEnabled(true);
                    }                    
                }
                PS.close();
                RS.close();
            } catch (SQLException e) {                
                JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
            }
    }
     
     
       public boolean consultarHuellaNuevoEmpleado(){
            String query = "SELECT HUELLA FROM EMPLEADOS";
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
                       JOptionPane.showMessageDialog(null,"¡Ya existe un empleado registrado con esta huella!","Mensaje",JOptionPane.ERROR_MESSAGE);
                       return true;
                    }                 
                }
                PS.close();
                RS.close();
            } catch (SQLException e) {                
                JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
            }            
        return false;
   }
}
