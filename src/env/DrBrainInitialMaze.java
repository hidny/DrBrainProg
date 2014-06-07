package env;

import java.io.*;
import java.util.Scanner;

//get the DrBrain Maze from an input file:

public class DrBrainInitialMaze {
	
	private char maze[][];
	
	//NOTE: always start pointing right
	/*//TODO fill in constants with meaningful names? Nah...

	public static final char start = 'S';
	public static final char brain = 'B';
	public static final char empty = ' ';
	public static final char death = 'D';
	
	public static final char gamble = 'G';
	
	public static final char startBrain = 'R';
	*/
	
	
	
	
	public static void main(String args[]) {
		//use args[0] later
		DrBrainInitialMaze maze = new DrBrainInitialMaze("D:\\SpartanWorkplace\\DrBrainProg\\input");
		System.out.println("Dr Brain always points to the right (" + maze.getInitialOrientation() +")");
		//maze = new DrBrainMaze("input");
	}
	
	//pre: filename with maze format
	public DrBrainInitialMaze(String filename) {
		String curLine;
		try {
		
			Scanner in = new Scanner(new File(filename));
			
			//first line is just an explanation:
			if(in.hasNextLine()) {
				curLine = in.nextLine();
				//System.out.println(curLine);
			}
			
			if(in.hasNextLine()) {
				curLine = in.nextLine();
				//System.out.println(curLine);
			}
			
			int numColumns = in.nextInt();
			//System.out.println(numColumns);
			int numRows = in.nextInt();
			//System.out.println(numRows);
		
			maze = new char[numRows][numColumns];
			
			if(in.hasNextLine()) {
				curLine = in.nextLine();
				System.out.println(curLine);
			}
			
			for(int i=0; i<numRows; i++) {
				if(in.hasNextLine()) {
					curLine = in.nextLine();
					System.out.println(curLine);
					
					if(curLine.length() >= numColumns) {
						//read the row:
						for(int j=0; j<numColumns; j++) {
								maze[i][j] = curLine.charAt(j);
						}
					} else {
						//skip this line because there's nothing on it.
						i--;
					}
				}
					
			}
			
			in.close();
		} catch (Exception e) {
			System.err.print("Error: could not read file");
			System.exit(0);
		}
	
	}
	
	public char[][] getHardCopyInitialMazeWithoutBrain() {
		char newmaze[][] = new char[maze.length][maze[0].length];
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[0].length; j++) {
				newmaze[i][j] = maze[i][j];
				if(newmaze[i][j] == 'S') {
					newmaze[i][j] = ' ';
				} else if(newmaze[i][j] == 'R') {
					newmaze[i][j] = 'B';
				
				//MAYBE: don't just ignore gamble zone.
				} else if(newmaze[i][j] == 'G') {
					newmaze[i][j] = ' ';
				}
			}
		}
			
		return newmaze;
	}
	
	//returns intial x pos. note top left is 0,0
	public int getInitialPosX() {
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[0].length; j++) {
				if(maze[i][j] == 'S' || maze[i][j] == 'R') {
					return j;
				}
			}
		}
		return -1;
	}
	
	//returns intial y pos. note top left is 0,0
	public int getInitialPosY() {
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[0].length; j++) {
				if(maze[i][j] == 'S' || maze[i][j] == 'R') {
					return i;
				}
			}
		}
		return -1;
	}
	
	//for now: start it on the right all the time(right = 0)
	//MAYBE: change this?
	public int getInitialOrientation() {
		return 0;
	}
		
}
	/*
	 * 
Brain Maze first Test

11 7

12345678901
23456789012
34567890123
45678901234


	 */
