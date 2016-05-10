/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.pkg445.program.pkg1;

/**
 *
 * @author ianberger
 */
public class Block {
	private boolean isActive;
	private BlockType type;
	private float x,y,z;
	public enum BlockType{
		BlockType_Grass(0),
		BlockType_Sand(1),
		BlockType_Water(2),
		BlockType_Dirt(3),
		BlockType_Stone(4),
		BlockType_Bedrock(5);
		
		private int blockId;
		BlockType(int id) {
			blockId = id;
		}
		public int getId(){
			return blockId;
		}
		public void setId(int id){
			blockId = id;
		}
	}
	
	public Block(BlockType type){
		this.type = type;
	}
	public void setCoords(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public boolean isActive(){
		return isActive;
	}
	public void setActive(boolean active){
		isActive = active;
	}
	public int getId(){
		return type.getId();
	}
}
