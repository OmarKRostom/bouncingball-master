/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Camera;
import controller.Enemy;
import controller.Entity;
import controller.Light;
import controller.MasterRenderer;
import controller.Player;
import controller.Terrain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import model.DataLoaderVAO;
import model.ModelTexture;
import model.OBJLoader;
import model.ModelInfo;
import model.ModelAndTexuredInfo;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.Audio;

/**
 *
 * @author yehia
 */
public class TheView {
public static int Score;
private static int y=0;
private float lastx = -1;
private float lastz = -1;
private boolean negFlag = false;
private ArrayList<Integer> xvals = new ArrayList();
private ArrayList<Integer> zvals = new ArrayList();

    
    public void getIntelligentPosition() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            negFlag = !negFlag;
            int X_pos = rand.nextInt(25);
            int Z_pos = rand.nextInt(50000);
            if(negFlag) {
                xvals.add(-1*X_pos);
                zvals.add(-1*Z_pos);
            } else {
                xvals.add(X_pos);
                zvals.add(-1*Z_pos);
            }
        }
        //Collections.sort(zvals);
    }
    
    public void collided() {
        for (int i = 0; i < 1000; i++) {
            if(lastz <= zvals.get(i)+25 && lastz>=zvals.get(i)-25 && lastx <= xvals.get(i)+2 && lastx>=xvals.get(i)-2) {
                System.out.println("PLAYER X : " + lastx);
                System.out.println("ENEMY X : " + xvals.get(i));
                System.out.println("PLAYER Z : " + lastz);
                System.out.println("ENEMY Z : " + zvals.get(i));
            }
        }
    }

    public void view() throws IOException, LWJGLException {
        
        Random rand = new Random();
        //DisplayManager.createDisplay();
        DataLoaderVAO loader = new DataLoaderVAO();

        ModelInfo model = OBJLoader.loadObjModel("laastsphere", loader);

        ModelAndTexuredInfo staticModel = new ModelAndTexuredInfo(model, new ModelTexture(loader.loadTexture("ball", "jpg")));

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        Player myPlayer = new Player(staticModel, new Vector3f(10, 15, 10), 0, 180, 0, 1.5f);

        Light light = new Light(new Vector3f(350, 2000, 200), new Vector3f(1, 1, 1));

        List<Terrain> myList=new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        
        for(int i=0;i>-100;i--){
        Terrain terrain = new Terrain(0.5f/16, i, loader, new ModelTexture(loader.loadTexture("Road", "jpg")));
         myList.add(terrain);
        }
        
        Camera camera = new Camera(myPlayer);
        MasterRenderer renderer = new MasterRenderer(loader);
        
       

        
        ModelInfo model2 = OBJLoader.loadObjModel("CrateModel",loader);
        ModelAndTexuredInfo staticModel2 = new ModelAndTexuredInfo(model2, new ModelTexture(loader.loadTexture("wood","jpg")));
        //guis.add(gui);
        
       
        
        getIntelligentPosition();
        for (int i = 0; i < 1000; i++) {
            Enemy enemy = new Enemy(staticModel2,new Vector3f(xvals.get(i),0,zvals.get(i)),0,180,0,0.1f);
            enemies.add(enemy);
        }
        
        
        
        while (!Display.isCloseRequested()) {
            Menu menu = new Menu();
            menu.checkInput();
            for(Terrain terrain:myList){
                renderer.processTerrain(terrain); 
            }
            
            renderer.processEntity(myPlayer);
            this.lastx = myPlayer.getPosition().x;
            this.lastz = myPlayer.getPosition().z;
            collided();
            for (int i = 0; i < 1000; i++) {
                Enemy enemy = enemies.get(i);
                renderer.processEntity(enemy);
            }
            
            
            camera.move();
            light.move();
            myPlayer.move();
            
            
           
            renderer.render(light, camera);
            
           
            if(y%2==0)
            {
            Score++;
            }
            y++;
            Menu.writeFont(250,250,"Score : "+Integer.toString(Score));
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();

    }
}