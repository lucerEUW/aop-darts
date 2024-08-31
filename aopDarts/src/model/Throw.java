package model;

import javax.swing.*;

import view.DartboardView;
import view.View;


public class Throw{
	 private static int currentNumber = 0;
	 private static boolean ascending = true;
	 private static float xValue = 0;
	 private static float yValue = 0;
	 // Controls whether the X or Y coordinate is being determined
	 private static boolean xRunning = true;
	 private static boolean yRunning = false;
	 // The speed at which the indicator bar moves (higher = slower)
	 private static int speed = 10;
	 // Flag to indicate if the throw is complete
	 private static boolean finishedThrow = false;
	 
	 public void Throw(View view) {
		 while (!finishedThrow) {
			 // Update the UI with the current position of the indicator bar
             updateIndicatorBar(View.dartboard);
             
             // Reverse direction if the bar reaches the ends
             if (currentNumber >= 99) {
                 ascending = false; 
             } else if (currentNumber <= 0) {
                 ascending = true; 
             }
             // Increment or decrement the indicator based on direction
             if (ascending) {
           	  	currentNumber ++;
             } else {
           	  	currentNumber--;
             }

             try {
            	 // Pause briefly to control the movement speed
                 Thread.sleep(speed);
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
             }
         }
	 }
	 
     public static void setxValue() {
    	 // Switch from setting X to setting Y
    	 xRunning = false;
    	 yRunning = true;
		 xValue = (float) currentNumber/100; // Capture the X value as a percentage
		 currentNumber = 0;
		 ascending = true;
     }
     
     public static void setyValue(DartboardView dartboardView) {
    	 yValue = (float) currentNumber/100; // Capture the Y value as a percentage
		 finishedThrow = true; // Mark the throw as complete
     }
     
     private void updateIndicatorBar(DartboardView dartboardView) {
    	 if (xRunning){
    		 // Update UI with the current X position
    		 SwingUtilities.invokeLater(() -> dartboardView.setAimingPosition((float) currentNumber/100, 0));
    	 }else if (yRunning) {
    		 // Update UI with the current Y position
    		 SwingUtilities.invokeLater(() -> dartboardView.setAimingPosition(xValue, (float) currentNumber/100));
    	 }
     }
     
     public float getx() {
    	 return xValue;
     }

     public float gety() {
    	 return yValue;
     }
     
     public static void resetThrow() {
    	 Throw.xRunning = true;
    	 Throw.yRunning = false;
    	 Throw.finishedThrow = false;
     }
}
