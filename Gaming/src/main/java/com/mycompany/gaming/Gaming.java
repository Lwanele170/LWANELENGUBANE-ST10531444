/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gaming;

/**
 *
 * @author Student
 */
public class Gaming {

    public static void main(String[] args) {

        // Cities
        String[] cities = {"Cape Town", "Port Elizabeth", "Pretoria"};

        // Gaming consoles
        String[] consoles = {"PS5", "XBOX", "NINTENDO SWITCH"};

        // Yearly sales
        // PS5, XBOX, SWITCH
        int[][] sales = {
            {1000, 2000, 3000}, 
            {2000, 3000, 4000},   
            {1500, 1100, 1200}    
        };

        
        int[] totalSales = new int[3];

        System.out.println("==============================================");
        System.out.println("          GAMING CONSOLE REPORT");
        System.out.println("             YEARLY SALES REPORT");
        System.out.println("==============================================");

        System.out.println("City\t\t\tPS5\tXBOX\tSWITCH\tTotal");
        System.out.println("------------------------------------------------");

        // Calculate the total sales for each city
        for (int i = 0; i < cities.length; i++) {

            int total = 0;

            for (int j = 0; j < consoles.length; j++) {
                total = total + sales[i][j];
            }

            totalSales[i] = total;

            System.out.println(cities[i] + "\t\t"
                    + sales[i][0] + "\t"
                    + sales[i][1] + "\t"
                    + sales[i][2] + "\t"
                    + totalSales[i]);
        }

        // Find the city with the most sales
        int highest = totalSales[0];
        int highestCity = 0;

        for (int i = 1; i < totalSales.length; i++) {

            if (totalSales[i] > highest) {
                highest = totalSales[i];
                highestCity = i;
            }
        }

        System.out.println("------------------------------------------------");
        System.out.println("City with the most sales: "
                + cities[highestCity]);
        System.out.println("Total sales: " + highest);

        System.out.println("==============================================");
    }
}
