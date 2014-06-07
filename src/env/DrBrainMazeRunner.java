package env;

import incrementer.SolutionIncrementer;

//this class should hold the code,
// the maze and the current pos of Dr. Brain.
//outside progs should only need to run runStaticP2 and set init M/ set init P1.

public class DrBrainMazeRunner {
	
	
	//Dr Brain
	//posx
	//posy
	//facing  0 ->,    1  up,  2 <-,  3->
	
	//maze: what you get from DrBrainInitial Maze without S and R(Brain on a Brain).
	
	//code: set of instructions (for now, we won't use pause because pause is almost the same as collect
	//-1: pause
	//0: collect
	//1: action
	//2: right
	//3: left
	//4: call P2
	//5: call P1
	
	//TODO: test on med by having p2 be pickup x 7.
	
	//Algo:
		//1. get initial config of position and start the main code 
		// and start all command sets to 0 unless user wants to initialize to something else.
	
	//TEST: 2. first do a basic check to see if it's even logically possible to make it... 
		//1. Is there enough movement?
		//2. Is there enough pickups?
			//TEST: find a clever way to get to the next possible solution.
	
		//3.run the main code.
			
			//case 1: When fail.
			//key: change the last command in the main code before the crash.
		
			//if all of m has been checked. i.e. m[7] = 5, 5, 5, 5, 5, 5, 5
				//change last command in p[1].
				//if last command in p[1] is 4: change it to 0 and increment the one left... and so one.
		
			//if all of p1 has been checked (for now: just tell programmer it doesn't work.)
				//change last command in p[2].
				//if last command in p[1] is 43: change it to 0 and increment the one left... and so one.
	
			//if all of p2 has been checked, all possible solutions have been checked.
	
			//case 2: all brains are picked up
				//show the solution and pause. :)

	//repeat

	private DrBrainInitialMaze initMaze;
	private char curMaze[][];
	private int x = -1;
	private int y = -1;
	private int orient = -1;
	
	//soft copies of the functions in SolutionIncrementer:
	private int m[];
	private int p1[];
	private int p2[];
	
	
	
	public static int NUM_COMMANDS_PER_FUNCTION = 7;
	//pre: maze is the proper format and init commands follow the rules of input.
			//p2: 0-3
			//p1: 0-4
			//m: 0-5
	
	//post: runs all possible DrBrain solution and prints the ones that work.
	//		this will cycle through Main, then P1, then finally P2.
	
	
	SolutionIncrementer inc;
	
	public DrBrainMazeRunner(DrBrainInitialMaze maze, SolutionIncrementer inc) {
		setInitMazePosAndOrientation(maze);
		

		this.inc = inc;
		m = inc.getM();
		p1 = inc.getP1();
		p2 = inc.getP2();
		
		int minNumMoves = DrBrainBasicMazeFunctions.getLowerBoundOnNumMoves(curMaze, x, y);
		
		//Print description:
		System.out.println("Maze description:");
		System.out.println("Number of brains:");
		System.out.println(countNumBrains(curMaze));
		System.out.println("you have to make at least " + minNumMoves + " action moves");
		System.out.print("Initial P2:  ");
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(p2[i] + " ");
		}
		System.out.println();
		//End print desc
	}
	
	public static int LAST_COMMAND_INDEX = NUM_COMMANDS_PER_FUNCTION - 1;
	
	
	public void runall(){
		int ret = 0;
		int status = 0;
		
		int indexOfCrash = 0;
		do {
			setInitMazePosAndOrientation();
			
			//run main:
			for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
				//FOR TESTING:
				//printSolution();
				
				ret = doCommand(m[i]);
				/*System.out.println("after main command #" + (i+1) + ":");
				printStatus();*/
				
				if( ret != 0) {
					//TODO: test the skip when crash algo.
					
					indexOfCrash = i;
					
					break;
				}
			}
			
			//Check to see if there is a solution.
			if(countNumBrains(curMaze) == 0) {
				printSolution();
				printStatus();
			}
			
			//Increment command:
			//Change last command in main so we could (eventually) go through all possible programs:
			if(ret ==0) {
				status = inc.incrementMain(LAST_COMMAND_INDEX);
			
			//Change the line in main where it crashed:
			} else {
				status = inc.incrementMain(indexOfCrash);
			}
			
			if(status == SolutionIncrementer.TEST_CHANGED_P1) {
				//printSolution();
			}
			//FOR TESTING:
			//printStatus();
		} while(status != SolutionIncrementer.ALL_POSSIBILITIES_CHECKED);
		
		
		
	}
	
	//pre: if command is 4, don't run from p2.
	//if command is 5, don't run from p1 or p2. (or else infinite loop)
	//post: runs the command
	private int doCommand(int command) {
		int ret = 0;
		switch(command) {
			case 0:
				pickup();
				break;
				
			case 1:
				ret = goStraight();
				break;
				
			case 2:
				turnRight();
				break;
				
			case 3:
				turnLeft();
				break;
			case 4:
				for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
					ret = doCommand(p2[i]);
					if(ret == -1) {
						break;
					}
				}
				break;
				
			case 5:
				for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
					ret = doCommand(p1[i]);
					if(ret == -1) {
						break;
					}
					/*System.out.println("after P1: step #" + (i+1) + ":");
					printStatus();*/
				}
				break;
		}
		
		return ret;
	}
	
	//0,0------------------------>
	// *
	// *
	// *
	// V
	//moves straight according to orientation
	//returns -1 if it hits a dead zone.
	private int goStraight() {
		if (orient % 4 == 0) {
			x++;
		} else if(orient % 4 == 1) {
			y--;
		} else if(orient % 4 == 2) {
			x--;
		} else if(orient % 4 == 3) {
			y++;
		}  else {
			System.out.println("ERROR: orientation unknown!");
			System.out.println(orient + "?");
			System.exit(0);
		}
		
		
		handleMovingIntoBorders();
		
		if(curMaze[y][x] == 'D') {
			return -1;
		} else {
			return 0;
		}
	}
	
	private void turnRight() {
		orient--;
		if(orient < 0) {
			orient +=4;
		}
	}
	
	private void turnLeft() {
		orient++;
		if(orient > 3) {
			orient -=4;
		}
	}
	
	private void pickup() {
		curMaze[y][x] = ' ';
	}
	
	
	//code to handle moving into borders. (The borders act like a wall)
	private void handleMovingIntoBorders() {
		if(x < 0) {
			x++;
		} else if(y < 0) {
			y++;
		} else if(x >= curMaze[0].length) {
			x--;
		} else if(y >= curMaze.length) {
			y--;
		}
	}
	
	
	private void printSolution() {
		System.out.println("Solution:");
		System.out.print("M:  ");
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(m[i] + " ");
		}
		System.out.println();
		
		System.out.print("P1:  ");
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(p1[i] + " ");
		}
		System.out.println();
		
		System.out.print("P2:  ");
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(p2[i] + " ");
		}
		System.out.println();
		
		//TESTING! AAHHH this may cause a bug.
		/*System.out.println("Found 1 solution. Stopping here!");;
		System.exit(0);*/
	}
	
	//FOR TESTING:
	public void setInitMain(int initM[]) {
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			m[i] = initM[i];
		}
	}
	
	//FOR TESTING:
	public void setInitP1(int initP1[]) {
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			p1[i] = initP1[i];
		}
	}
	
	//For checking p2s:
	public void setInitP2(int initP2[]) {
		for(int i=0; i<NUM_COMMANDS_PER_FUNCTION; i++) {
			p2[i] = initP2[i];
		}
	}
	
	//gets the make original maze from DrBrainInitialMaze config
	//to make a maze we can work with models it before the intial prog starts.
	public void setInitMazePosAndOrientation(DrBrainInitialMaze maze) {
		initMaze = maze;
		setInitMazePosAndOrientation();
	}
	
		
	public void setInitMazePosAndOrientation() {
		curMaze = initMaze.getHardCopyInitialMazeWithoutBrain();
		x = initMaze.getInitialPosX();
		y = initMaze.getInitialPosY();
		orient = initMaze.getInitialOrientation();
		
	}
	
	
	public void printStatus() {
		System.out.println("(x,y) = (" + x + ", " + y + ")");
		System.out.println("Orientation: " + orient);
		
		for(int i=0; i<curMaze.length; i++) {
			for(int j=0; j<curMaze[0].length; j++) {
				System.out.print(curMaze[i][j]);
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	
	//post: returns the number of brains in the current maze:
	private static int countNumBrains(char curMaze[][]) {
		int count = 0;
		for(int i=0; i<curMaze.length; i++) {
			for(int j=0; j<curMaze[0].length; j++) {
				if(curMaze[i][j] == 'B') {
					count++;
				}
			}
		}
		return count;
		
	}

	public int[] getP1() {
		return p1;
	}


	public int[] getP2() {
		return p2;
	}

}
