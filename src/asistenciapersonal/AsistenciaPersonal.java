/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asistenciapersonal;

import java.util.ArrayList;

/**
 *
 * @author juliancc
 */
public class AsistenciaPersonal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<String> lista = new ArrayList<>();

        lista.add("03:59:58");
        lista.add("01:49:54");
        lista.add("01:02:04");
        lista.add("01:36:05");
        lista.add("14:47:05");
        lista.add("07:18:50");
        lista.add("02:00:20");
        lista.add("00:00:09");
        lista.add("03:04:09");
        lista.add("00:04:01");
        lista.add("00:18:01");

        int horas = 0;
        int minutos = 0;
        int segundos = 0;
        for (int x = 0; x < lista.size(); x++) {
            String[] array = lista.get(x).split(":");
            horas += Integer.parseInt(array[0]);
            minutos += Integer.parseInt(array[1]);
            segundos += Integer.parseInt(array[2]);
        }

        int tmpsegundos = 0;
        tmpsegundos = segundos;
        while (tmpsegundos >= 60) {
                minutos += (tmpsegundos / 60);
                tmpsegundos = (tmpsegundos % 60);
        }
        segundos = tmpsegundos;
        
        int tmpminutos = 0;
        tmpminutos = minutos;
        while(tmpminutos > 60){
            horas += (tmpminutos / 60);
            tmpminutos = (tmpminutos % 60);
        }
        minutos = tmpminutos;

        String tiempoTotal = horas + ":" + minutos + ":" + segundos;
        System.out.println(tiempoTotal);
    }

}
