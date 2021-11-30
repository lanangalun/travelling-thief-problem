/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

//import java.util.ArrayList;
//import java.io.File;  // Import the File class
//import java.io.IOException;  // Import the IOException class to handle errors
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.Scanner;
//import java.io.FileNotFoundException;
/**
 *
 * @author 8.1
 */
public class Ts {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
//         TODO code application logic here
        for (int loop = 0; loop < 1; loop++) {
            
        
       FileTTP FileTTP = new FileTTP();
       FileTTP.readFile();
      int[][] aa = FileTTP.kota;
       
        
        
        int jumlahKota=FileTTP.dimension;
        int beban = 0;
        int kapasitas = FileTTP.capacity;
        double rr = FileTTP.rentingRatio;
        double vmax = FileTTP.maxSpeed;
        double vmin = FileTTP.minSpeed;
        double c = (vmax-vmin)/kapasitas;
        int kota[][] = FileTTP.kota;
        int item[][] = FileTTP.item;
        int iterasi = 1;
        optimasi akhir = new optimasi();
        akhir.initialSolution(jumlahKota, beban, kapasitas, vmax, vmin, rr, c, item, kota);
        double hasilAkhir = akhir.hasilAkhir;
        double hasilItem = akhir.hasilItem;
        double hasilJarak = akhir.hasilJarak;
        int[]bawaan = akhir.bawaan;
        int[]tour = akhir.tour;
        System.out.println("");
//        System.out.println("  "+hasilItem);
//        System.out.println("  "+hasilJarak);
//        System.out.println("  "+hasilAkhir);
        
        akhir.hillClimbing(iterasi, bawaan, item, kota, kapasitas, jumlahKota, tour, vmax, c,rr);
        int[]bawaan2 = akhir.bawaan;
        int[]tour2 = akhir.tour;
        double hasilAkhir2 = akhir.hasilAkhir;
        double hasilItem2 = akhir.hasilItem;
        double hasilJarak2 = akhir.hasilJarak;
//        System.out.println("mulaibawaan----");
//        for (int var : bawaan2){
//            
//            System.out.print(var+" ");
//            }
//        System.out.println("");
//        System.out.println("mulaitour----");
//        
//        for (int var2 : tour2){
//            
//            System.out.print(var2+" ");
//            }
//        System.out.println("");
        akhir.fitnessKota2(jumlahKota, tour2, bawaan2, item, kota, vmax, c);
        int cekTour=akhir.cekKota(tour2, jumlahKota);
//        System.out.println("");
//        System.out.println("tour kota "+cekTour);
//        System.out.println("hasil item  "+hasilItem2);
//        System.out.println("hasil jarak  "+hasilJarak2);
//        System.out.println("hasil akhir  "+hasilAkhir2);
        
        
//        System.out.println(c);
//        System.out.println(vmax);
//        System.out.println(vmin);
//        System.out.println(kapasitas);
        int iterasi2 = 200000000;
        akhir.llhSelect2(iterasi2, bawaan2, item, kota, kapasitas, jumlahKota, tour2, vmax, c, rr);
        int[]bawaan3 = akhir.bawaan;
        int[]tour3 = akhir.tour;
        double hasilAkhir3 = akhir.hasilAkhir;
        double hasilItem3 = akhir.hasilItem;
        double hasilJarak3 = akhir.hasilJarak;
        
        System.out.println("mulaibawaan----");
        for (int var3 : bawaan3){
            
            System.out.print(var3+" ");
            }
        System.out.println("");
        System.out.println("mulaitour----");
        
        for (int var4 : tour3){
            
            System.out.print(var4+" ");
            }
        System.out.println("");
       
        int cekTour2=akhir.cekKota(tour3, jumlahKota);
        System.out.println("");
        System.out.println("tour kota "+cekTour2);
        System.out.println("hasil item  "+hasilItem3);
        System.out.println("hasil jarak  "+hasilJarak3);
        System.out.println("hasil akhir  "+hasilAkhir3);
        akhir.cekConstraint2(bawaan3,item,kota,kapasitas);
    }
    
        }
}
