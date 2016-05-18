package main;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import view.Menu;
import view.TheView;


public class MainGameLoop {
 
    public static void main(String[] args) throws IOException, LWJGLException {

        Menu mymenu = new Menu();
        mymenu.play_in_game();
        mymenu.menu();
        
        //TheView v = new TheView();
        //v.view();

    }

}
