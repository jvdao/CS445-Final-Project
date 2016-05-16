/***************************************************************
* file: Block.java
* author: J. Dao, P. Santos, I. Berger
* class: CS 445 - Computer Graphics
*
* assignment: Quarter Project
* date last modified: 5/16/2016
*
* purpose: Block class that stores a blocks ID which determines its type based 
* on the enum BlockType, as well as its 3D coordinates in the world, and whether 
* it is active or in the world.
****************************************************************/ 
package cs.pkg445.program.pkg1;

public class Block {
	private boolean isActive;
	private BlockType type;
	private float x,y,z;
	//Enumeration of block type based on block IDs
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
		// method: getID
		// purpose: return block's id
		public int getId(){
			return blockId;
		}
		// method: setID
		// purpose: set block's id
		public void setId(int id){
			blockId = id;
		}
	}
	// Constructor: Block
    // purpose: intializes variables 
	public Block(BlockType type){
		this.type = type;
		isActive = true;
	}
	// method: setCoords
	// purpose: set block's coordinates in the world
	public void setCoords(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	// method: isActive
	// purpose: returns if the block is set to be active or in the world
	public boolean isActive(){
		return isActive;
	}
	// method: setActive
	// purpose: set if the block is to be active or in the world
	public void setActive(boolean active){
		isActive = active;
	}
	// method: getId
	// purpose: return block's ID based on enum BlockType
	public int getId(){
		return type.getId();
	}
}
