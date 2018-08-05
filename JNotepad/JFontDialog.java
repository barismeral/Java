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

package baris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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



public class JFontDialog {



    JList<String> sizeList;
    JList<String> fontStyleList;
    JList<String> fontList;
    JFrame frame;
    JButton okButton;


  public  JFontDialog(){

       frame = new JFrame("Font Dialog");


        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        JLabel fontLabel = new JLabel("Fonts: ");


        final String[] FONTS = {"Arial", "Arial Black", "Batang", "Big Caslon", "Blackmoor LET", "Book Antiqua",
                "Britannic Bold", "Calisto MT", "Century", "Century Gothic", "Chalkboard", "Cochin", "comic Sans MS", "Cooper Black",
                "Courier", "Cracked", "Dialog", "Didot", "Euphemia UCAS", "Futura", "Garamond", "Geeza Pro", "Geneva", "Georgia",
                "Gill Sans", "Gulim", "Harrington", "Hei", "Helvetica", "Hercuşanum", "Hiragino", "Impact", "Jazz LET", "Kai", "Kino MT",
                "LiHei Pro", "Lucida Fax", "Lucida Sans", "Marker Felt", "Mistral", "Modern No. 20", "Mona Lisa Solid", "Monaco", "Monospaced",
                "Monotype Corsiva", "MS Gothic", "MT Extra", "Onyx", "Optima", "Osaka", "Palatino", "Papyrus", "Party LET", "Raana",
                "Rockwell", "SansSerif", "Santa Fe LET", "Sathu", "Savoya LET", "Serif", "Silom", "Stencil", "STHeiti", "Stone Sans",
                "Symbol", "Tahoma", "Times", "Times New Roman", "Verdana", "Webding", "Zapfino"};

        fontList = new JList<>(FONTS);
        JScrollPane fontScroll = new JScrollPane(fontList);

        JPanel  fontPanel = new JPanel();
        fontPanel.add(fontScroll);


        frame.getContentPane().add(fontPanel,BorderLayout.WEST);


        final  String[] fontStyle = {"Normal","Bold","Italic"};

        fontStyleList = new JList<>(fontStyle);
        JScrollPane styleScroll = new JScrollPane(fontStyleList);



        JPanel stylePanel = new JPanel();
        stylePanel.add(styleScroll);

        frame.getContentPane().add(stylePanel,BorderLayout.CENTER);

           final String[] SIZES = new String[50];

           for (int i=2,j=0;i<101;i+=2,j++){

               SIZES[j]=String.valueOf(i);
           }

          sizeList = new JList<>(SIZES);
           JScrollPane sizeScroll = new JScrollPane(sizeList);

           JPanel sizePanel = new JPanel();

           sizePanel.add(sizeScroll);

           frame.getContentPane().add(sizePanel,BorderLayout.EAST);



         okButton = new JButton("Ok");

         JButton cancelButton = new JButton("Cancel");

         JPanel buttonPanel = new JPanel();

         buttonPanel.add(okButton);
         buttonPanel.add(cancelButton);

         buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

           frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);


           cancelButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {

                   frame.dispose();

               }
           });


    }

/**
 * @return Font f
 */

    public Font getFont(){


     Font f = new Font(fontList.getSelectedValue(),fontStyleList.getSelectedIndex(),Integer.valueOf(sizeList.getSelectedValue()));

    return f;

    }

    /**
     *
     * @return JButton okButton
     */
    public JButton getOkButton(){
      return okButton;

    }




}