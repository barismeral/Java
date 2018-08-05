// @author Barış

import java.util.Scanner;







public class Program {

    public  static void  main(String[] args){

        int decimalNum,i=0;
        int[] dizi = new int[8];



        Scanner input = new Scanner(System.in);



        decimalNum = input.nextInt();

        while (i<8){

            dizi[i]=decimalNum%2;

            decimalNum=decimalNum/2;

            i++;


        }

       for (int j=7;j>=0;j--){

            System.out.print(dizi[j]+" ");

        }










    }


}
