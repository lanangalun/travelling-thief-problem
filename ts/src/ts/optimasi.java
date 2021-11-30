/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import java.lang.Math;
import java.util.Arrays;
/**
 *
 * @author 8.1
 */
public class optimasi {
    
    int[] tour ;
    int[] bawaan ;
    double hasilJarak;
    double hasilItem;
    double hasilAkhir;
    long waktuAwal = System.nanoTime();
    
    public void initialSolution(int jumlahKota,int itemNumber,int kapasitas,double vmax,double vmin,
    double rr, double c,int[][]item, int[][] kota){
       this.bawaan = new int[jumlahKota]; bawaan[0]=0;
       int max = jumlahKota-1;
       int min = 0;
       int range = max - min + 1;
       int rand1 = (int)(Math.random() * range) + min;
       this.tour = new int[jumlahKota+1];
       this.tour[0]=rand1;
       int lokasiKota ;
       int jarakTerdekat ;
       int kotaSelanjutnya=rand1;
       int beban = 0;
       int[]tabuList = new int[jumlahKota];
       tabuList[rand1] = 1;
       for (int i = 0; i < jumlahKota-1; i++) {
            jarakTerdekat = Integer.MAX_VALUE;
            lokasiKota = kotaSelanjutnya;
            for (int j = 0; j < jumlahKota; j++) {
                if((kota[lokasiKota][j]<jarakTerdekat)&&
                    (tabuList[j]==0)&&(lokasiKota!=j)){
                          jarakTerdekat=kota[lokasiKota][j];
                          kotaSelanjutnya=j;
                      }    
            }
            tabuList[kotaSelanjutnya]=1;
            this.tour[i+1]=kotaSelanjutnya;
        }
        this.tour[jumlahKota]= tour[0];
        
        fitnessItem(jumlahKota, bawaan, item);
        fitnessKota(jumlahKota, tour, bawaan,item,kota, vmax, c);
        fitnessTTP(hasilItem,hasilJarak,rr);
    }
    
    /**
     *
     * @param jumlahKota
     * @param bawaan
     * @param item
     */
    public void  fitnessItem(int jumlahKota, int []bawaan,int[][]item){
        this.hasilItem=0;
        for (int i = 0; i < jumlahKota; i++) {
            if(bawaan[i]!=0){
                this.hasilItem = hasilItem + item[(bawaan[i]*i)-bawaan[i]][0];
                
            }
           
        }
        
    }
    
    public void fitnessKota(int jumlahKota,int [] tour, int[] bawaan,int [][]item,int[][]kota,double vmax, double c){
        int beban = 0;
        this.hasilJarak=0;
        for (int i = 0; i < jumlahKota; i++) {
            if(bawaan[tour[i]]!=0){
                 beban = beban + item[(tour[i]*bawaan[tour[i]])-bawaan[tour[i]]][1];
            }
            
            this.hasilJarak = hasilJarak + (kota[tour[i]][tour[i+1]]/
                    (vmax-c*beban));
            
            
        }
//        System.out.println(this.hasilJarak);
        
    }
    
    public void fitnessKota2(int jumlahKota,int [] tour, int[] bawaan,int [][]item,int[][]kota,double vmax, double c){
        int beban = 0;
        this.hasilJarak=0;
        for (int i = 0; i < jumlahKota; i++) {
            if(bawaan[tour[i]]!=0){
                 beban = beban + item[(tour[i]*bawaan[tour[i]])-bawaan[tour[i]]][1];
            }
            
            this.hasilJarak = hasilJarak + (kota[tour[i]][tour[i+1]]/
                    (vmax-c*beban));
//            System.out.println(" "+(vmax-c*beban));
            
        }
//        System.out.println(this.hasilJarak);
        
    }
    
    public void fitnessTTP(double hasilItem, double hasilJarak, double rr){
        
        this.hasilAkhir = hasilItem - (hasilJarak*rr);
//        System.out.println("item "+hasilItem);
//        System.out.println("rr "+rr);
//        System.out.println("jarak rr "+hasilJarak*rr);
//        System.out.println("jarak "+hasilJarak);
    }
    
    public int cekConstraint(int[] bawaan,int[][] item, int[][]kota,int kapasitas){
        int hasilCek = 0;
        int beban =0;
        for (int i = 0; i < kota.length; i++) {
            if(bawaan[i]!=0){
                beban = beban+item[(bawaan[i]*i)-bawaan[i]][1];
            }
        }
        if(beban<=kapasitas){
            hasilCek = 1;
        }
        return hasilCek;
    }
    
    public int cekConstraint2(int[] bawaan,int[][] item, int[][]kota,int kapasitas){
        int hasilCek = 0;
        int beban =0;
        for (int i = 0; i < kota.length; i++) {
            if(bawaan[i]!=0){
                beban = beban+item[(bawaan[i]*i)-bawaan[i]][1];
            }
        }
        if(beban<=kapasitas){
            hasilCek = 1;
        }
//        System.out.println(beban);
//        System.out.println(kapasitas);
        System.out.println("kapasitas = "+kapasitas);
        System.out.println("beban = "+beban);
        System.out.println();
        return hasilCek;
    }
    
    public int cekKota(int[] tour,int jumlahKota){
        int[] cek = new int[jumlahKota+1];
        int hasil=1;
        for (int i = 0; i < jumlahKota; i++) {
            cek[tour[i]]=1;
        }
        if(tour[jumlahKota]==tour[0]){
            cek[jumlahKota]=1;
        }
        for (int i = 0; i < jumlahKota+1; i++) {
            if(cek[i]!=1){
                hasil = 0;
                break;
        }
        }  
        return hasil;
    }
    
    public void hillClimbing(int iterasi,int[] bawaan,int[][] item, int[][]kota,int kapasitas,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp;
        double fitnessAwal;
        double fitnessAkhir;
        
        for (int i = 0; i < iterasi; i++) {
            
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = (int)(Math.random() * range) + min;
            fitnessAwal = this.hasilJarak; 
//            System.out.println("  "+rand1+" dan "+rand2);
            temp = this.tour[rand1];
            this.tour[rand1] = tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=tour[0];
//            System.out.println("");
//            for (int element: this.tour) {
//            System.out.print(element+" ");
//        }
//            System.out.println("");
            fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
            fitnessAkhir=this.hasilJarak;
//            System.out.println("fitness awal ="+fitnessAwal);
//            System.out.println("fitness akhir ="+fitnessAkhir);
            if(fitnessAkhir>fitnessAwal){
            temp = this.tour[rand1];
            this.tour[rand1] = tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=tour[0];
            this.hasilJarak = fitnessAwal;
//            System.out.println("  aa"+rand1+" dan "+rand2);
            
//            for (int a: this.tour) {
//            System.out.print(a+" ");
//        }
//                System.out.println("");   
//            
//            
//                System.out.println("kurang");
            }
        }
        
        int cekK = 0;
        for (int i = 0; i < iterasi; i++) {
            
            int rand1 = (int)(Math.random() * (range-1)) + min+1;
            int rand2 = (int)(Math.random() * (range-1)) + min+1;
            fitnessAwal = this.hasilItem; 

            temp = this.bawaan[rand1];
            this.bawaan[rand1] = bawaan[rand2];
            this.bawaan[rand2] = temp;

            fitnessItem( jumlahKota,  bawaan,item);
            fitnessAkhir=this.hasilItem;
            cekK = cekConstraint(bawaan,item,kota,kapasitas);
            if((fitnessAkhir<fitnessAwal)||(cekK==0)){
            temp = this.bawaan[rand1];
            this.bawaan[rand1] = bawaan[rand2];
            this.bawaan[rand2] = temp;
           
            this.hasilItem = fitnessAwal;
             }
            cekK=0;
        }
        
         fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
        fitnessItem( jumlahKota,  bawaan,item);
        fitnessTTP( hasilItem,  hasilJarak, rr );
    }
    
    public void llh3(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int rand1 = (int)(Math.random() * range) + min;
        int rand2 = (int)(Math.random() * range) + min;
        int rentan = 0;
        double fitnessAwal = hasilAkhir;
        
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
        int[]balik = new int[akhir-awal+1];
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        this.tour[jumlahKota]=tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        
        
    }
    
    public void llh1(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        
        int max = jumlahKota-1;
        int min = 1;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int[]balik = new int[jumlahKota];
        
      
        
        for (int j = 1; j < jumlahKota-1; j++) {
        double fitnessAwal = this.hasilAkhir;
        int rand1 = (int)(Math.random() * range) + min;    
        int rand2 = j;
         
        if(rand1==rand2){
            break;
        }
        
        else if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
       
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = this.tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        this.tour[jumlahKota]=tour[0];
        
       fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
       fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        else{
            break;
        }
        }
        
    }
    
    public void llh6(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        
        int max = jumlahKota-1;
        int min = 1;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int[]balik = new int[jumlahKota];
        int temp=0;
      
        
        for (int j = 1; j < jumlahKota; j++) {
        double fitnessAwal = this.hasilAkhir;
        int rand1 = (int)(Math.random() * range) + min;    
        int rand2 = j;
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
       
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = this.tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        this.tour[jumlahKota]=this.tour[0];
        temp=this.tour[awal+1];
        this.tour[awal+1]=this.tour[awal];
        this.tour[awal]=temp;
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        else{
            break;
        }
        }
        
    }
    
    
    
    public void llh5(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int[]balik = new int[jumlahKota];
        int swap=0;
      
        
        for (int j = 0; j < jumlahKota; j++) {
        double fitnessAwal = this.hasilAkhir;
        int rand1 = (int)(Math.random() * range) + min;    
        int rand2 = j;
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
       
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = this.tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        swap=this.tour[awal];
        this.tour[awal]=this.tour[akhir];
        this.tour[akhir]=swap;
        this.tour[jumlahKota]=this.tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        else{
            break;
        }
        }
        
    }
    
    
    public void llh4(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        double fitnessAwal = this.hasilAkhir;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = (int)(Math.random() * range) + min;
           

            temp = this.tour[rand1];
            this.tour[rand1] = this.tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=this.tour[0];

            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            double fitnessAkhir=this.hasilAkhir;
//            System.out.println("hasil awal "+fitnessAwal);
//            System.out.println("hasil akhir "+fitnessAkhir);
         
       
            if(fitnessAkhir<fitnessAwal){
            this.tour = Arrays.copyOf(copy,copy.length);
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        
            }
        
        
    }
    
    public void llh11(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-2;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        
        int[]copy =  Arrays.copyOf(tour,tour.length);
        for (int i = 0; i < jumlahKota/10; i++) {
            
        
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = (int)(Math.random() * range) + min;
           

            temp = this.tour[rand1];
            this.tour[rand1] = this.tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=this.tour[0];
            
            }

            fitnessKota( jumlahKota,this.tour,this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            
//            System.out.println("hasil awal "+fitnessAwal);
//            System.out.println("hasil akhir "+fitnessAkhir);
         
       
            
        
        
    }
    
    public void llh2(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        double fitnessAwal = this.hasilAkhir;
            for (int i = 0; i < jumlahKota; i++) {
            
        
            
            int rand2 = (int)(Math.random() * range) + min;
            if(rand2==i){
                
                i++;
                if(i==jumlahKota){
                    break;
                }
            }
            int rand1 = i;

            temp = this.tour[rand1];
            this.tour[rand1] = this.tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=this.tour[0];

            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            double fitnessAkhir=this.hasilAkhir;
//            System.out.println("hasil awal "+fitnessAwal);
//            System.out.println("hasil akhir "+fitnessAkhir);
         
       
            if(fitnessAkhir<fitnessAwal){
            this.tour = Arrays.copyOf(copy,copy.length);
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
        
            }
            else{
                 fitnessAwal=this.hasilAkhir;
            }
        
            }
    }
    
    public void llh7(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        double fitnessAwal = this.hasilAkhir;
        int counter =0;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        while(counter<=5){
            
        
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = (int)(Math.random() * range) + min;
//            int rand1 = 2;
//            int rand2 =5;
            
            temp = this.tour[rand1];
            this.tour[rand1] = this.tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=this.tour[0];

            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            double fitnessAkhir=this.hasilAkhir;
//            System.out.println("hasil awal "+fitnessAwal);
//            System.out.println("hasil akhir "+fitnessAkhir);
            
       
            if(fitnessAkhir<fitnessAwal){
            this.tour = Arrays.copyOf(copy,copy.length);
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
//          
            counter =counter+1;
        
            }
            else{
                counter = 0;
            }
    }
//        for (int i = 0; i < tour.length; i++) {
//            System.out.print(tour[i]+" ");
//        }
        
    }
    
    public void llh8(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int[]balik = new int[jumlahKota];
        int swap=0;
      
        
        for (int j = 0; j < jumlahKota; j++) {
        double fitnessAwal = this.hasilAkhir;
        int rand1 = (int)(Math.random() * range) + min;    
        int rand2 = j;
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
       
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = this.tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        swap=this.tour[awal];
        this.tour[awal]=this.tour[akhir];
        this.tour[akhir]=swap;
        this.tour[jumlahKota]=this.tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        else{
            copy =  Arrays.copyOf(this.tour,tour.length);
            fitnessAwal=this.hasilAkhir;
        }
        }
        
    }
    
    public void llh9(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        
        for (int i = jumlahKota-2; i <0 ; i--) {
            double fitnessAwal = this.hasilAkhir;
        
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = i;
           

            temp = this.tour[rand1];
            this.tour[rand1] = tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=tour[0];

            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            double fitnessAkhir=this.hasilAkhir;
//            System.out.println("hasil awal "+fitnessAwal);
//            System.out.println("hasil akhir "+fitnessAkhir);
         
       
            if(fitnessAkhir<fitnessAwal){
            this.tour = Arrays.copyOf(copy,copy.length);
            temp = this.tour[rand1];
            this.tour[rand1] = tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=tour[0];
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        
            }
            else{
            copy =  Arrays.copyOf(this.tour,tour.length);
            fitnessAwal=this.hasilAkhir;
            }
        }
        
    }
    
    public void llh10(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int[]balik = new int[jumlahKota];
        int swap=0;
      
        
        for (int j = jumlahKota-2; j <0 ; j--) {
        double fitnessAwal = this.hasilAkhir;
        int rand1 = (int)(Math.random() * range) + min;    
        int rand2 = j;
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
       
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = this.tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        
        this.tour[jumlahKota]=this.tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;
//        System.out.println("hasil awal = "+fitnessAwal);
//        System.out.println("hasil akhir = "+fitnessAkhir);
        
        
        
        if(fitnessAwal>fitnessAkhir){
           this.tour = Arrays.copyOf(copy,copy.length);
           fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
           
        }
        else{
            copy =  Arrays.copyOf(this.tour,tour.length);
            fitnessAwal=this.hasilAkhir;
        }
        }
        
    }
    
    public void tpo(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(this.tour,tour.length);
        int awal=0;
        int akhir=0;
        int swap=0;
        double gbest = this.hasilAkhir;
        int iterasi = 10;
        int randI = 1;
        
        for (int i = 0; i < iterasi; i++) {
            int rand1 = (int)(Math.random() * range) + min;    
            int rand2 = (int)(Math.random() * range) + min;
            swap = this.tour[rand1];
            this.tour[rand1]=this.tour[rand2];
            this.tour[rand2]=swap;
            this.tour[jumlahKota]=this.tour[0];
            fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            if(gbest<this.hasilAkhir){
                gbest=this.hasilAkhir;
                copy =  Arrays.copyOf(this.tour,tour.length);
               
            }
            for (int j = 0; j < randI; j++) {
                int rand3=(int)(Math.random() * (range-2)) + 1;
                
                for (int k = rand3; k < jumlahKota-2; k++) {
                    swap = this.tour[k];
                    this.tour[k]=this.tour[k+1];
                    this.tour[k+1]=swap;
                    fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
                    fitnessTTP( hasilItem,  hasilJarak, rr );
                    if(gbest<this.hasilAkhir){
                        gbest=this.hasilAkhir;
                        copy =  Arrays.copyOf(this.tour,tour.length);
                        break;
                    }
                }
            }
    
        }
        this.tour=Arrays.copyOf(copy,copy.length);
        fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
        fitnessTTP( hasilItem,  hasilJarak, rr );
        
    }
    
    public void llhItem1(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr, int kapasitas){
        int max = jumlahKota-1;
        int min = 1;
        int range = max - min + 1;
        int cekBeban;
        int rand1 = (int)(Math.random() * range) + min;
        double fitnessAwal = this.hasilAkhir;
        double fitnessAkhir;
        if(this.bawaan[rand1]==0){
            this.bawaan[rand1]=1;
            cekBeban=cekConstraint(this.bawaan,item,kota, kapasitas);
            if(cekBeban==0){
                this.bawaan[rand1]=0;
            }
            else{
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            fitnessAkhir=this.hasilAkhir;
            if(fitnessAwal>fitnessAkhir){
                this.bawaan[rand1]=0;
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            }
            }
        }
        else{
             this.bawaan[rand1]=0;
            cekBeban=cekConstraint(bawaan,item,kota, kapasitas);
            if(cekBeban==0){
                this.bawaan[rand1]=1;
            }
            else{
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            fitnessAkhir=this.hasilAkhir;
            if(fitnessAwal>fitnessAkhir){
                this.bawaan[rand1]=1;
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            }
            }
        }
        
    }
    
    public void llhItem2(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr, int kapasitas){
        int max = jumlahKota-1;
        int min = 1;
        int range = max - min + 1;
        int cekBeban;
        for (int  i= 0;  i<bawaan.length; i++) {
       
        this.bawaan[i]=0;
        }
        
            fitnessKota( jumlahKota,this.tour,  bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            
        
    }
    
    public void llhSwap(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int temp = 0;
        double fitnessAwal = this.hasilAkhir;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        
            int rand1 = (int)(Math.random() * range) + min;
            int rand2 = (int)(Math.random() * range) + min;
           

            temp = this.tour[rand1];
            this.tour[rand1] = this.tour[rand2];
            this.tour[rand2] = temp;
            this.tour[jumlahKota]=this.tour[0];

            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
            
            double fitnessAkhir=this.hasilAkhir;

         
        
        
    }
    
    public void llhInverse(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int rand1 = (int)(Math.random() * range) + min;
        int rand2 = (int)(Math.random() * range) + min;
        int rentan = 0;
        double fitnessAwal = hasilAkhir;
        
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
        int[]balik = new int[akhir-awal+1];
        for (int i = 0; i <= akhir-awal; i++) {
            balik[i] = tour[akhir-i];
           
        }
     
        
        for (int i = 0; i <= akhir-awal; i++) {
            this.tour[awal+i]=balik[i];
            
        }
        this.tour[jumlahKota]=tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;      
        
    }
    
     public void llhInsert(int[] bawaan,int[][] item, int[][]kota,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = jumlahKota-1;
        int min = 0;
        int range = max - min + 1;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int awal=0;
        int akhir=0;
        int rand1 = (int)(Math.random() * range) + min;
        int rand2 = (int)(Math.random() * range) + min;
        int swap = 0;
        double fitnessAwal = hasilAkhir;
        
        
        if(rand1==rand2){
            rand1= jumlahKota/2;
            rand2= rand1+1;
        }
        
        if(rand1>rand2){
             awal = rand2;
             akhir = rand1;
             
        }
        else {
            awal = rand1;
            akhir = rand2;
            
        }
        
        for (int k = awal; k < akhir-1; k++) {
                    swap = this.tour[k];
                    this.tour[k]=this.tour[k+1];
                    this.tour[k+1]=swap;
                }
        
        this.tour[jumlahKota]=tour[0];
        
        fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
        fitnessTTP( this.hasilItem,  this.hasilJarak, rr );
        
        double fitnessAkhir = this.hasilAkhir;      
        
    }
    
    public void llhSelect2(int iterasi,int[] bawaan,int[][] item, int[][]kota,
        int kapasitas,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = 3;
        int min = 1;
        int range = max - min + 1;
        double fitness = this.hasilAkhir;
        double tempFitness = this.hasilAkhir;
        int counter =0;
        long waktuAkhir;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int[]copyitem =  Arrays.copyOf(bawaan,bawaan.length);
        int[]copy2 =  Arrays.copyOf(tour,tour.length);
        int[]copyitem2 =  Arrays.copyOf(bawaan,bawaan.length);
        double bestfitness=0;
        int cekcek=0;
        double bestItem=0;
        double bestKota=0;
        double bestKapasitas;
        double p;
        double r;
        double cool = (((16000*(Math.sqrt(jumlahKota)))-1)/(16000*(Math.sqrt(jumlahKota))));
        double cool2 = (((12000*(Math.sqrt(jumlahKota)))-1)/(12000*(Math.sqrt(jumlahKota))));
        
        for (double i = 1000; i > 0; i=i-0.003) {
            
        
            System.out.println(fitness);
           
            
         waktuAkhir=System.nanoTime();
        if(((waktuAkhir-waktuAwal)/1000000000)>600){
            break;
        }
        
        
        int rand = (int)(Math.random() * range) + min;
        
        switch(rand)
      {
	 case 1:
           copy =  Arrays.copyOf(this.tour,tour.length);
           copyitem =  Arrays.copyOf(this.bawaan,bawaan.length);
           tempFitness = this.hasilAkhir;
           llhSwap(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);  
           llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 case 2:
           copy =  Arrays.copyOf(this.tour,tour.length);
           copyitem =  Arrays.copyOf(this.bawaan,bawaan.length);
           tempFitness = this.hasilAkhir;
           llhInsert(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr); 
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 case 3:
           copy =  Arrays.copyOf(this.tour,tour.length);
           copyitem =  Arrays.copyOf(this.bawaan,bawaan.length);
           tempFitness = this.hasilAkhir;
           llhInverse(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 default:
	   System.out.println("Default ");
             }
           
        fitness = this.hasilAkhir;

        if(fitness<tempFitness){
        p=Math.exp((-(tempFitness-fitness))/(i));
        r= Math.random();
        if(r>p){
         
            this.tour=copy;
            this.bawaan=copyitem;
            fitnessKota( jumlahKota,this.tour,  this.bawaan,item,kota, vmax,  c);
            fitnessItem( jumlahKota, this.bawaan,item);
            fitnessTTP( hasilItem,  hasilJarak, rr );
            
        }

        }
  
        }
    }
    
    public void llhSelect(int iterasi,int[] bawaan,int[][] item, int[][]kota,
        int kapasitas,int jumlahKota,int[]tour,double vmax,double c,double rr){
        int max = 10;
        int min = 1;
        int range = max - min + 1;
        double fitness = this.hasilAkhir;
        int counter =0;
        long waktuAkhir;
        int[]copy =  Arrays.copyOf(tour,tour.length);
        int[]copyitem =  Arrays.copyOf(bawaan,bawaan.length);
        double bestfitness=0;
        int cekcek=0;
        double bestItem=0;
        double bestKota=0;
        double bestKapasitas;
        for (int i = 0; i < iterasi; i++) {
        if(i%10==0){
            System.out.println(fitness);
        }    
            
        waktuAkhir=System.nanoTime();
        if(((waktuAkhir-waktuAwal)/1000000000)>60){
            System.out.println("bestfitness = "+ bestfitness);
            if(bestfitness>fitness){ 
               System.out.println("best bawaan ----");
                for (int var6 : copyitem){
                    System.out.print(var6+" ");
                }
                        System.out.println("");
                for (int var7 : copy){
                    System.out.print(var7+" ");
                }
                        System.out.println("");
                        System.out.println("----------");
                        int cekcek2 = cekKota(copy,jumlahKota);
                        System.out.println("cek kota = "+cekcek2);
                        System.out.println("hasil item  "+bestItem);
                        System.out.println("hasil jarak  "+bestKota);
                        System.out.println("hasil akhir  "+bestfitness);
                        cekConstraint2(copyitem,item,kota,kapasitas);
            }
            break;
        }
        
        
        int rand = (int)(Math.random() * range) + min;
        
        switch(rand)
      {
	 case 1:
           llh1(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);  
           llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 case 2:
           llh2(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr); 
	  llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 case 3:
           llh3(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);
	  llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
	 case 4:
           llh4(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);  
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
         case 5:
           llh5(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr); 
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
         case 6:
           llh6(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr); 
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;  
         case 7:
           llh7(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);  
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
         case 8:
           llh8(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);  
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
         case 9:
           llh9(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr); 
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;
         case 10:
           llh10(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);
	   llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
	   break;  
	 default:
	   System.out.println("Default ");
             }
             
        fitness=this.hasilAkhir;
        
//        if(fitness==this.hasilAkhir){
//            counter++;
//    
//        }
//        else if(fitness<this.hasilAkhir){
//            counter = 1;
//            fitness=this.hasilAkhir;
//        }
//
//        if((counter%12)==0){
//            tpo(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);
//             llhItem1( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
//    
//            if(this.hasilAkhir>fitness){
//                counter = 1;
//                
//                fitness=this.hasilAkhir;
//            }
//            else{
//                counter++;
//            }
//        }
//        
//        if((counter%501)==0){
//            
//             if(fitness>bestfitness){
//                bestfitness=fitness;
//                copy =  Arrays.copyOf(this.tour,tour.length);
//                copyitem =  Arrays.copyOf(this.bawaan,bawaan.length);
//                 bestItem=this.hasilItem;
//                 bestKota=this.hasilJarak;
//                
//            }
//            llh11(this.bawaan,item, kota, jumlahKota,this.tour, vmax, c, rr);
////            llhItem2( this.bawaan, item, kota, jumlahKota,this.tour, vmax, c, rr, kapasitas);
//            fitness=this.hasilAkhir;
//            counter=0;
//           
//        }
//        
//        
//            cekcek = cekKota(this.tour,jumlahKota);
//            if(cekcek==0){
//                break;
//            }
            
            
        }
    }
}
