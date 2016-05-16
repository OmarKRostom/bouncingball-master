/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author loai Aboelsooud
 */
public class Menu {

    private static enum State {

        INTRO, MAIN_MENU, GAME;
    }
    private State state = State.INTRO;
    public TrueTypeFont font;
    public boolean antiAlias = true;

    public void loadFont() {
        Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
        font = new TrueTypeFont(awtFont, false);

    }

    public void writeFont(int BL,int BU,String x) {

        Color.white.bind();
        font.drawString(BL, BU, x, Color.green);

    }

    public void menu() throws LWJGLException, IOException {

        switch (state) {
            case MAIN_MENU:
                writeFont(500,100,"PAUSE");
                break;
            case GAME:
                TheView.view();
                break;
            case INTRO:
                DisplayManager.createDisplay();
                Display.setVSyncEnabled(true);
                loadFont();
                 GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
      
                while (!Display.isCloseRequested()) {
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                     writeFont(400,400,"space bar to start");
                    
                    checkInput();
                    Display.update();
                    Display.sync(60);
                }

                break;
        }
    }

    public  void checkInput() throws IOException, LWJGLException {
        switch (state) {
            case INTRO:
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    state = state.GAME;
                    menu();
                }
                /*   if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                 Display.destroy();
                 System.exit(0);
                 }*/
                break;
            case GAME:
                if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
                    state = state.MAIN_MENU;
                    menu();
                }
                break;
            case MAIN_MENU:

                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    state = state.GAME;
                    menu();

                }

                break;

        }
    }

}
