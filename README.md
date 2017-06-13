# maze-evolve
Maze generation using Genetic Algorithms

## Purpose ## 

This Java program is designed to generate NxN dimensional mazes based upon the JGAP library and Genetic Algorithms principals. These mazes are designed to be difficult to solve, while avoiding use of _Standard_ techniques for maze generation due to their propensity to potentially lead to patterns or noticeable flaws to humans. 

## Definition of terms and Design decisions ## 

Maze Position: The maze begins at the top left square and ends at the bottom right. 

Density (of a maze): Throughout the comments and in this readme the density of a maze refers to the walls per area of a maze, a higher                          density means more walls for a given maze size.

The key selection pressures of these mazes are: 
* The solvability of the maze (unsolvable mazes are pressured to be less dense) 
* Density of the maze given that there exists a solution 
* The number of steps an A\* implementation with the heuristic of euclidean distance to the goal. 

