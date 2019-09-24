/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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

    public static void main(String args[]) throws SQLException {

        GenerarExcel excel = new GenerarExcel();
        excel.crearExcel("1064430876");
    }

    public void crearExcel(String documento) throws SQLException {
        String tiempoTotal = "";
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("HORAS DE ASISTENCIA");
        try {

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

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 9));
            String[] contenidoTitulo = new String[]{"HORA DE INGRESO", "HORA DE SALIDA", "HORAS TRABAJADAS", "COMENTARIOS"};

            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
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

            Row filaEncabezado = sheet.createRow(5);

            for (int i = 0; i < contenidoTitulo.length; i++) {
                Cell celdaEncabezado = filaEncabezado.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(contenidoTitulo[i]);
            }

            String query = "SELECT HORA_INGRESO_AM, HORA_SALIDA_PM,HORAS_TRABAJADAS_PM,COMENTARIOS_PM "
                    + "FROM HORA_INGRESO INNER JOIN EMPLEADOS ON EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.DOCUMENTO = ?";
            String query2 = "SELECT CAST(CONVERT(SUM(HORA_INGRESO.HORAS_TRABAJADAS_PM),TIME) AS CHAR(20)) AS \"TIEMPO\" "
                    + "FROM HORA_INGRESO INNER JOIN EMPLEADOS ON EMPLEADOS.ID = HORA_INGRESO.FK_EMPLEADO AND EMPLEADOS.DOCUMENTO = ?";

            PreparedStatement PS;
            PS = conexion.getConnection().prepareStatement(query);
            PS.setString(1, documento);
            ResultSet RS = PS.executeQuery();
            int numCols = RS.getMetaData().getColumnCount();
            int filaDatos = 6;
            while (RS.next()) {
                Row FilaDatos = sheet.createRow(filaDatos);
                for (int a = 0; a < numCols; a++) {
                    Cell celdaDatos = FilaDatos.createCell(a);
                    celdaDatos.setCellStyle(datosEstilo);
                    if (a == 2) {
                        celdaDatos.setCellValue(RS.getTime(a + 1));
//                        tiempo.add(RS.getString(a + 1));
//                        sumarTiempo += Integer.parseInt(RS.getString(a + 1));
                    }
                    celdaDatos.setCellValue(RS.getString(a + 1));
                }
                filaDatos++;
            }
            PreparedStatement PS2;
            PS2 = conexion.getConnection().prepareStatement(query2);
            PS2.setString(1, documento);
            ResultSet RS2 = PS2.executeQuery();
            while (RS2.next()) {
                tiempoTotal = RS2.getString("TIEMPO");
            }

            Row FilaDatos = sheet.createRow(filaDatos);
            Cell celdaDatos = FilaDatos.createCell(0);
            celdaDatos.setCellStyle(datosEstilo);
            celdaDatos.setCellValue("TOTAL HORAS");
            Cell celdaDatos2 = FilaDatos.createCell(2);
            celdaDatos2.setCellStyle(datosEstilo);
            celdaDatos2.setCellValue(tiempoTotal.toString());

            for (int x = 0; x < numCols; x++) {
                sheet.autoSizeColumn(x);
            }

            FileOutputStream fileout = new FileOutputStream("archivo excel.xls");
            book.write(fileout);
            fileout.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Excel", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Excel", JOptionPane.ERROR_MESSAGE);
        }
    }
}
