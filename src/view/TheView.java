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
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author yehia
 */
public class TheView {
    
    
    public void view() throws IOException{
        DisplayManager.createDisplay();
		DataLoaderVAO loader = new DataLoaderVAO();
		
		
		ModelInfo model = OBJLoader.loadObjModel("Stormtrooper", loader);
		
		ModelAndTexuredInfo staticModel = new ModelAndTexuredInfo(model,new ModelTexture(loader.loadTexture("Dag_Texture")));
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
                
                Player myPlayer = new Player(staticModel, new Vector3f(0,0,-15),0,0,0,1f);
                
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera(myPlayer);	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
	
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
                        renderer.processEntity(myPlayer);
                        
                        camera.move();
                        
                        myPlayer.move();
                       
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

      }
}
