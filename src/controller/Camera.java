package controller;


import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
        private Player myPlayer;
	private Vector3f position = new Vector3f(0,5,0);
	private float pitch = 5; //camera angle hight
	private float yaw=0;
	private float roll;
        private float distanceFromPlayer=50;
        private float angleAroundPlayer=0;
        
	
	public Camera(Player player){
             myPlayer=player;
        }
	
	public void move(){
         
            calculatezoom();
            calculatePitch();
            calculateAngleAroundPlayer();
            float horizontalDistance=getHorizontalDistance();
            float verticalDistance=getVerticalDistance();
            calculateCameraPosition(horizontalDistance,verticalDistance);
            this.yaw=180-(myPlayer.getRotY() + angleAroundPlayer);
            checkInput();           
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
        private void calculatezoom(){
           if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
               distanceFromPlayer+=1;
           }
           else if(Keyboard.isKeyDown(Keyboard.KEY_X)){
               distanceFromPlayer-=1;
           }
        }
        
        private void calculatePitch(){
            if(Keyboard.isKeyDown(Keyboard.KEY_P)){
               pitch+=0.5;
           }
           else if(Keyboard.isKeyDown(Keyboard.KEY_O)){
               pitch-=0.5;
           }
        }
        
        private void calculateAngleAroundPlayer(){
            if(Keyboard.isKeyDown(Keyboard.KEY_M)){
              angleAroundPlayer+=1;
           }
           else if(Keyboard.isKeyDown(Keyboard.KEY_N)){
               angleAroundPlayer-=1;
           }
        }
        
        private float getHorizontalDistance(){
            return (float)(distanceFromPlayer*Math.cos(Math.toRadians(pitch)));
        }
        
        private float getVerticalDistance(){
            return (float)(distanceFromPlayer*Math.sin(Math.toRadians(pitch)));
        }
        
	private void calculateCameraPosition(float hDistance , float vDistance){
            float theta = myPlayer.getRotY() + angleAroundPlayer;
            float xOffset=(float)(hDistance * Math.sin(Math.toRadians(theta)));
            float zOffset=(float)(hDistance * Math.cos(Math.toRadians(theta)));
            
            position.x=myPlayer.getPosition().x-xOffset;
            position.y=myPlayer.getPosition().y+vDistance;
            position.z=myPlayer.getPosition().z-zOffset;
        }

        private void checkInput(){
        
            if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
                distanceFromPlayer=-12;
                pitch=2;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_W)){
                distanceFromPlayer=50;
                pitch=5;
            }
        
        
        }
}
