/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author juliancc
 */
public class CrearPDF {
    public Document doc;
    public Table tabla;
        public Table TablaDatosEmpleado;
    public PdfFont helvetica;
    PdfFont helveticaBold;
    PdfDocument pdfdocument;
    
    public void montarPDF(String path) throws IOException{    
            PdfWriter writer = new PdfWriter(path);
            pdfdocument = new PdfDocument(writer);            
            doc = new Document(pdfdocument,PageSize.A4.rotate());
            doc.setMargins(30, 10, 10, 10);
            helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);
            helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);            
            tabla = new Table(new float[]{5,5,5,10});
            tabla.setWidthPercent(100);
    }
    
    public void agregarDatos(Table tabla, String data,PdfFont font, boolean isHeader){
        StringTokenizer tokenizer = new StringTokenizer(data,";");
        while(tokenizer.hasMoreTokens()){
            if(isHeader){
                tabla.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }else{
                tabla.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }    
        public void agregarDatosempleado(Table tabla, String data, boolean isHeader){
        StringTokenizer tokenizer = new StringTokenizer(data,";");
        while(tokenizer.hasMoreTokens()){
                if(isHeader){
                tabla.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken())));
            }else{
                tabla.addCell(new Cell().add(new Paragraph(tokenizer.nextToken())));
            }
        }
    }
        public void montarTablaDatosEmpleado(String data) throws IOException{        
            TablaDatosEmpleado = new Table(new float[]{5,10,5,5,5});
            TablaDatosEmpleado.setWidthPercent(100);
            this.agregarDatosempleado(TablaDatosEmpleado, data, true);
    }
}
