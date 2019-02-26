package dev.ipsych0.myrinnia.input;

import dev.ipsych0.myrinnia.abilityhud.AbilityHUD;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.entities.EntityManager;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.puzzles.SliderPuzzle;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.ui.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.ui.ScrollBar;
import dev.ipsych0.myrinnia.ui.UIImageButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

public class MouseManager implements MouseListener, MouseMotionListener, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -98228253788414846L;
    private boolean leftPressed, rightPressed, isDragged;
    private int mouseX, mouseY;
    private int mouseMovedTimer;
    private Rectangle mouseCoords;
    public static boolean justClosedUI;

    public MouseManager() {

    }

    public void tick() {
        mouseMovedTimer++;
        if (leftPressed) {
            mouseMovedTimer = 0;
            Player.mouseMoved = true;
        }

        if (mouseMovedTimer > 20)
            Player.mouseMoved = false;

    }

    // Getters & Setters

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isDragged() {
        return isDragged;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }


    // Implemented methods
    @Override
    public void mousePressed(MouseEvent e) {
        // Left Click
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            CraftingUI.craftButtonPressed = true;
            ShopWindow.hasBeenPressed = true;
            ChatDialogue.hasBeenPressed = true;
            QuestUI.hasBeenPressed = true;
            State.hasBeenPressed = true;
            UIImageButton.hasBeenPressed = true;
            CharacterUI.hasBeenPressed = true;
            SkillsUI.hasBeenPressed = true;
            SkillsOverviewUI.hasBeenPressed = true;
            HPOverlay.hasBeenPressed = true;
            BankUI.hasBeenPressed = true;
            SliderPuzzle.hasBeenPressed = true;
            AbilityHUD.hasBeenPressed = true;
            AbilityShopWindow.hasBeenPressed = true;
            AbilityOverviewUI.hasBeenPressed = true;
        }

        // Right Click
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
            InventoryWindow.equipPressed = true;
            CraftingUI.craftResultPressed = true;
            EntityManager.isPressed = false;
            BankUI.hasBeenPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            isDragged = false;
            justClosedUI = false;
            ScrollBar.clickTimer = 0;
            ScrollBar.scrollTimer = 0;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            isDragged = false;
            rightPressed = false;
        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovedTimer = 0;
        Player.mouseMoved = true;
        mouseX = e.getX();
        mouseY = e.getY();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovedTimer = 0;
        Player.mouseMoved = true;

        if (leftPressed) {
            isDragged = true;
        }

        // Fix hier shit
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public Rectangle getMouseCoords() {
        return mouseCoords;
    }

    public void setMouseCoords(Rectangle mouseCoords) {
        this.mouseCoords = mouseCoords;
    }

}
