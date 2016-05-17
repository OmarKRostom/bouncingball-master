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
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author yehia
 */
public class TheView {
public static int Score;
private static int y=0;
private int lastx = -1;
private int lastz = -1;
private boolean negFlag = true;
private TreeMap zvals = new TreeMap();
private TreeMap xvals = new TreeMap();
private ArrayList<Float> xvalues = new ArrayList();
private ArrayList<Float> zvalues = new ArrayList();
    
    public void getIntelligentPosition(char type) {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            negFlag = !negFlag;
        if(type=='x' && negFlag==false) {
            float res = rand.nextInt(25)+0;
            xvals.put(i,res);
            xvalues.add(res);
        }
        else if(type=='x' && negFlag==true) {
            float res = -1 * rand.nextInt(25)+0;
            xvals.put(i,res);
            xvalues.add(res);
        }
        else if(type=='z') {
            float res = rand.nextInt(50000)+0;
            zvals.put(i,res);
            zvalues.add(res);
        }
        }
    }
    
//    public boolean did_collide_x(float pos) {
//        for (int i = -50; i < 50; i++) {
//            if(xvals.containsKey((int)pos+i)) 
//                return true;
//        }
//        return false;
//    }
//    
//    public boolean did_collide_z(float pos) {
//        for (int i = -50; i < 50; i++) {
//            if(zvals.containsKey((int)pos+i)) 
//                return true;
//        }
//        return false;
//    }
    
    public boolean detectcollision(Vector3f pos) {
        int check = 0;
        for (int i = 0; i < 1000; i++) {
            if((float)xvals.get(i)+5 > pos.x && (float)xvals.get(i)-5 < pos.x && (float)zvals.get(i)+1000 > -1*pos.z && (float)zvals.get(i)-1000 < -1*pos.z) {
                return true;
            }
        }
        return false;
    }

    public void view() throws IOException, LWJGLException {
        
        Random rand = new Random();
//        DisplayManager.createDisplay();
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
        MasterRenderer renderer = new MasterRenderer();

        
        ModelInfo model2 = OBJLoader.loadObjModel("CrateModel",loader);
        ModelAndTexuredInfo staticModel2 = new ModelAndTexuredInfo(model2, new ModelTexture(loader.loadTexture("wood","jpg")));
        getIntelligentPosition('x');
        getIntelligentPosition('z');
        for (int i = 0; i < 1000; i++) {
            Enemy enemy = new Enemy(staticModel2,new Vector3f(xvalues.get(i),0,-1*zvalues.get(i)),0,180,0,0.1f);
            enemies.add(enemy);
        }
        
        while (!Display.isCloseRequested()) {
            if(detectcollision(myPlayer.getPosition())) {
                System.out.println("COLLIDED");
            }
            Menu menu = new Menu();
            menu.checkInput();
            for(Terrain terrain:myList){
                renderer.processTerrain(terrain); 
            }
            
            renderer.processEntity(myPlayer);
            for (Enemy enemy:enemies) {
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
            Menu.writeFont(20,20,"Score : "+Integer.toString(Score));
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUP();
        DisplayManager.closeDisplay();

    }
}