package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");
    /**
	* 
	* @param read user vehicle selection input
	*/
    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            
            return input;
        }
        catch(Exception e) {
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            
            return -1;
        }
    }
    /**
	* 
	* @param read the vehicle registration number input
	*/
    public String readVehicleRegistrationNumber() throws Exception {
        try {
            String vehicleRegNumber = scan.nextLine();
            
            // Displays an error if the character string is null or equal to zero
            if(vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            return vehicleRegNumber;
        }
        catch(Exception e) {
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            
            throw e;
        }
    }
}
