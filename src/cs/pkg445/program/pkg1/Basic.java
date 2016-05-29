/***************************************************************
* file: DoubleIt.java
* author: J. Dao, P. Santos, I. Berger
* class: CS 141 – Programming and Problem Solving
*
* assignment: Quarter Project
* date last modified: 5/27/2016
*
* purpose: This program create a 30x30x30 minecraft chunk filled with random 
* blocks that are either grass, dirt, sand, stone, bedrock, or water.
****************************************************************/ 
package cs.pkg445.program.pkg1;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

public class Basic{
	

    private FPCameraController fp;
    private DisplayMode displayMode;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
   
    // method: start
    // purpose: create window, initialize OpenGL, print instructions, and run the main loop
    public void start(){
        try{
            createWindow();
            initGL();
			printInstructions();
			fp = new FPCameraController(0f, 0f, 0f);
            run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    // method: createWindow
    // purpose: creates a nonfullscreen window with resolution 640x480 with title "Final Program"
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++)
        {
            if (d[i].getWidth() == 640
                    && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 32)
            {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Final Program");
        Display.create();

    }
    
    // method: initGL
    // purpose: initialize OpenGL and set the background to black
    private void initGL(){
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100f, (float) Display.getWidth() / (float) Display.getHeight(), 0.1f, 300f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_TEXTURE_2D);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		initLightArrays();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
		glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
		
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
    }
	
	// method: initLightArrays
    // purpose: initialize light arrays to be used with OpenGL
	private void initLightArrays(){
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(0f).put(0f).put(0f).put(1f).flip();
		
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1f).put(1f).put(1f).put(0f).flip();
	}
	
	// method: printInstructions
	// purpose: print control instructions to the standard output
	private void printInstructions(){
		System.out.println("W - move forward");
		System.out.println("S - move backward");
		System.out.println("A - strafe left");
		System.out.println("D - strafe right");
		System.out.println("Shift - move down");
		System.out.println("Space - move up");
	}
    
    // method: run
    // purpose: main loop and runs the program by using gameloop.
    public void run(){
        while (!Display.isCloseRequested()){
            try{
                fp.gameLoop();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // method: main
    // purpose: entry point for program, runs the start method
    public static void main(String[] args){
        Basic basic = new Basic();
        basic.start();
    }
}
