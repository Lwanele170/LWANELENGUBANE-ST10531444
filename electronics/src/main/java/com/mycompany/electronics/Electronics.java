/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.electronics;

/**
 *
 * @author Student
 */

import java.util.Scanner;

public class Electronics {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("ELECTRONICS STORE");
        System.out.println("=================");

        System.out.print("Enter console type: ");
        String console = input.nextLine();

        System.out.print("Enter store name: ");
        String storeName = input.nextLine();

        System.out.print("Enter total sales: ");
        int sales = input.nextInt();

        ConsoleSales mySales = new ConsoleSales(console, storeName, sales);

        mySales.printReport();

        input.close();
    }
}