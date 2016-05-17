
package skyBox;

import controller.Camera;
import model.DataLoaderVAO;
import model.ModelInfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;


public class skyBoxRenderer {
    
    private static final float SIZE = 500f;
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};
        
        private static String[] TEXTURE_FILEs = {"right","left","top","bottom","back","front"};
        
        private ModelInfo cube;
        private int texture;
        private skyBoxShader shader;
        
        public skyBoxRenderer(DataLoaderVAO loader ,Matrix4f projectionMatrix){
            cube = loader.loadToVAO(VERTICES, 3);
            texture = loader.loadCupeMap(TEXTURE_FILEs);
            shader = new skyBoxShader();
            shader.start();
            shader.loadProjectionMatrix(projectionMatrix);
            shader.stop();
        }
        
        public void render(Camera camera){
            shader.start();
            shader.loadViewMatrix(camera);
            GL30.glBindVertexArray(cube.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
            shader.stop();
        }
        
    
}
