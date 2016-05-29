/***************************************************************
* file: FPCameraController.java
* author: J. Dao, P. Santos, I. Berger
* class: CS 445 - Computer Graphics
*
* assignment: Quarter Project
* date last modified: 5/29/2016
*
* purpose: This code creates a first person camera with standard controls, and 
* adds a minecraft chunk as described in Basic.java to the world.
****************************************************************/ 
package cs.pkg445.program.pkg1;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController{
    private Vector3f position = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
	private Chunk chunk;

    // Constructor: FPCameraController
    // purpose: intializes variables 
    public FPCameraController(float x, float y, float z){
        position = new Vector3f(x, y, z);
		chunk = new Chunk(0, -100,	-50);//Starting location of chunck relative to camera's initial possition
    }

    // method: yaw
    // purpose: increment the yaw by the amount param
    public void yaw(float amount){
        yaw += amount;
    }

    // method: pitch
    // purpose: decrement the pitch by the amount param and keep it from allowing the camera to turn upside down
    public void pitch(float amount){
        pitch -= amount;
		if(pitch<-90){
			pitch = -90;
		}
		if(pitch>90){
			pitch = 90;
		}
    }

    // method: walkFoward
    // purpose: moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance){
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(position.x-=xOffset).put(position.y).put(position.z+=zOffset).put(1f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: walkBackwards
    // purpose: moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance){
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(position.x+=xOffset).put(position.y).put(position.z-=zOffset).put(1f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: strafeLeft
    // purpose: strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance){
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(position.x-=xOffset).put(position.y).put(position.z+=zOffset).put(1f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: strafeRight
    // purpose: strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance){
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));
        position.x -= xOffset;
        position.z += zOffset;
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(position.x-=xOffset).put(position.y).put(position.z+=zOffset).put(1f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: moveUp
    // purpose: moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance){
        position.y -= distance;
    }

    // method: moveDown
    // purpose: moves the camera down
    public void moveDown(float distance){
        position.y += distance;
    }

    // method: lookThrough
    // purpose: tranlsates and rotate so that it looks through the camera
    public void lookThrough(){
        //rotate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //rotate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(position.x).put(position.y).put(position.z).put(1f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: gameLoop
    // purpose: keeps the game running until escape key is pressed
    public void gameLoop(){
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; 
        float lastTime = 0.0f; 
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        
        //hide the mouse
        Mouse.setGrabbed(true);
        while (!Display.isCloseRequested()){
            try{
                if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                    System.exit(0);
                
                time = Sys.getTime();
                lastTime = time;

                //distance in mouse movement
                //from the last getDX() call.
                dx = Mouse.getDX();
                //distance in mouse movement
                //from the last getDY() call.
                dy = Mouse.getDY();
                camera.yaw(dx * mouseSensitivity);
                camera.pitch(dy * mouseSensitivity);

                if (Keyboard.isKeyDown(Keyboard.KEY_W)){//move forward
                    camera.walkForward(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_S)){//move backwards
                    camera.walkBackwards(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_A)){//strafe left
                    camera.strafeLeft(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_D)){//strafe right
                    camera.strafeRight(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){//move up
                    camera.moveUp(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){//move down
                    camera.moveDown(movementSpeed);
                }

                //set the modelview matrix back to the identity
                glLoadIdentity();
                //look through the camera before you draw anything
                camera.lookThrough();
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                //render();
				chunk.render();
                Display.update();
                Display.sync(60);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        Display.destroy();
    }

    // method: render
    // purpose: draws cube
    private void render(){
        try{

            //Top - blue
            glBegin(GL_QUADS);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glEnd();

            //Bottom - Orange
            glBegin(GL_QUADS);
            glColor3f(1.0f, 0.5f, 0.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);
            glEnd();

            //Front - green
            glBegin(GL_QUADS);
            glColor3f(0f, 1.0f, 0.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glEnd();

            //Back - Magenta
            glBegin(GL_QUADS);
            glColor3f(1.0f, 0f, 1.0f);       
            glVertex3f(1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, -1.0f);
            glEnd();

            //Left red
            glBegin(GL_QUADS);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glEnd();

            //Right - Cyan
            glBegin(GL_QUADS);
            glColor3f(0.0f, 1.0f, 1.0f);      
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);
            glEnd();

        } catch (Exception e){
        }
    }
}
