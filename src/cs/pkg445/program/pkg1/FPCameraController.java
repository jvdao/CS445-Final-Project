/***************************************************************
* file: DoubleIt.java
* author: J. Dao, P. Santos, I. Berger
* class: CS 141 – Programming and Problem Solving
*
* assignment: Quarter Project
* date last modified: 5/4/2016
*
* purpose: This program simulates a first person camera by displaying
* a cube and allowing a user to navigate the environment.
****************************************************************/ 
package cs.pkg445.program.pkg1;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController
{
    private Vector3f position = null;
    private Vector3f lPosition = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;

    // Constructor: FPCameraController
    // purpose: intializes variables 
    public FPCameraController(float x, float y, float z)
    {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }

    // method: yaw
    // purpose: increment the yaw by the amount param
    public void yaw(float amount)
    {
        yaw += amount;
    }

    // method: pitch
    // purpose: increment the pitch by the amount param
    public void pitch(float amount)
    {
        pitch -= amount;
    }

    // method: walkFoward
    // purpose: moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: walkBackwards
    // purpose: moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }

    // method: strafeLeft
    // purpose: strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: strafeRight
    // purpose: strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));
        position.x -= xOffset;
        position.z += zOffset;
    }

    // method: moveUp
    // purpose: moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance)
    {
        position.y -= distance;
    }

    // method: moveDown
    // purpose: moves the camera down
    public void moveDown(float distance)
    {
        position.y += distance;
    }

    // method: lookThrough
    // purpose: tranlsates and rotate so that it looks through the camera
    public void lookThrough()
    {
        //rotate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //rotate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }

    // method: gameLoop
    // purpose: keeps the game running until escape key is pressed
    public void gameLoop()
    {
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
        while (!Display.isCloseRequested())
        {
            try
            {
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

                if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
                {
                    camera.walkForward(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
                {
                    camera.walkBackwards(movementSpeed);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left {
                {
                    camera.strafeLeft(movementSpeed);
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right {
                {
                    camera.strafeRight(movementSpeed);
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up {
                {
                    camera.moveUp(movementSpeed);
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) //move down
                {
                    camera.moveDown(movementSpeed);
                }

                //set the modelview matrix back to the identity
                glLoadIdentity();
                //look through the camera before you draw anything
                camera.lookThrough();
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                render();
                Display.update();
                Display.sync(60);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }

    // method: render
    // purpose: draws cube
    private void render()
    {
        try
        {

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

        } catch (Exception e)
        {
        }
    }
}
