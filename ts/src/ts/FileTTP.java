/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;


import java.io.File;  // Import the File class
import java.util.Scanner;
import java.io.FileNotFoundException;
/**
 *
 * @author 8.1
 */
public class FileTTP {
    public String[] info = new String[9];
    public String tempInfo ;
    public String[] finalInfo;
    int dimension;
    int itemNumber;    
    int capacity ;   
    double minSpeed ; 
    double maxSpeed ;    
    double rentingRatio ;
    double c;
    double[] information = new double[7];
    int [][] item ;
    int[][] kota ;
    String file="berlin52_n51_bounded-strongly-corr_01.ttp";
    String file1="berlin52_n51_bounded-strongly-corr_01.ttp";
    String file2="eil51_n50_bounded-strongly-corr_01.ttp";
    String file3="pr439_n438_bounded-strongly-corr_01.ttp";
    String file4="rat783_n782_bounded-strongly-corr_01.ttp";
    String file5="kroA100_n99_bounded-strongly-corr_01.ttp";
    String file6="a280_n279_bounded-strongly-corr_01.ttp";
    String file7="eil76_n75_bounded-strongly-corr_01.ttp";
    
    public FileTTP(){
     dimension=0;
     itemNumber=0;    
     capacity=0;   
     minSpeed=0; 
     maxSpeed=0;    
     rentingRatio=0;
     c=0;
     item=new int[itemNumber][3];
     kota= new int[dimension][dimension];
}
    
    public void readFile(){
       int counter = 0;
       int cont = 0;
        try {
			Scanner scanner = new Scanner(new File(file));
			while (cont<9) {
                            info[counter] = scanner.nextLine();
				
                                counter++;
                                cont++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        counter=0;
        for (int i = 0; i < 9; i++) {
            info[i] = info[i].replaceAll("\\s", "");
           
        }
        tempInfo = info[2];
        finalInfo = tempInfo.split(":");
	this.dimension = Integer.parseInt(finalInfo[1]);
        tempInfo =info[3];
        finalInfo = tempInfo.split(":");
        this.itemNumber = Integer.parseInt(finalInfo[1]);
        tempInfo =info[4];
        finalInfo = tempInfo.split(":");
        this.capacity = Integer.parseInt(finalInfo[1]);
        tempInfo =info[5];
        finalInfo = tempInfo.split(":");
        this.minSpeed = Double.parseDouble(finalInfo[1]);
        tempInfo =info[6];
        finalInfo = tempInfo.split(":");
        this.maxSpeed = Double.parseDouble(finalInfo[1]);
        tempInfo =info[7];
        finalInfo = tempInfo.split(":");
        this.rentingRatio = Double.parseDouble(finalInfo[1]);
        this.c = (maxSpeed-minSpeed)/capacity;
        
        
        
        String[]dataKota = new String[dimension];
        String[]dataBarang = new String[itemNumber];
        try {
			Scanner scanner = new Scanner(new File(file));
			         for (int i = 0; i <11+dimension+itemNumber; i++) {
                               if(i<10){
                                   scanner.nextLine();
                               }
                               else if((i>=10)&&(i<10+dimension)){
                                  dataKota[i-10] = scanner.nextLine();
                               }
                               else if(i>10+dimension){
                                   dataBarang[i-(11+dimension)]=scanner.nextLine();
                               } else 
                                   scanner.nextLine();
                                 }
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        double [][] koordinat= new double[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
        tempInfo =dataKota[i];
        finalInfo = tempInfo.split("\\s");
         koordinat[i][0] =Double.parseDouble(finalInfo[1]);
         koordinat[i][1] =Double.parseDouble(finalInfo[2]);
        }
        
        this.item=new int[itemNumber][3];
        
        for (int i = 0; i < itemNumber; i++) {
        tempInfo =dataBarang[i];
        finalInfo = tempInfo.split("\\s");
         this.item[i][0] =Integer.parseInt(finalInfo[1]);
         this.item[i][1] =Integer.parseInt(finalInfo[2]);
         this.item[i][2] =Integer.parseInt(finalInfo[3]);
        }
       
        this.kota = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(i==j){
                    this.kota[i][j]=0;
                }
                else{
                    this.kota[i][j] =(int)Math.ceil( Math.sqrt( Math.pow((koordinat[i][0]-koordinat[j][0]),2)
                            +Math.pow((koordinat[i][1]-koordinat[j][1]),2)));
                }
            }
        }
        
        
    }
    
     public int getDimension() {
        return this.dimension;
    }
     
    public int getItemNumber() {
        return this.itemNumber;
    }
    
    public int getcapacity() {
        return this.capacity;
    }
    
    public double getMinSpeed() {
        return this.minSpeed;
    }
    
    public double getMaxSpeed() {
        return this.maxSpeed;
    }
    
    public double getRentingRatio() {
        return this.rentingRatio;
    }
    
     public double getC() {
        return this.c;
    }
    
    
}
