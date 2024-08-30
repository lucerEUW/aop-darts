package vlKlapptDart;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Throw{
	 private static boolean running = true;
	 private static int currentNumber = 0;
	 private static boolean ascending = true;
	 public static float xValue = 0;
	 public static float yValue = 0;
	 public static boolean xRunning = true;
	 public static boolean yRunning = false;
	 private static GameScore score;
	 public static int speed = 1;
	 public static boolean finishedThrow = false;
	 
	 public void Throw(View view, GameScore scoreIn) {
		 score = scoreIn;
		 while (!finishedThrow) {
			 /*
             if (!running) {
                 break;
             }*/
             
             //looping between 0-99
             updateIndicatorBar(view.dartboard);
             
             if (currentNumber >= 99) {
                 ascending = false; 
             } else if (currentNumber <= 0) {
                 ascending = true; 
             }
             if (ascending) {
           	  currentNumber = currentNumber + speed;
             } else {
           	  currentNumber =currentNumber - speed;
             }

             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
             }
         }
	 }
	 
     public static void setxValue() {
    	 xRunning = false;
    	 yRunning = true;
		 xValue = (float) currentNumber/100;
		 //System.out.println("xValue: " + xValue);
		 currentNumber = 0;
		 ascending = true;
     }
     
     public static void setyValue(DartboardView dartboardView) {
    	 running = false;
    	 yValue = (float) currentNumber/100;
		 //System.out.println("yValue: " + yValue);
		// dartboardView.addDart(xValue, yValue, score.getCurrentPlayer().getColor());
		 finishedThrow = true;
     }
     
     private void updateIndicatorBar(DartboardView dartboardView) {
    	 if (xRunning){
    		 SwingUtilities.invokeLater(() -> dartboardView.setAimingPosition((float) currentNumber/100, 0));
    	 }else if (yRunning) {
    		 SwingUtilities.invokeLater(() -> dartboardView.setAimingPosition(xValue, (float) currentNumber/100));
    	 }
     }
     
     public float getx() {
    	 return xValue;
     }

     public float gety() {
    	 return yValue;
     }
}
