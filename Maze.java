package ridley.alistair.maze;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;

public class Maze {

	/* Constants for maze generation */ 
	public static final int MAZE_WIDTH = 20; 
	public static final int MAZE_HEIGHT = 20; 
	public static final int MAX_ALLOWED_EVOLUTIONS = 1000; 

	public static void main(String[] args) throws Exception { 
		
		/* Perform configuration setup */ 
		Configuration conf = new DefaultConfiguration();
		FitnessFunction ff = new MazeFitnessFunction(); 
		conf.setFitnessFunction(ff);
		
		/* Create sample gene */ 
		
		/* Each gene consists of an array of booleans of size 2*area of the maze, these will determine the ability of a maze at a given point to allow traversal 
		 * to each given squares neighbour to the north and east respectively. 
		 */
		Gene[] sample = new Gene[2 * MAZE_WIDTH * MAZE_HEIGHT]; 
		for(int i = 0; i < sample.length; i++){
			sample[i] = new BooleanGene(conf);
		}

		Chromosome sampleChromosome = new Chromosome(conf, sample);
		conf.setSampleChromosome(sampleChromosome);
		
		/* Initialise random population */ 
		conf.setPopulationSize(70);
		Chromosome[] chromosomes = new Chromosome[conf.getPopulationSize()];
		/* Initialise each chromosome */ 
		for(int i = 0; i < chromosomes.length; i++){
			/* Initialise each chromsome's gene */
			Gene[] genes = new Gene[2 * MAZE_WIDTH * MAZE_HEIGHT];
			for(int j = 0; j < genes.length; j++){
				genes[j] = new BooleanGene(conf);
				genes[j].setAllele(Math.random()>0.7); //Each boolean is set to a random value (weighted to being unable to traverse since good mazes are typically dense)
			}
			chromosomes[i] = new Chromosome(conf, sample);
			chromosomes[i].setGenes(genes);
		}
		
		/* Population is created with the given chromosomes */ 
		@SuppressWarnings("deprecation")
		Genotype population = new Genotype(conf, chromosomes);
		
		System.out.println("Starting");
		for(int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++){
			/* Evolve the population */ 
			population.evolve();
			
			/* Print statistics about the population */ 
		    System.out.println("BEST VALUE FOR POPULATION " + i + ": " + population.getFittestChromosome().getFitnessValue());
			IChromosome fittest = population.getFittestChromosome();
			if(i%40==0){
				BuildMaze.build(get3dBool(fittest),String.format("Maze%d.jpg", i)); //For every 40 mazes print an image of the maze
			}
		}
	}

	
	/* A method to convert a chromosome into its corresponding maze */ 
	public static boolean[][][] get3dBool(IChromosome ic){
		//Build maze
		boolean[][][] maze = new boolean[Maze.MAZE_WIDTH][Maze.MAZE_HEIGHT][4]; 
		for(int i = 0; i < Maze.MAZE_WIDTH*Maze.MAZE_HEIGHT; i++){
			for(int j = 0; j < 2; j++){
				maze[i%Maze.MAZE_WIDTH][i/Maze.MAZE_WIDTH][j] = ((BooleanGene) ic.getGene((2*i) + j)).booleanValue();
			} 
			if(i > 0){
				for(int j = 2; j < 4; j++){
					maze[i%Maze.MAZE_WIDTH][i/Maze.MAZE_WIDTH][j] = ((BooleanGene) ic.getGene((2*(i-1)) + (j-2))).booleanValue();
				}
			}
		}

		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[i].length; j++){
				//If at north can't go north
				if(j==0){
					maze[i][j][0] = false;
				}
				//If at east can't go east
				if(i==Maze.MAZE_WIDTH-1){
					maze[i][j][1] = false;
				}
				//If at south can't go south 
				if(j==Maze.MAZE_HEIGHT-1){
					maze[i][j][2] = false;
				}
				//If at west can't go west 
				if(i==0){
					maze[i][j][3] = false;
				}

			}
		}
		return maze;
	}
}
