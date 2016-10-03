package dev.ipsych0.mygame.input;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

import dev.ipsych0.mygame.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	private boolean leftPressed, rightPressed, isDragged, isDoublePressed;
	private int mouseX, mouseY;
	private UIManager uiManager;
	
	// Double-click cooldown
	private long lastClickedTimer, clickCooldown = 480, clickTimer = clickCooldown;

	public MouseManager(){
		
	}
	
	public void setUIManager(UIManager uiManager){
		this.uiManager = uiManager;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		clickTimer += System.currentTimeMillis() - lastClickedTimer;
		lastClickedTimer = System.currentTimeMillis();
		
		
		if(clickTimer < clickCooldown){
			if(e.getClickCount() % 2 == 0 && !e.isConsumed()){
				e.consume();
				isDoublePressed = true;
				clickTimer = 0;
				System.out.println("Double-Clicked!");
			}
		}
		else{
			leftPressed = true;
			isDragged = true;
			clickTimer = 0;
			System.out.println("Single Clicked!");
		}
		
		
		// Right Click
		if(e.getButton() == MouseEvent.BUTTON3){
			rightPressed = true;
			System.out.println("Right Clicked!");
		}
	}
	
	// Getters & Setters
	
	public boolean isLeftPressed(){
		return leftPressed;
	}
	
	public boolean isRightPressed(){
		return rightPressed;
	}
	
	public boolean isDragged() {
		return isDragged;
	}

	public boolean isDoublePressed() {
		return isDoublePressed;
	}

	public void setDoublePressed(boolean isDoublePressed) {
		this.isDoublePressed = isDoublePressed;
	}

	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
	
	// Implemented methods
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			leftPressed = false;
			isDragged = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			rightPressed = false;
		}
		
		if(uiManager != null)
			uiManager.onMouseRelease(e);
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(uiManager != null)
			uiManager.onMouseMove(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(isDragged){
			// Fix hier shit
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
