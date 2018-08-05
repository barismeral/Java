Copyright [2018.07.07] [Barış Meral]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.







/**
 * @author Baris M.
 * @author Merve C.
 * @since 2018.07.07
 * @see javax.swing
 * @see java.awt
 * @version 1.1
 * @throws java.lang.InterruptedException
 * @throws java.lang.IllegalArgumentException
 * @throws java.lang.NullPointerException
 * @jdk 10
 */







package com.barismeral.screen.splashscreen;

import javax.swing.*;
import java.awt.*;



public class SplashFrame implements Runnable {

    private static JFrame frame;

    public static final short MAX_WEIGHT = 320;
    public static final short  MAX_HEIGHT = 310;

    public static final short MIN_WEIGHT = 250;
    public static final short MIN_HEIGHT = 230;

    public static final short MAX_VISIBLED_MINUTES = 10000;



private SplashFrame(){}

/**
* @param int minutes
* @param String imagePath
* @param int weight
* @param int height
* @param Boolean JProgressBar enabled ?
*/
        public static void showSplashScreen(int minituesMillis,String imagePath,int weight,int height,Boolean progressbarVisible){


         if (weight>MAX_WEIGHT||height>MAX_HEIGHT)
             throw new IllegalArgumentException("Maximum weight = 320 & Maximum height = 310 ");

         if (height<MIN_HEIGHT || weight<MIN_WEIGHT)
             throw new IllegalArgumentException("Minimum weight = 250 & Minimum height = 230");

         if (minituesMillis<=0||minituesMillis>MAX_VISIBLED_MINUTES)
             throw new IllegalArgumentException("Minimum minitues 500 millis & Maximum 10000 millis");

         if (imagePath==null  || imagePath=="") throw new  NullPointerException("File not found");








        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int windowHeight =   toolkit.getScreenSize().height;
        int windowsWeight = toolkit.getScreenSize().width;



        ImageIcon image = new ImageIcon(imagePath);






     frame = new JFrame();
     frame.setLocation((windowsWeight/2)-100,windowHeight/4);
     frame.setUndecorated(true);
     frame.setSize(weight,height);

     frame.setBackground(new Color(0,0,0,0));
     frame.getRootPane().setOpaque(false);

        JLabel iconLabel = new JLabel();


        JPanel panel2 = new JPanel(new BorderLayout());

        panel2.add(iconLabel,BorderLayout.CENTER);

        iconLabel.setIcon(image);

        panel2.setBackground(new Color(0,0,0,0));


 frame.add(panel2,BorderLayout.CENTER);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(true);
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(progressBar,BorderLayout.SOUTH);
progressBar.setBackground(new Color(0,0,0,0));

  frame.add(panel,BorderLayout.SOUTH);

        Thread thread = new Thread(new SplashFrame());
        if (!progressbarVisible)progressBar.setVisible(false);
        thread.start();


     try{

         progressBar.setValue(25);
         Thread.sleep(minituesMillis/5);
         progressBar.setValue(50);
         Thread.sleep(minituesMillis/4);
         progressBar.setValue(75);
         Thread.sleep(minituesMillis/3);
         progressBar.setValue(100);
         Thread.sleep(500);
         frame.setVisible(false);


          Runtime.getRuntime().gc();


     }
     catch (InterruptedException i){

      System.out.print(i);


     }

    }


    @Override
    public void run() {
        frame.setVisible(true);
    }
}
