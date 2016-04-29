/**
 * *************************************************************
 * file: Basic.java author: ?????????????????????? class: CS 445 – Computer
 * Graphics
 * 
* assignment: final program date last modified: 4/27/2016 * purpose: 
***************************************************************
 */
package cs.pkg445.program.pkg1;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Basic {
	// method: start
    // purpose: create window, initialize OpenGL, print instructions, and run the main loop

    public void start() {
        try {
            createWindow();
            initGL();
            printInstructions();
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	// method: createWindow
    // purpose: creates a nonfullscreen window with resolution 640x480 with title "Final Program"

    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Final Program");
        Display.create();
    }
	// method: initGL
    // purpose: initialize OpenGL's coordinate system with the orgin at the center of the window and set the background to black

    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClearDepth(1.0f);                   // Set background depth to farthest
        glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling
        glDepthFunc(GL_LEQUAL);    // Set the type of depth-test
        glShadeModel(GL_SMOOTH);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-3, 3, -3, 3, 3, -3);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT,
                GL_NICEST);
    }
	// method: render
    // purpose: 

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glRotatef(-45, 1.0f, 1.0f, 1.0f);            // Rotate The Cube On X, Y & Z
                glBegin(GL_QUADS);
                glColor3f(0.0f, 1.0f, 0.0f);          // Set The Color To Green
                glVertex3f(1.0f, 1.0f, -1.0f);          // Top Right Of The Quad (Top)
                glVertex3f(-1.0f, 1.0f, -1.0f);          // Top Left Of The Quad (Top)
                glVertex3f(-1.0f, 1.0f, 1.0f);          // Bottom Left Of The Quad (Top)
                glVertex3f(1.0f, 1.0f, 1.0f);          // Bottom Right Of The Quad (Top)
                
                glColor3f(1.0f, 0.5f, 0.0f);          // Set The Color To Orange
                glVertex3f(1.0f, -1.0f, 1.0f);          // Top Right Of The Quad (Bottom)
                glVertex3f(-1.0f, -1.0f, 1.0f);          // Top Left Of The Quad (Bottom)
                glVertex3f(-1.0f, -1.0f, -1.0f);          // Bottom Left Of The Quad (Bottom)
                glVertex3f(1.0f, -1.0f, -1.0f);          // Bottom Right Of The Quad (Bottom
               
                glColor3f(1.0f, 0.0f, 0.0f);          // Set The Color To Red
                glVertex3f(1.0f, 1.0f, 1.0f);          // Top Right Of The Quad (Front)
                glVertex3f(-1.0f, 1.0f, 1.0f);          // Top Left Of The Quad (Front)
                glVertex3f(-1.0f, -1.0f, 1.0f);          // Bottom Left Of The Quad (Front)
                glVertex3f(1.0f, -1.0f, 1.0f);          // Bottom Right Of The Quad (Front)
                
                glColor3f(1.0f, 1.0f, 0.0f);          // Set The Color To Yellow
                glVertex3f(1.0f, -1.0f, -1.0f);          // Bottom Left Of The Quad (Back)
                glVertex3f(-1.0f, -1.0f, -1.0f);          // Bottom Right Of The Quad (Back)
                glVertex3f(-1.0f, 1.0f, -1.0f);          // Top Right Of The Quad (Back)
                glVertex3f(1.0f, 1.0f, -1.0f);          // Top Left Of The Quad (Back)
                
                glColor3f(0f, 0f, 1.0f);          // Set The Color To Blue
                glVertex3f(1.0f, -1.0f, -1.0f);          // Back Bottom Left Of The Quad (Left)
                glVertex3f(1.0f, 1.0f, -1.0f);          // Back Top Left Of The Quad (Left)
                glVertex3f(1.0f, 1.0f, 1.0f);          // Front Top Left Of The Quad (Left)
                glVertex3f(1.0f, -1.0f, 1.0f);          // Front Bottom Left Of The Quad (Left)
                
                glColor3f(1.0f, 0f, 1.0f);          // Set The Color To Purple
                glVertex3f(-1.0f, -1.0f, -1.0f);          // Back Bottom Right Of The Quad (Right)
                glVertex3f(1.0f, 1.0f, -1.0f);          // Back Top Right Of The Quad (Right)
                glVertex3f(-1.0f, 1.0f, 1.0f);          // Front Top Right Of The Quad (Right)
                glVertex3f(-1.0f, -1.0f, 1.0f);          // Front Bottom Right Of The Quad (Right)
                glEnd();

    }
	// method: run
    // purpose: main loop, updates display at 60Hz while it has not been requested to close

    public void run() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            processKeyboard();
            render();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
	// method: processKeyboard
    // purpose: respond to specified keyboard inputs: see comments for response to each input

    public void processKeyboard() {
    }
	// method: printInstructions
    // purpose: Print instructions to console for user input
//Yooo
    private void printInstructions() {
        System.out.println("Controls:");
        System.out.println("ESC - Quit");
    }
	// method: main
    // purpose: entry point for program, runs the start method

    public static void main(String[] args) {
        Basic basic = new Basic();
        basic.start();
    }
}
