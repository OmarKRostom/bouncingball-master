
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
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectf;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author loai Aboelsooud
 */
public class Menu {

    private static enum State {

        INTRO, MAIN_MENU, GAME;
    }
    private State state = State.INTRO;
    public static TrueTypeFont font;
    public boolean antiAlias = true;
    private static Texture texture;

    public static void loadFont() {
        Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
        font = new TrueTypeFont(awtFont, false);

    }

    public static void writeFont(int BL, int BU, String x) {
        loadFont();
        Color.white.bind();
        font.drawString(BL, BU, x, Color.red);

    }

    public static void loadImage() {

        try {
            // load texture from PNG file
        texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/lastisaasphere.png"));

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawImage() {
        loadImage();
        Color.white.bind();
        texture.bind(); // or GL11.glBind(texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(100, 100);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(100 + texture.getTextureWidth(), 100);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(100 + texture.getTextureWidth(), 100 + texture.getTextureHeight());
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(100, 100 + texture.getTextureHeight());
        GL11.glEnd();
    }

    public void menu() throws LWJGLException, IOException {

        switch (state) {
            case MAIN_MENU:
                glColor3f(1.0f, 0f, 0f);
                glRectf(0, 0, 1280, 720);
                writeFont(500, 100, "Pause");
                break;
            case GAME:
                TheView v = new TheView();
                v.view();
                break;
            case INTRO:
                DisplayManager.createDisplay();
                Display.setVSyncEnabled(true);
                loadFont();
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                while (!Display.isCloseRequested()) {
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                    drawImage();
                    writeFont(400, 400, "space bar to start");

                    checkInput();
                    Display.update();
                    Display.sync(60);
                }

                break;
        }
    }

    public void checkInput() throws IOException, LWJGLException {
        switch (state) {
            case INTRO:
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    state = state.MAIN_MENU;
                    menu();
                }
                /*   if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                 Display.destroy();
                 System.exit(0);
                 }*/
                break;
            case GAME:
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    state = state.MAIN_MENU;
                    System.out.println("test");
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