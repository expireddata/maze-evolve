package ridley.alistair.maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BuildMaze {
	/* This class defines how the mazes are drawn */ 
	public static final int SQUARE_SIZE = 64; 
	public static final int width = Maze.MAZE_WIDTH * SQUARE_SIZE; 
	public static final int height = Maze.MAZE_HEIGHT * SQUARE_SIZE; 
	public static final int BLACK = Color.BLACK.getRGB();
	public static final int WHITE = Color.WHITE.getRGB();
	
	
	public static void build(boolean[][][] maze, String fileName) throws IOException{
		
		/* Create a buffered image in which to store the pixel data of the maze */ 
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				//First colour in the walls and centre spaces 
				if(j%SQUARE_SIZE<=2 || i%SQUARE_SIZE<=2 || j%SQUARE_SIZE>=SQUARE_SIZE-3 || i%SQUARE_SIZE>=SQUARE_SIZE-3){ 
					bi.setRGB(i, j, BLACK);
				} else { 
					bi.setRGB(i, j, WHITE);
				}
			}
		}
		
		//Then iterate through the given maze, if a path is open it will have its corresponding wall recolured in white
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length; j++){
				if(maze[i][j][0]||(j>0&&maze[i][j-1][2])){ //Check north and south connections 
					
					/* Set corresponding wall to white */ 
					for(int x = (i*SQUARE_SIZE)+3; x < ((i+1)*SQUARE_SIZE)-3; x++){
						for(int y = (j*SQUARE_SIZE)-4; y < (j*SQUARE_SIZE)+4; y++){
							try {
								bi.setRGB(x, y, WHITE);
							} catch(Exception e){
								
							}
						}
					}
				}
				
				if(maze[i][j][1]||(i<(Maze.MAZE_WIDTH-1)&& maze[i+1][j][3])){ //Check east and west connections 
					
					/* Set corresponding wall to white */
					for(int y = (j*SQUARE_SIZE)+3; y < ((j+1)*SQUARE_SIZE)-3; y++){
						for(int x = ((i+1)*SQUARE_SIZE)-4; x < ((i+1)*SQUARE_SIZE)+4; x++){
							try {
								bi.setRGB(x, y, WHITE);
							} catch(Exception e){
								
							}
						}
					}
				}
			}
		}
		ImageIO.write(bi, "jpg", new File(fileName));
	}
}
