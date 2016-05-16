/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Camera;
import controller.Entity;
import controller.Light;
import controller.MasterRenderer;
import controller.Player;
import controller.Terrain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.DataLoaderVAO;
import model.ModelTexture;
import model.OBJLoader;
import model.ModelInfo;
import model.ModelAndTexuredInfo;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author yehia
 */
public class TheView {

    public static void view() throws IOException, LWJGLException {
       // DisplayManager.createDisplay();
        DataLoaderVAO loader = new DataLoaderVAO();

        ModelInfo model = OBJLoader.loadObjModel("laastsphere", loader);

        ModelAndTexuredInfo staticModel = new ModelAndTexuredInfo(model, new ModelTexture(loader.loadTexture("ball", "jpg")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        Player myPlayer = new Player(staticModel, new Vector3f(0, 5, 0), 0, 180, 0, 1.5f);
        
        

        Light light = new Light(new Vector3f(350, 2000, 200), new Vector3f(1, 1, 1));

        List<Terrain> myList=new ArrayList<>();
        for(int i=0;i>-100;i--){
        Terrain terrain = new Terrain(0.5f/12, i, loader, new ModelTexture(loader.loadTexture("Road", "jpg")));
         myList.add(terrain);
        }
        
        
        Camera camera = new Camera(myPlayer);
        MasterRenderer renderer = new MasterRenderer();

        
        while (!Display.isCloseRequested()) {

            
            
            for(Terrain terrain:myList){
                
            renderer.processTerrain(terrain);
            
            }
            
            
            renderer.processEntity(myPlayer);

            camera.move();

            myPlayer.move();

            
           
            renderer.render(light, camera);
           
            
            Menu.writeFont(20,20,"Score :");
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();

    }
}
