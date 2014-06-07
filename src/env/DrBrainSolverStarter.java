package env;

import incrementer.DrBrainForcedSlowIncrementer;
import incrementer.DrBrainIncFiltering;
import incrementer.DrBrainSimpleIncrementer;
import incrementer.SolutionIncrementer;

public class DrBrainSolverStarter {

	/**
	 * @param args[0] -> input file name
	 */
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Please put filename in args!");
			System.exit(0);
		}
		
		
		DrBrainInitialMaze maze = new DrBrainInitialMaze(args[0]);
		int main[] = new int[7];
		//int p1[] = new int[7];
		int p2[] = new int[7];
		
		//test1
		/*int main[] = new int[7];
		main[0] = 1;
		main[1] = 2;
		main[2] = 1;
		main[3] = 3;
		main[4] = 1;
		main[5] = 0;
		main[6] = 0;*/
		
		//test2
		for(int i=0; i<7; i++) {
			main[i] = 0;
		}
		/*main[0] = 3;
		main[1] = 3;
		main[2] = 3;
		main[3] = 1;
		main[4] = 1;
		main[5] = 1;
		main[6] = 0;*/
		//-1: pause
		//0: collect
		//1: action
		//2: right
		//3: left
		//4: call P2
		//5: call P1
		
		
		//testHardPuzzle1SlowIncrementer(p2, maze);
		testHardPuzzle9Filter(p2, maze);
	}

	public static void testHardPuzzleSolution(int p2[], int p1[], int main[], DrBrainInitialMaze maze) {
		//HARD PUZZLE:
				//set p2 for help:
				p2[0] = 1;
				p2[1] = 0;
				p2[2] = 1;
				p2[3] = 0;
				p2[4] = 1;
				p2[5] = 0;
				p2[6] = 3;
				
				p1[0]=0;
				p1[1]=0;
				p1[2]=4;
				p1[3]=2;
				p1[4]=4;
				p1[5]=4;
				p1[6]=4;
				
				main[0] = 5;
				main[1] = 5;
				main[2] = 1;
				main[3] = 4;
				main[4] = 2;
				main[5] = 5;
				main[6] = 5;
					
				//DrBrainMazeRunner.setInitMain(main);
				SolutionIncrementer inc = new DrBrainSimpleIncrementer(new int[7], new int[7], p2, false, true);
				DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
				runner.setInitMain(main);
				runner.setInitP1(p1);
				runner.setInitP2(p2);
				runner.runall();
				
	}
	
	public static void testHardPuzzle(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		
		
		SolutionIncrementer inc = new DrBrainSimpleIncrementer(new int[7], new int[7], p2, true, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.setInitP2(p2);
		runner.runall();
		
	}
	
	public static void testHardPuzzleDeath(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		SolutionIncrementer inc = new DrBrainSimpleIncrementer(new int[7], new int[7], p2, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	public static void testHardPuzzleFastDeath(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	public static void testHardPuzzleFilter(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		
		//start it right when it enconters a bug: FIXED! :)
		//I had index enter a function when I need m[index] to enter it.
		/*int p1[] = new int[7];
		p1[0]=0;
		p1[1]=0;
		p1[2]=4;
		p1[3]=2;
		p1[4]=4;
		p1[5]=4;
		p1[6]=4;*/
		System.out.println("testHardPuzzleFilter");
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	

	
	public static void testHardPuzzle3Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 3 filter:");
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 2;
		p2[3] = 1;
		p2[4] = 0;
		p2[5] = 1;
		p2[6] = 0;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}

	public static void testHardPuzzle3FilterSpoilers(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 3 filter:");
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 2;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	
	public static void testHardPuzzle1SlowIncrementer(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		System.out.println("Slow incrementer:");
		SolutionIncrementer inc = new DrBrainForcedSlowIncrementer(new int[7], new int[7], p2, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	public static void testHardPuzzle3SlowIncrementer(int p2[], DrBrainInitialMaze maze) {
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 2;
		p2[3] = 1;
		p2[4] = 0;
		p2[5] = 1;
		p2[6] = 0;
		System.out.println("Slow incrementer:");
		SolutionIncrementer inc = new DrBrainForcedSlowIncrementer(new int[7], new int[7], p2, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	
	public static void testHardPuzzle4Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 4 filter:");
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 2;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle4Filter2(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 4 filter:");
		p2[0] = 2;
		p2[1] = 1;
		p2[2] = 0;
		p2[3] = 1;
		p2[4] = 0;
		p2[5] = 1;
		p2[6] = 0;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle4Filter3(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 4 filter:");
		p2[0] = 3;
		p2[1] = 1;
		p2[2] = 0;
		p2[3] = 1;
		p2[4] = 0;
		p2[5] = 1;
		p2[6] = 0;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	
	public static void testHardPuzzle5Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 5 filter:");
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 3;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle5Filter2(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 5 filter:");
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 2;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle6FilterTest(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 6 filter SEEMS EZ:");
		
		System.out.println();
		p2[0] = 1;
		p2[1] = 3;
		p2[2] = 1;
		p2[3] = 1;
		p2[4] = 1;
		p2[5] = 2;
		p2[6] = 1;
		
		int p1[] = new int[7];
		p1[0] = 0;
		p1[1] = 0;
		p1[2] = 0;
		p1[3] = 0;
		p1[4] = 0;
		p1[5] = 1;
		p1[6] = 0;
		
		System.out.println("INITIAL P1:");
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(p1[i] + " ");
		}
		System.out.println();
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], p1, p2, maze, true, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle6FilterOops(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 6 filter SEEMS EZ: (oops. I messed it up lol.)");
		
		p2[0] = 1;
		p2[1] = 3;
		p2[2] = 1;
		p2[3] = 1;
		p2[4] = 1;
		p2[5] = 2;
		p2[6] = 1;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, true, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle6Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 6 filter SEEMS EZ:");
		
		p2[0] = 1;
		p2[1] = 3;
		p2[2] = 1;
		p2[3] = 1;
		p2[4] = 2;
		p2[5] = 1;
		p2[6] = 0;
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, true);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	//TODO: consider the bug as an obstacle?
	public static void testHardPuzzle7Filter(DrBrainInitialMaze maze) {
		System.out.println("Problem 7 filter: (I don't even know what P2 should be. *facepalm*");
		
		
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], new int[7], maze, false, false);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
	}
	
	
	public static void testHardPuzzle8Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 8 filter SEEMS EZ:");
		/* FAIL:
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 1;
		p2[3] = 0;
		p2[4] = 1;
		p2[5] = 0;
		p2[6] = 1;
		*/
		//Another fail:
		/*p2[0] = 1;
		p2[1] = 1;
		p2[2] = 0;
		p2[3] = 1;
		p2[4] = 0;
		p2[5] = 1;
		p2[6] = 0;
		*/
		System.out.println("Problem 8 continued.");
		//continued
		p2[0] = 1;
		p2[1] = 0;
		p2[2] = 0;
		p2[3] = 3;
		p2[4] = 3;
		p2[5] = 3;
		p2[6] = 3;
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], p2, maze, false, false);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
	
	public static void testHardPuzzle9Filter(int p2[], DrBrainInitialMaze maze) {
		System.out.println("Problem 9 filter SEEMS hard:");
		
	
		SolutionIncrementer inc = new DrBrainIncFiltering(new int[7], new int[7], new int[7], maze, false, false);
		DrBrainMazeRunner runner = new DrBrainMazeRunner(maze, inc);
		runner.runall();
		
		
	}
}
