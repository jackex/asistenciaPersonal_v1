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
import vista.EditarEmpleados;
/**
 *
 * @author juliancc
 */
public class HuellaDigitalEditarEmpleados {
    
    DBConnection conexion = new DBConnection();
    protected DPFPCapture LECTOREDITAR = DPFPGlobal.getCaptureFactory().createCapture();
    public DPFPEnrollment RECLUTADOR = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    public DPFPVerification VERIFICADOR = DPFPGlobal.getVerificationFactory().createVerification();
    protected DPFPTemplate TEMPLATE;
    protected static String TEMPLATE_PROPERTY = "TEMPLATE";
    protected DPFPFeatureSet FEATURESINSCRIPCION;
    protected DPFPFeatureSet FEATUREVERIFICACION;    
    EditarEmpleados EMPLEADOS;
    public String documento;
 
    
    public HuellaDigitalEditarEmpleados(EditarEmpleados empleado){
    this.EMPLEADOS = empleado;
    }
    
    public HuellaDigitalEditarEmpleados(){}
    
    public void start(){
        LECTOREDITAR.startCapture();
        mostrarMensaje("Lector de huella activado");
    }
    
    public void stop(){
        LECTOREDITAR.stopCapture();
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
        LECTOREDITAR.addDataListener(new DPFPDataAdapter(){
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
        
        LECTOREDITAR.addReaderStatusListener(new DPFPReaderStatusAdapter(){
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
        
        LECTOREDITAR.addSensorListener(new DPFPSensorAdapter(){
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
        LECTOREDITAR.addErrorListener(new DPFPErrorAdapter(){
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
          DPFPTemplate old = this.TEMPLATE;
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
                          this.EMPLEADOS.cerrarVentana();
                          this.EMPLEADOS.HUELLA2 = this;
                          consultarHuella(this.documento);                           
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
         if(documento != null){
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
                        this.EMPLEADOS.DETECCIONHUELLA.setEnabled(true);
                         this.EMPLEADOS.botonActualizarEmpleado.setEnabled(true);
                         this.EMPLEADOS.BTNBUSCARIMAGEN.setEnabled(true);
                        //return true;
                    }else{
                        this.EMPLEADOS.DETECCIONHUELLA.setEnabled(true);
                        JOptionPane.showMessageDialog(null,"No existe un registro de huella para este usuario","Mensaje",JOptionPane.ERROR_MESSAGE);
                    }                    
                }
                PS.close();
                RS.close();
            } catch (SQLException e) {                
                JOptionPane.showMessageDialog(null,e.getMessage(),"Mensaje",JOptionPane.ERROR_MESSAGE);
            }   
         }else{
             JOptionPane.showMessageDialog(null,"Debe digitar el documento del empleado","Mensaje",JOptionPane.ERROR_MESSAGE);
         }
   }
}
