package vlKlapptDart;

import javax.swing.*;
import java.awt.event.*;

public class Throw{
	 private static boolean running = true;
	 private static int currentNumber = 0;
	 private static boolean ascending = true;
	 private static int xValue;
	 private static int yValue;
	 
	 public static void main(String[] args) {
		 //test button setup
		 JFrame frame = new JFrame();
	     JButton button = new JButton("stop");
	     JLabel label = new JLabel("currentNumber: 0");
	     frame.setLayout(new java.awt.FlowLayout());
	     frame.add(label);
	     frame.add(button);
	     frame.setSize(300, 200);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setVisible(true);
		 
	     //starting extra thread for counter running simultaneously
	     Thread incrementerThread = new Thread(() -> {
	          while (true) {
	              if (!running) {
	                  break;
	              }
	              
	              //looping between 0-99
	              label.setText("Current Number: " + currentNumber);
	              if (currentNumber >= 99) {
	                  ascending = false; 
	              } else if (currentNumber <= 0) {
	                  ascending = true; 
	              }
	              if (ascending) {
	                  currentNumber++;
	              } else {
	                  currentNumber--;
	              }

	              try {
	                  Thread.sleep(10);
	              } catch (InterruptedException e) {
	                  Thread.currentThread().interrupt();
	              }
	          }
	      });
	     incrementerThread.start();
	     
	     //storing values from both pressing and releasing 
	     button.addMouseListener(new MouseAdapter() {
	    	 public void mousePressed(MouseEvent e) {
	    		 xValue = currentNumber;
	    		 System.out.println("xValue: " + xValue);
	    		 currentNumber = 0;
	    		 ascending = true;
	         }
	    	 //and releasing 
	         public void mouseReleased(MouseEvent e) {
	        	 running = false;
	        	 yValue = currentNumber;
	    		 System.out.println("xValue: " + yValue);
	         }
	     });
	 }
}
