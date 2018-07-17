package dev.ipsych0.mygame.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javax.swing.JFrame;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.audio.AudioManager;


public class Display implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	
	}
	
	private void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		
		// For the X (close) button
		frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	        	 try {
	        		 AudioManager.cleanUp();
	        	 }catch(Exception e) {
	        		 
	        		 System.err.println("Unexpected crash. Unable to close OpenAL audio context.");
	        	 }finally {
	        		 System.exit(0);
	        	 }
	          }        
	       });
		frame.setResizable(false);
		
		// Window will appear in the center of the user's screen
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		
		frame.pack();
		frame.setResizable(false);
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
