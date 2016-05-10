
package controller;

import model.ModelAndTexuredInfo;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import view.DisplayManager;

/**
 *
 * @author yehia
 */
public class Player extends Entity{

    private static final float runSpeed=0.05f;
    private static final float turnSpeed=0.5f;
    private static final float LeftRightSpeed=1f;
    private static final float gravity=-0.001f;
    private static final float jumpPower=0.3f; 
    private static final float terrainHieght=0;
    private float currentRunSpeed=0;
    private float currentTurnSpeed=0;
    private float movingLeftRightSpeed=0; 
    private float upwardsSpeed=0;
    Boolean inAir=false;
    
    
    public Player(ModelAndTexuredInfo model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    
    public void move(){
        checkInputs();
        currentTurnSpeed=turnSpeed;
        
        if(movingLeftRightSpeed!=0){
            this.currentRunSpeed=0;    
        }
        else{
           this.currentRunSpeed=runSpeed;
           super.increaseRotation(this.currentTurnSpeed*DisplayManager.getFrameTime(),0,0);
        }
        
        //super.increasePosition(movingLeftRightSpeed,0,0);
      
        float distance= this.currentRunSpeed*DisplayManager.getFrameTime();
        float dx= (float)(distance*Math.sin(Math.toRadians(super.getRotY())));
        float dz=(float)(distance*Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed+=gravity*DisplayManager.getFrameTime();
        //super.increasePosition(0, upwardsSpeed*DisplayManager.getFrameTime(),0);
        if(super.getPosition().y<terrainHieght){
            upwardsSpeed=0;
            super.getPosition().y=terrainHieght;
            inAir=false;  
        }
        
   
    }
    
    
    
    private void checkInputs(){
        
       if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
          movingLeftRightSpeed=LeftRightSpeed;    
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
             movingLeftRightSpeed=-LeftRightSpeed;
        }
        else{ 
            movingLeftRightSpeed=0;
        }
          
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            if(!inAir){
                upwardsSpeed=jumpPower;
                inAir=true;
            }
        }
    }
    
    
    
    
}
