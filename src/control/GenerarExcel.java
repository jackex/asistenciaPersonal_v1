/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author juliancc
 */
public class GenerarExcel {

    DBConnection conexion = new DBConnection();

   public void crearExcel(String documento,String hora_entrada,String hora_salida) throws SQLException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("REPORTE DE ASISTENCIA");
        try {
            String nombreCompleto = "";
            String identificacion = "";
            String cargo = "";
            String municipio  = "";
            String consulta = "SELECT DISTINCT PRIMER_NOMBRE,SEGUNDO_NOMBRE,PRIMER_APELLIDO, SEGUNDO_APELLIDO, DOCUMENTO, "
                    + "CARGOS.CARGO, MUNICIPIOS.NOMBRE "
                    + "FROM EMPLEADOS INNER JOIN CARGOS ON EMPLEADOS.FK_CARGO = CARGOS.ID  "
                    + "INNER JOIN MUNICIPIOS ON EMPLEADOS.FK_MUNICIPIO = MUNICIPIOS.ID "
                    + "INNER JOIN HORA_INGRESO ON HORA_INGRESO.FK_EMPLEADO = EMPLEADOS.ID AND EMPLEADOS.DOCUMENTO = ?";
                    
            PreparedStatement PS2 = conexion.getConnection().prepareStatement(consulta);
            PS2.setString(1,documento);
            ResultSet RS2 = PS2.executeQuery();
            while(RS2.next()){
                nombreCompleto = RS2.getString("PRIMER_NOMBRE")+ " "+RS2.getString("SEGUNDO_NOMBRE")+ " " +RS2.getString("PRIMER_APELLIDO")+ " "+RS2.getString("SEGUNDO_APELLIDO");
                identificacion = RS2.getString("DOCUMENTO");
                cargo = RS2.getString("CARGOS.CARGO");
                municipio = RS2.getString("MUNICIPIOS.NOMBRE");
            }  
            
            CellStyle estiloTitulo = book.createCellStyle();
            estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
            estiloTitulo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuente = book.createFont();
            fuente.setFontName("Arial");
            fuente.setBold(true);
            fuente.setFontHeightInPoints((short) 12);
            estiloTitulo.setFont(fuente);

            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(estiloTitulo);
            celdaTitulo.setCellValue("FORMATO DE REPORTE PARA IMPRESION DEL NÚMERO DE HORAS PRESTADAS"
                    + " POR EMPLEADO");

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));
            String[] contenidoTitulo = new String[]{"FECHA DE INGRESO","HORA DE INGRESO","FECHA SALIDA", "HORA DE SALIDA", "HORAS TRABAJADAS", "COMENTARIOS"};

            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.TEAL.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setAlignment(HorizontalAlignment.CENTER);
            datosEstilo.setBorderTop(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            
            Row filaDatosPersonales = sheet.createRow(3);
            
            Cell celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("NOMBRE EMPLEADO");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(nombreCompleto);
            
            filaDatosPersonales = sheet.createRow(4);
            celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("DOCUMENTO");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(documento);
            
            filaDatosPersonales = sheet.createRow(5);
            celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("CARGO");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(cargo);
            
            filaDatosPersonales = sheet.createRow(6);
            celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("MUNICIPIO");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(municipio);
            
            filaDatosPersonales = sheet.createRow(7);
            celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("FECHA INICIAL");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(hora_entrada);
            
            filaDatosPersonales = sheet.createRow(8);
            celdaDatosPersonales = filaDatosPersonales.createCell(0);
            celdaDatosPersonales.setCellStyle(headerStyle);
            celdaDatosPersonales.setCellValue("FECHA FINAL");
            celdaDatosPersonales =  filaDatosPersonales.createCell(1);
            celdaDatosPersonales.setCellValue(hora_salida);

            Row filaEncabezado = sheet.createRow(10);

            for (int i = 0; i < contenidoTitulo.length; i++) {
                Cell celdaEncabezado = filaEncabezado.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(contenidoTitulo[i]);
            }

            String query = "SELECT DATE_FORMAT(HORA_INGRESO_AM,\"%Y-%m-%d\") ,DATE_FORMAT(HORA_INGRESO_AM,\"%H:%i:%s\") , "
                    + "DATE_FORMAT(HORA_SALIDA_PM,\"%Y-%m-%d\"),DATE_FORMAT(HORA_SALIDA_PM,\"%H:%i:%s\"),HORAS_TRABAJADAS_PM ,"
                    + "COMENTARIOS_PM FROM HORA_INGRESO "
                    + "INNER JOIN EMPLEADOS ON EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.DOCUMENTO = ?"
                    + "AND HORA_INGRESO.HORA_INGRESO_AM >= CONCAT(\""+hora_entrada+"\",\"T00:00:00\")"
                    + "AND HORA_INGRESO.HORA_SALIDA_PM <= CONCAT(\""+hora_salida+"\",\"T23:59:59\")";
            
            ArrayList<String> lista = new ArrayList<>();
            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            ResultSet RS = PS.executeQuery();
            int numCols = RS.getMetaData().getColumnCount();
            int filaDatos = 11;
            while (RS.next()) {
                Row FilaDatos = sheet.createRow(filaDatos);
                for (int a = 0; a < numCols; a++) {
                    Cell celdaDatos = FilaDatos.createCell(a);
                    celdaDatos.setCellStyle(datosEstilo);
                    if (a == 4) {
                        celdaDatos.setCellValue(RS.getTime(a + 1));
                        lista.add(RS.getString(a + 1));
                    }
                    celdaDatos.setCellValue(RS.getString(a + 1));
                }
                filaDatos++;
            }

            int horas = 0;
            int minutos = 0;
            int segundos = 0;
            for (int x = 0; x < lista.size(); x++) {
                String[] array = lista.get(x).split(":");
                horas += Integer.parseInt(array[0]);
                minutos += Integer.parseInt(array[1]);
                segundos += Integer.parseInt(array[2]);
            }

            int tmpsegundos = segundos;
            while (tmpsegundos >= 60) {
                minutos += (tmpsegundos / 60);
                tmpsegundos = (tmpsegundos % 60);
            }
            segundos = tmpsegundos;

            int tmpminutos = minutos;
            while (tmpminutos > 60) {
                horas += (tmpminutos / 60);
                tmpminutos = (tmpminutos % 60);
            }
            minutos = tmpminutos;
            String tiempoTotal = horas + ":" + minutos + ":" + segundos;

            Row FilaDatos = sheet.createRow(filaDatos);
            Cell celdaDatos = FilaDatos.createCell(0);
            celdaDatos.setCellStyle(headerStyle);
            celdaDatos.setCellValue("TOTAL HORAS TRABAJADAS");
            Cell celdaDatos2 = FilaDatos.createCell(1);
            celdaDatos2.setCellStyle(datosEstilo);
            celdaDatos2.setCellValue(tiempoTotal);

            for (int x = 0; x < numCols; x++) {
                sheet.autoSizeColumn(x);
            }

            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            desktopPath.replace("\\", "/");
            try (FileOutputStream fileout = new FileOutputStream(new File(desktopPath+documento+".xls"))) {
                book.write(fileout);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Excel", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Excel", JOptionPane.ERROR_MESSAGE);
        }
    }
}
