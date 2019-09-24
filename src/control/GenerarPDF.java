/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package control;
//
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import java.io.FileOutputStream;
//
///**
// *
// * @author juliancc
// */
//public class GenerarPDF {
//    
//    private Font FuenteBold = new Font(Font.FontFamily.COURIER,12,Font.BOLD);
//    private Font FuenteNormal = new Font(Font.FontFamily.COURIER,12,Font.NORMAL);
//    
//
//    public GenerarPDF(String titulo, String bodyInfo,String rutaSalida){
//        try{
//        Document doc = new Document(PageSize.A1);
//        PdfWriter.getInstance(doc, new FileOutputStream(rutaSalida));
//        doc.open();
//        doc.add(getHeader(titulo));
//        doc.add(getBody(bodyInfo));
//        doc.close();
//        }catch(Exception e){
//            System.out.println("Error " + e);
//        }
//    }
//    
//    private Paragraph getHeader(String texto){
//        Paragraph p = new Paragraph();
//        p.setAlignment(Element.ALIGN_CENTER);
//        Chunk c = new Chunk();
//        c.append(texto);
//        c.setFont(FuenteBold);
//        p.add(c);
//        return p;
//    }
//    
//    private Paragraph getBody(String texto){
//        Paragraph p = new Paragraph();        
//        p.setAlignment(Element.ALIGN_UNDEFINED);
//        Chunk c = new Chunk();
//        c.append(texto);
//        c.setFont(FuenteNormal);
//        p.add(c);
//        return p;
//    }
//    
//}
