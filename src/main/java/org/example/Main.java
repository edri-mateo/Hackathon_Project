package org.example;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{
    public static void main(String[] args) {
        UI gui = new UI();

        // name,subject,date,weight(optional),description(optional)

        if (args.length > 0) {
            // args[0] should be file name?
            File inputFile = new File(args[0]);

            try {
                Scanner scnr = new Scanner(inputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
