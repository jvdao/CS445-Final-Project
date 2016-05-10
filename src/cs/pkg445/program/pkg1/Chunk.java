/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.pkg445.program.pkg1;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author ianberger
 */
public class Chunk {
	
	static final int CHUNK_SIZE = 30;
	static final int CUBE_LENGTH = 2;
	private Block[][][] blocks;
	private int VBOVertexHandle;
	private int VBOColorHandle;
	private int startX, startY, startZ;
	private Random r;
	
	private int VBOTextureHandle;
	private Texture texture;
	
	public void render(){
		glPushMatrix();
		
		glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
		glVertexPointer(3, GL_FLOAT, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
		glColorPointer(3, GL_FLOAT, 0, 0L);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
		glBindTexture(GL_TEXTURE_2D, 1);
		glTexCoordPointer(2, GL_FLOAT, 0, 0L);
		
		glDrawArrays(GL_QUADS, 0, CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE*24);
		
		glPopMatrix();
	}
	public void rebuildMesh(float startX, float startY, float startZ){
		VBOColorHandle = glGenBuffers();
		VBOVertexHandle = glGenBuffers();
		VBOTextureHandle = glGenBuffers();
		FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE)*6*12);
		FloatBuffer vertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE)*6*12);
		FloatBuffer vertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE)*6*12);
		for(float x=0; x<CHUNK_SIZE; x++)
			for(float z=0; z<CHUNK_SIZE; z++)
				for(float y=0; y<CHUNK_SIZE; y++){
					vertexPositionData.put(createCube((float)(startX+x*CUBE_LENGTH), (float)(startY+y*CUBE_LENGTH+(int)(CHUNK_SIZE*.8)), (float)(startZ+z*CUBE_LENGTH)));
					vertexColorData.put(createCubeVertexCol(getCubeColor(blocks[(int)x][(int)y][(int)z])));
					vertexTextureData.put(createTexCube(0f, 0f, blocks[(int)x][(int)y][(int)z]));
				}
		vertexColorData.flip();
		vertexPositionData.flip();
		vertexTextureData.flip();
		glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexPositionData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexColorData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexTextureData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	private float[] createCubeVertexCol(float[] cubeColorArray){
		float[] cubeColors = new float[cubeColorArray.length*4*6];
		for(int i=0; i<cubeColors.length; i++)
			cubeColors[i] = cubeColorArray[i%cubeColorArray.length];
		return cubeColors;
	}
	public static float[] createTexCube(float x, float y, Block block){
		float offset = (1024f/16)/1024f;
		
		switch(block.getId()){
			case 0://Grass
				return new float[]{
					//TOP QUAD(DOWN=+Y)
					x+offset*3, y+offset*10,//green wool for top of grass
					x+offset*2, y+offset*10,
					x+offset*2, y+offset*9,
					x+offset*3, y+offset*9,
					//BOTTOM QUAD
					x+offset*3, y+offset*1,
					x+offset*2, y+offset*1,
					x+offset*2, y+offset*0,
					x+offset*3, y+offset*0,
					//FRONT QUAD
					x+offset*3, y+offset*0,
					x+offset*4, y+offset*0,
					x+offset*4, y+offset*1,
					x+offset*3, y+offset*1,
					//BACK QUAD
					x+offset*4, y+offset*1,
					x+offset*3, y+offset*1,
					x+offset*3, y+offset*0,
					x+offset*4, y+offset*0,
					//LEFT QUAD
					x+offset*3, y+offset*0,
					x+offset*4, y+offset*0,
					x+offset*4, y+offset*1,
					x+offset*3, y+offset*1,
					//RIGHT QUAD
					x+offset*3, y+offset*0,
					x+offset*4, y+offset*0,
					x+offset*4, y+offset*1,
					x+offset*3, y+offset*1};
			case 1://Sand
				return sameTextureOnAllSides(x, y, offset, 2, 1);
			case 2://Water
				return sameTextureOnAllSides(x, y, offset, 14, 0);
			case 3://Dirt
				return sameTextureOnAllSides(x, y, offset, 2, 0);
			case 4:
				return sameTextureOnAllSides(x, y, offset, 1, 0);
			case 5:
				return sameTextureOnAllSides(x, y, offset, 1, 1);
		}
		throw new RuntimeException("No texture mapping for block id: " + block.getId());
	}
	private static float[] sameTextureOnAllSides(float x, float y, float offset, float left, float top){
		float right = left+1;
		float bottom = top+1;
		return new float[]{
			//TOP QUAD(DOWN=+Y)
			x+offset*right, y+offset*bottom,
			x+offset*left, y+offset*bottom,
			x+offset*left, y+offset*top,
			x+offset*right, y+offset*top,
			//BOTTOM QUAD
			x+offset*right, y+offset*top,
			x+offset*left, y+offset*top,
			x+offset*left, y+offset*bottom,
			x+offset*right, y+offset*bottom,
			//FRONT QUAD
			x+offset*left, y+offset*bottom,
			x+offset*right, y+offset*bottom,
			x+offset*right, y+offset*top,
			x+offset*left, y+offset*top,
			//BACK QUAD
			x+offset*right, y+offset*top,
			x+offset*left, y+offset*top,
			x+offset*left, y+offset*bottom,
			x+offset*right, y+offset*bottom,
			//LEFT QUAD
			x+offset*left, y+offset*bottom,
			x+offset*right, y+offset*bottom,
			x+offset*right, y+offset*top,
			x+offset*left, y+offset*top,
			//RIGHT QUAD
			x+offset*left, y+offset*bottom,
			x+offset*right, y+offset*bottom,
			x+offset*right, y+offset*top,
			x+offset*left, y+offset*top};
	}
	
	public static float[] createCube(float x, float y, float z){
		int offset = CUBE_LENGTH/2;
		return new float[]{
			//TOP QUAD
			x+offset, y+offset, z,
			x-offset, y+offset, z,
			x-offset, y+offset, z-CUBE_LENGTH,
			x+offset, y+offset, z-CUBE_LENGTH,
			//BOTTOM QUAD
			x+offset, y-offset, z-CUBE_LENGTH,
			x-offset, y-offset, z-CUBE_LENGTH,
			x-offset, y-offset, z,
			x+offset, y-offset, z,
			//FRONT QUAD
			x+offset, y+offset, z-CUBE_LENGTH,
			x-offset, y+offset, z-CUBE_LENGTH,
			x-offset, y-offset, z-CUBE_LENGTH,
			x+offset, y-offset, z-CUBE_LENGTH,
			//BACK QUAD
			x+offset, y-offset, z,
			x-offset, y-offset, z,
			x-offset, y+offset, z,
			x+offset, y+offset, z,
			//LEFT QUAD
			x-offset, y+offset, z-CUBE_LENGTH,
			x-offset, y+offset, z,
			x-offset, y-offset, z,
			x-offset, y-offset, z-CUBE_LENGTH,
			//RIGHT QUAD
			x+offset, y+offset, z,
			x+offset, y+offset, z-CUBE_LENGTH,
			x+offset, y-offset, z-CUBE_LENGTH,
			x+offset, y-offset, z
		};
	}
	private float [] getCubeColor(Block block){
		return new float[]{1,1,1};
//		switch(block.getId()){
//			case 0:
//				return new float[]{0,1,0};//Grass - Green
//			case 1:
//				return new float[]{1, 250/255f, 180/255f};//Sand - Light Tan
//			case 2:
//				return new float[]{0,0,1};//Water - Blue
//			case 3:
//				return new float[]{139/255f, 90/255f, 43/255f};//Dirt - Brown
//			case 4:
//				return new float[]{.5f,.5f,.5f};//Stone - Grey
//			case 5:
//				return new float[]{0,0,0};//Bedrock - Black
//		}
//		return new float[]{1,1,1};//Default - White
	}
	public Chunk(int startX, int startY, int startZ){
		try{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("terrain.png"));
		}catch(Exception e){
			System.out.println("terrain.png not found");
		}
		
		r = new Random();
		blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
		for(int x=0; x<CHUNK_SIZE; x++){
			for(int y=0; y<CHUNK_SIZE; y++){
				for(int z=0; z<CHUNK_SIZE; z++){
					float randomValue = r.nextFloat();
					if(randomValue>5/6f){
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
					}else if(randomValue>4/6f){
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Sand);
					}else if(randomValue>3/6f){
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
					}else if(randomValue>2/6f){
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
					}else if(randomValue>1/6f){
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
					}else{
						blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
					}
				}
			}
		}
		VBOColorHandle = glGenBuffers();
		VBOVertexHandle = glGenBuffers();
		VBOVertexHandle = glGenBuffers();
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
		rebuildMesh(startX, startY, startZ);
	}
}
