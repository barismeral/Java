/*
 * Copyright 1995-2018 IntelliCode, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by IntelliCode in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 *
 *
 * Please contact IntelliCode, Inc., 34692 Uskudar, Istanbul,
 * Turkiye or visit www.intellicode.com if you need additional information or
 * have any questions.
 */

/**
        * @author Merve
        * @author Barış
        * @see java.awt;
        * @see javax.swing;
        * @since 28.05.2018
        * @version 1.0.0
        * jdk version 10
        *
        *
        *
        */



package baris;

//import com.barismeral.screen.splashscreen.SplashFrame;

import com.barismeral.screen.splashscreen.SplashFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.Cursor;
import java.io.*;
import java.net.*;


public class NotePad extends JFrame implements ActionListener,Runnable {


    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu dosya, duzen, bicim, yardim;
    private JMenuItem yeni, ac, kaydet, farkliKaydet, cikis;
    private JMenuItem kes, kopyala, yapistir, tumunuSec;
    private JMenuItem metinRengi,metinTipi;
    private JMenuItem notePadJHakkinda;
    private ImageIcon icon = new ImageIcon("src\\res\\Notepad.png");
    private JScrollPane scrollPane;
    private JPanel panel;

    boolean dosyaAcildi = false,kaydedildi=false;

    JFontDialog fontDialog;
    boolean update=false;

    public NotePad() {

        this.setLayout(null);
        this.setIconImage(icon.getImage());
        this.setTitle("NotePadJ");
        this.setSize(640, 360);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 360));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                 if (textArea.getText().isEmpty()){

                    System.exit(0);
                }
                 else if (kaydedildi && !update) {
                    System.exit(0);
                }


                else {

                    JOptionPane.showMessageDialog(null, "Lütfen Çalışmayı Kaydedin.", "Çıkış Uyarısı", 2);

                    try {
                        Save();
                    }
                    catch (IOException o){
                        JOptionPane.showMessageDialog(null,o.getMessage(),"Kayıt Başarısız",1);
                    }

                }
            }
        });


        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        this.setContentPane(panel);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.TRUETYPE_FONT, 16));
        textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        textArea.setBounds(1, 1, this.getWidth() - 20, this.getHeight() - 20);
        textArea.setCursor(Cursor.getDefaultCursor());
        textArea.setLineWrap(true);
        textArea.setEditable(true);


        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update=true;


            }

            @Override
            public void removeUpdate(DocumentEvent e) {

             update=true;

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                  update=true;


            }
        });
        panel.add(textArea, BorderLayout.CENTER);
        menuBar = new JMenuBar();

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getContentPane().add(scrollPane);
        dosya = new JMenu("Dosya");
        duzen = new JMenu("Düzen");
        bicim = new JMenu("Biçim");
        yardim = new JMenu("Yardım");

        dosya.addActionListener(this::actionPerformed);
        duzen.addActionListener(this::actionPerformed);
        bicim.addActionListener(this::actionPerformed);
        yardim.addActionListener(this::actionPerformed);

        menuBar.add(dosya);
        menuBar.add(duzen);
        menuBar.add(bicim);
        menuBar.add(yardim);

        yeni = new JMenuItem("Yeni");


        JMenuItem[] dosyaItems = {yeni = new JMenuItem("Yeni"),ac = new JMenuItem("Aç"),
                kaydet = new JMenuItem("Kaydet"), farkliKaydet = new JMenuItem("Farklı Kaydet"),
                cikis = new JMenuItem("Çıkış")};

        JMenuItem[] duzenItems = { kes = new JMenuItem("Kes"),
                kopyala = new JMenuItem("Kopyala"), yapistir = new JMenuItem("Yapıştır"),
                tumunuSec = new JMenuItem("Tümünü Seç")};

        bicim.add(metinRengi = new JMenuItem("Metin Rengi"));
        bicim.add(metinTipi = new JMenuItem("Metin Tipi"));


        yardim.add(notePadJHakkinda = new JMenuItem("NotePadJ Hakkında"));
        metinRengi.addActionListener(this::actionPerformed);
        metinTipi.addActionListener(this::actionPerformed);

        notePadJHakkinda.addActionListener(this::actionPerformed);


        for (JMenuItem i : dosyaItems) {

            dosya.add(i);
            i.addActionListener(this::actionPerformed);

        }
        for (JMenuItem j : duzenItems) {

            duzen.add(j);
            j.addActionListener(this::actionPerformed);

        }

        this.setJMenuBar(menuBar);


    }

           File files = null;

    @Override
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == ac) {
            try {
                Open();

            }catch (IOException ioE) {
                JOptionPane.showMessageDialog(this, ioE.getMessage(),"Dosya Açılamadı" ,1);
            }
        }


        else if (e.getSource() == kaydet) {



            if (!kaydedildi){

                try {
                    Save();
                    JOptionPane.showMessageDialog(this,"Kaydedildi","Başarılı",1);
                    update=false;

                }catch (IOException oE){
                    JOptionPane.showMessageDialog(this,oE.getMessage(),"Dosya Kaydedilemedi",1);
                }
            }
            else {

                try{

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(files));
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    JOptionPane.showMessageDialog(this,"Kaydedildi","Başarılı",1);
                    update=false;

                }
                catch (IOException ioo){
                    JOptionPane.showMessageDialog(this,ioo.getMessage(),"Kaydedilemedi",1);

                }

            }


        }


        else if (e.getSource() == farkliKaydet) {

            try {
                Save();
                update=false;
            }catch (IOException oE){
                JOptionPane.showMessageDialog(this,oE.getMessage(),"Dosya Kaydedilemedi",1);
            }



        }


        else if (e.getSource() == cikis) {

            if (kaydedildi && !update) {
                System.exit(0);
            }
            else {

                JOptionPane.showMessageDialog(this, "Lütfen Çalışmayı Kaydedin.", "Çıkış Uyarısı", 2);

            }

        }


        else if (e.getSource() == yeni) {


            textArea.setText(null);
            kaydedildi = false;

        } else if (e.getSource() == kes)
            textArea.cut();

        else if (e.getSource() == kopyala)
            textArea.copy();
        else if (e.getSource() == tumunuSec)
            textArea.selectAll();

        else if (e.getSource() == metinRengi) {


            textArea.setForeground(JColorChooser.showDialog(null, "Yazı Tipi Rengi", Color.BLACK));


        } else  if (e.getSource()==metinTipi){

         fontDialog = new JFontDialog();
          fontDialog.frame.setVisible(true);

          fontDialog.getOkButton().addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  textArea.setFont(fontDialog.getFont());

                  fontDialog.frame.dispose();
              }
          });

       }






        else if (e.getSource() == notePadJHakkinda) {

            JFrame hakkinda = new JFrame();
            hakkinda.setTitle("Hakkında");
            hakkinda.setLocationRelativeTo(this);

            hakkinda.setLayout(null);
            hakkinda.setResizable(false);
            hakkinda.setBackground(Color.GRAY);
            hakkinda.setSize(240, 250);
            JSeparator sperator = new JSeparator();
            sperator.setLocation(25, 1);
            sperator.setBounds(5, 100, 225, 1);
            hakkinda.add(sperator);
            hakkinda.setVisible(true);

            JLabel text = new JLabel("NotePadJ");
            JLabel text2 = new JLabel("Author Barış Meral");
            JLabel text3 = new JLabel("Since 28.04.2018");
            JLabel text4 = new JLabel("Version 1.0.0");

            JLabel linkLabel = new JLabel("https://github.com/barismerall");
            linkLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            linkLabel.setForeground(Color.BLUE);
            linkLabel.setBounds(10, 50, 230, 20);
            hakkinda.add(linkLabel);


            text.setFont(new Font("Arial", Font.BOLD, 14));
            text2.setFont(new Font("Arial", Font.BOLD, 14));
            text3.setFont(new Font("Arial", Font.BOLD, 14));
            text4.setFont(new Font("Arial", Font.BOLD, 14));

            text.setBounds(10, 100, 200, 50);
            text2.setBounds(10, 120, 200, 50);
            text3.setBounds(10, 140, 200, 50);
            text4.setBounds(10, 160, 200, 50);


            hakkinda.add(text);
            hakkinda.add(text2);
            hakkinda.add(text3);
            hakkinda.add(text4);

            linkLabel.setFocusable(true);
            linkLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    linkLabel.setForeground(Color.black);

                    try {
                        Desktop.getDesktop().browse(new URL("https://github.com/barismerall").toURI());
                    }
                    catch (MalformedURLException m){
                        m.printStackTrace();
                    }
                    catch (URISyntaxException u){
                        u.printStackTrace();
                    }
                    catch (IOException i){
                        i.printStackTrace();
                    }


                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                }


            });

            linkLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));


        }

    }

    public static void main(String[] args) throws ClassNotFoundException{


SplashFrame.showSplashScreen(10000,"C:\\Users\\Barış\\Desktop\\Java.png",320,310,true);

        Thread th = new Thread(new NotePad());
        th.start();


    }


    @Override
    public void run() {

        this.setVisible(true);



    }


    private void Save()throws IOException,FileNotFoundException{

      FileDialog saveDialog = new FileDialog(this,"Kaydet",FileDialog.SAVE);
      saveDialog.setFilenameFilter(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
              return name.endsWith(".txt");
          }
      });
      saveDialog.setFile("*.txt");
      saveDialog.setVisible(true);

      String fileName = saveDialog.getFile();
      File file = new File(saveDialog.getDirectory()+fileName);
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(textArea.getText());

      bufferedWriter.flush();
      bufferedWriter.close();


      kaydedildi = true;

      files = file;

    }

    private void Open()throws IOException{

        FileDialog openDialog = new FileDialog(this,"Aç",FileDialog.LOAD);

        openDialog.setVisible(true);

        File openedFile = new File(openDialog.getDirectory()+openDialog.getFile());
        FileReader fileReader = new FileReader(openedFile);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readText;
        while ((readText=bufferedReader.readLine())!=null)
            textArea.append(readText);

        bufferedReader.close();


        this.setTitle(openDialog.getFile());

          dosyaAcildi =true;


    }





}