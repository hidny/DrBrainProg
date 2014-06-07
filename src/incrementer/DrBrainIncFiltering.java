package incrementer;

import env.DrBrainInitialMaze;
import env.DrBrainMazeRunner;

import env.DrBrainBasicMazeFunctions;

public class DrBrainIncFiltering extends DrBrainSimpleIncrementer {
	

	private static int NULL_COMMAND = -1;
	//************Optimization tricks in MAIN prog:
	//Hopefully these tricks will make it go faster by skiping many bad possible solutions.
	
	private int minNumPickupsYouHavetoMake;
	//min moves you have to make in a specific maze.
	//Look at comments over getLowerBoundOnNumMoves function for details
	private int minNumMovesYouHaveToMake;
	
	public int getMinNumPickupsYouHavetoMake() {
		return minNumPickupsYouHavetoMake;
	}

	public int getMinNumMovesYouHaveToMake() {
		return minNumMovesYouHaveToMake;
	}
	

	
	
	//index 0 is for main, 1 is for P1 and 2 is for P2
	private int currentNumBrainsMightPickup[] = new int[3];
	private int currentNumForwards[] = new int [3];
	
	
	//this is change when P1 just got setup or change
	//it's there to indicate that the prog should check for short cuts in the first iteration.
	private boolean firstIncwithNewP1;
	
	private static final int MAIN = 0;
	private static final int P1 = 1;
	private static final int P2 = 2;
	
	private static final int INITIAL_SKIP_TRIAL_INDEX = -1;
		
	
	
	public DrBrainIncFiltering(int m[], int p1[], int p2[], DrBrainInitialMaze maze, boolean p1IsConst, boolean p2IsConst) {
		this(m, p1, p2, maze.getHardCopyInitialMazeWithoutBrain(), maze.getInitialPosX(), maze.getInitialPosY(), p1IsConst, p2IsConst);
	}
	
	//TODO: make hard copies of the arrays.
	public DrBrainIncFiltering(int m[], int p1[], int p2[], char curMaze[][], int startX, int startY, boolean p1IsConst, boolean p2IsConst) {
		super(m, p1, p2, p1IsConst, p2IsConst);
		
		System.out.println("Dr Brain Filtering");
		countMinNumberOfMovesOfEachTypeForMaze(curMaze, startX, startY);
		
		//indicate that it's the first increment run so it can update # pickups and # action for P1 & P2:
		firstIncwithNewP1 = true;
		
		
	}

	//pre: for now curMaze can't have S or R.
	private void countMinNumberOfMovesOfEachTypeForMaze(char curMaze[][], int startX, int startY) {
		minNumPickupsYouHavetoMake = DrBrainBasicMazeFunctions.countNumBrains(curMaze);
		minNumMovesYouHaveToMake = DrBrainBasicMazeFunctions.getLowerBoundOnNumMoves(curMaze, startX, startY);
	}
		
	//Idea: every time P1 & P2 changes, this function will count the max # of brain picks,
	//# of straights and # of turns to make sure it's even logically possible
	//every time main gets updated, instead of a recount, these functions will cleverly do an update.
	
	//Every time P1 or P2 gets an update, we will have to update the countss
	private void updateCountNumpickupsAndActions() {
		currentNumBrainsMightPickup[P2] = countNumPickupsFunctionP1P2MightDo(p2);
		currentNumBrainsMightPickup[P1] = countNumPickupsFunctionP1P2MightDo(p1);
		currentNumBrainsMightPickup[MAIN] = countNumPickupsMainMightDo(m);
		
		currentNumForwards[P2] = countNumForwardsFunctionMightDo(p2);
		currentNumForwards[P1] = countNumForwardsFunctionMightDo(p1);
		currentNumForwards[MAIN] = countNumForwardsFunctionMightDo(m);
	}
	
	
	//pre: count num pickups was already called for P2 then P1.
	//post:
	//simple count of # of pickups in main so it could be easily dynamically changed
	// as the steps in main get changed.
	//If you use this function for P1 and P2, the filtering by pickups won't be as effective.
	//this can be used to check for bugs when compared to the more effective/less
	// safe count method for P1/P2
	private int countNumPickupsMainMightDo(int commands[]) {
		int count = 0;
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			switch(commands[i]) {
			case 0:
				//DO NOT CHANGE THIS!!!
				count++;
				break;		
			case 4:
				count+=currentNumBrainsMightPickup[P2];
				break;
				
			case 5:
				count+=currentNumBrainsMightPickup[P1];
				break;
			}
		}
		return count;
	}
	
	
	private int countNumPickupsFunctionP1P2MightDo(int commands[]) {
		return countNumPickupsFunctionP1P2MightDo(commands, true);
	}
	
	//pre: function gets called for P2, then P1
	private int countNumPickupsFunctionP1P2MightDo(int commands[], boolean standingOnPossibleTreasure) {
		int count = 0;
		
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			switch(commands[i]) {
			case 0:
				if(standingOnPossibleTreasure) {
					count++;
				}
				standingOnPossibleTreasure = false;
				break;
				
			case 1:
				standingOnPossibleTreasure = true;
				break;
			
				//At this point, we know we're running this function for P1:
			case 4:
					//these functions may be called 5 more times than they need to be
					// but who cares? I certainly don't.
				count+=countNumPickupsFunctionP1P2MightDo(p2, standingOnPossibleTreasure);
				standingOnPossibleTreasure = doesP2endsWithStandingOnPossibleTreasure(standingOnPossibleTreasure);
				break;
				
			case 5:
				System.out.println("Error: countNumPickupsFunctionP1P2MightDo was called by Main or P1/P2 calls command #5(run P2)");
				System.exit(0);
				break;
			}
		}
		return count;
	}
	
	//post: determine if the location DrBrain lands on at the end of P2 could have a brain.
	private boolean doesP2endsWithStandingOnPossibleTreasure(boolean standingOnPossibleTreasure) {
		
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			switch(p2[i]) {
			case 0:
					if(standingOnPossibleTreasure) {
						standingOnPossibleTreasure = false;
					}
				break;
				
			case 1:
				standingOnPossibleTreasure = true;
				break;
			}
		}
		return standingOnPossibleTreasure;
	}
		
	//pre: function gets called for P2, then P1, then Main
	private int countNumForwardsFunctionMightDo(int commands[]) {
		int count = 0;
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			switch(commands[i]) {
			case 1:
				count++;
				break;		
			case 4:
				count+=currentNumForwards[P2];
				break;
				
			case 5:
				count+=currentNumForwards[P1];
				break;
			}
		}
		return count;
	}
	
	
	//pre: 1. Min number of moves and currentNumBrainsMightPickup is calculated for current Maze
		//2. currentNumForwards is updated for current M, P1, P2
		//3. currentNumBrainsMightPickup is updated for current M, P1, P2	
	
	// 		assume that a pickup is counted in currentNumBrainsMightPickup no matter what.
	//		assume that currentNumBrainsMightPickup and currentNumForwards is updated
	//post: skips obvious bad answers based on #pickups and actions we do as we go through the solutions
	public int incrementMain(int index) {
		int ret = incrementMainNoSkip(index);
		
		//if ret == 0, it's just another boring iteration,
		//so check if the next iteration is even logically possible:
		if(ret == 0) {
			ret = skipFromIndexToLastLineOfMainUntilpossibleSolutionFound(index);
		} 
		
		//while we don't have a main prog that's logically possible
		while(ret != 0) {
			
			//if ret is just the indicator var for checkForPossibleLogicalSkipsFromIndexToLastLineOfMain
			//not doing anything:
			if(ret == CHECK_NO_CHANGE) {
				ret = 0;
				break;
			}
			//if we are done searching:
			else if(ret == SolutionIncrementer.ALL_POSSIBILITIES_CHECKED ) {
				break;
			
			//In case of changing P1/P2, check for possible skips in main:
			} else {
				//System.out.println("CHANGED P1/P2");
				updateCountNumpickupsAndActions();
				ret = skipFromIndexToLastLineOfMainUntilpossibleSolutionFound(INITIAL_SKIP_TRIAL_INDEX);
			}
		}
		
		//TESTING
		//System.out.println("************************************");
		
		return ret;
	}
	
	public int incrementMainNoSkip(int index) {
		int oldCommand = NULL_COMMAND;
		int newCommand = NULL_COMMAND;
		
		
		
		//if done incrementing main, increment P1:
 		if(index < 0) {
 			//System.out.println("IncrementP1");
 			
 			firstIncwithNewP1 = true;
			int ret = incrementP1resetMain();
			//TESTING
			/*System.out.print("last P1:  ");
			for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
				System.out.print(p1[i] + " ");
			}
			System.out.println();
			*/
			return ret;
			
			//END TESTING
		
		//If it's the first iteration of Main
		} else if(firstIncwithNewP1) {
			firstIncwithNewP1 = false;
			updateCountNumpickupsAndActions();
			
			//System.out.println("first increment main!");
			//Sanity testing:
			for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
				if(m[i] != 0) {
					System.out.println("Error: in first iteration of main with new P1, main isn't null across the board.");
					System.out.println("index = " + index);
					System.out.print("M:  ");
					for(int j=0; j<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; j++) {
						System.out.print(m[j] + " ");
					}
					System.out.println("END TESTING");
				}
			}
			//End sanity test
				
		}
	
			
			
		oldCommand = m[index];
		
		m[index]++;
		//rollover at 5 for main function:
		if(m[index] > 5) {
			m[index] = 0;
		}
		
		newCommand = m[index];
		updateNumBrainPickupsAndMoveForward(oldCommand, newCommand);
		
		//TESTING
		/*System.out.println("index = " + index);
		System.out.print("M:  ");
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(m[i] + " ");
		}
		System.out.println();
		*/
		//END TESTING
		
		
		if(m[index] == 0) {
			setMainCommandsAfterIndexto0(index);
			//recursively call increment main to change the previous command.
			return incrementMainNoSkip(index - 1);
		} else {
			return 0;
		}		
	}
	
	private static int CHECK_NO_CHANGE = -5;
	
	//pre: index >= 0.
	//TODO: test & simplify.
	//checks for possible skips at index i, if we could skip this function will 
	//keep checking until P1 is changed or it has found a main function that could logically succeed.
	private int skipFromIndexToLastLineOfMainUntilpossibleSolutionFound(int index) {
		
		//SANITY CHECK:
		if(index > DrBrainMazeRunner.LAST_COMMAND_INDEX) {
			System.out.println("ERROR: check for poss index > 6.");
			System.exit(0);
		} else if(index < -1) {
			System.out.println("ERROR: check for poss index < -1.");
			System.exit(0);
		}
		//END SANITY CHECK
		
		//1. while P1 is changed or main function is logically possible:
				//check if enough pickups
				//if not enough: check if could have enough
						//If can't have enough
							//set current command + 1 and next commands in main to 0		
						//End if
				//End if
			//end while
				//retry with next step in main.
		
		int mostPossiblePickups;
		int mostPossibleActions;
		int ret = 0;
		
		boolean noChange = true;
		
		for(int i=index; i<=DrBrainMazeRunner.LAST_COMMAND_INDEX; i++) {
			
			//if P1 has been changed:
			if(i<0 && noChange == false) {
				return ret;
			}
			
			if(minNumPickupsYouHavetoMake > currentNumBrainsMightPickup[MAIN]) {
				mostPossiblePickups = getMostpossiblePickupsFromCurrentIndex(i);
				
				if(minNumPickupsYouHavetoMake > mostPossiblePickups) {
					//skip:
					setMainCommandsAfterIndexto0(i);
					ret = incrementMainNoSkip(i);
					
					//After the increment, find the index of i where m[i]=5 didn't become 0:
					while(i >=0 && m[i] == 0) {
						i--;
					}
					//decrement i because we want to check if we can reskip:
					i--;
					noChange = false;
					continue;
				}
			}
			
			//Same logic for actions repeated: (They are seperate enough to warrant 2 different codes at two different places)
			//1.check if enough actions
			if(minNumMovesYouHaveToMake > currentNumForwards[MAIN]) {
				mostPossibleActions = getMostpossibleActionsFromCurrentIndex(i);
				
				//if not enough: check if there exist commands after index that would allow
				//	Main to have enough move forwards:
				if(minNumMovesYouHaveToMake > mostPossibleActions) {
					//skip:
					setMainCommandsAfterIndexto0(i);
					ret = incrementMainNoSkip(i);
					
					//After the increment, find the index of i where m[i]=5 didn't become 0:
					while(i >=0 && m[i] == 0) {
						i--;
					}
					//decrement i because we want to check if we can reskip:
					i--;
					noChange = false;
					continue;
				}
			}
		}
		
		if(noChange == true) {
			return CHECK_NO_CHANGE;
		} else {
			return 0;
		}
	}
	
	
	//pre: currentNumBrainsMightPickup is updated and correct
	//post: returns the most possible pickups the main program will if commands from 0 to index are set in stone
	// and commands from index+1 to 6 are variable:
	private int getMostpossiblePickupsFromCurrentIndex(int index) {
		int mostPossible = 0;
		for(int i=0; i<=index; i++) {
			mostPossible+=getNumBrainPickupsWithCommand(m[i]);
		}
		int numStepsAfterIndex = DrBrainMazeRunner.LAST_COMMAND_INDEX - index;
		int maxPickupCommand = Math.max(1, currentNumBrainsMightPickup[P2]);
		maxPickupCommand = Math.max(currentNumBrainsMightPickup[P1], maxPickupCommand);
	
		mostPossible += numStepsAfterIndex * maxPickupCommand;
		
		return mostPossible;
	}
	
	//pre: currentNumForwards is updated and correct
		//post: returns the most possible actions the main program will if commands from 0 to index are set in stone
		// and commands from index+1 to 6 are variable:
	//It's the same idea as pickups.
	private int getMostpossibleActionsFromCurrentIndex(int index) {
		int mostPossible = 0;
		for(int i=0; i<=index; i++) {
			mostPossible+=getNumActionWithCommand(m[i]);
		}
		int numStepsAfterIndex = DrBrainMazeRunner.LAST_COMMAND_INDEX - index;
		int maxActionCommand = Math.max(1, currentNumForwards[P2]);
		maxActionCommand = Math.max(currentNumForwards[P1], currentNumForwards[P2]);
	
		mostPossible += numStepsAfterIndex * maxActionCommand;
		
		return mostPossible;
	}
	
	
	public void setMainCommandsAfterIndexto0(int index) {
		index++;
		int oldCommand;
		int newCommand = 0;
		while(index < DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION) {
			oldCommand = m[index];
			m[index] = newCommand;
			updateNumBrainPickupsAndMoveForward(oldCommand, newCommand);
			index++;
		}
	}
	
	private void updateNumBrainPickupsAndMoveForward(int oldCommand, int newCommand) {
		currentNumBrainsMightPickup[MAIN] += getNumBrainPickupsWithCommand(newCommand);
		currentNumBrainsMightPickup[MAIN] -= getNumBrainPickupsWithCommand(oldCommand);
		
		currentNumForwards[MAIN] += getNumActionWithCommand(newCommand);
		currentNumForwards[MAIN] -= getNumActionWithCommand(oldCommand);
	}
	
	private int getNumBrainPickupsWithCommand(int command) {
		//TODO: maybe throw a warning if command == 6?
		if(command == 0 || command == 6) {
			return 1;	
		//go from left to p2:
		} else if(command == 4 ) {
			return currentNumBrainsMightPickup[P2];
			//go from p2 to p1
		} else if(command == 5 ) {
			return currentNumBrainsMightPickup[P1];
		//go from p1 to pickup
		}
		
		return 0;
	}
	
	private int getNumActionWithCommand(int command) {
		if(command == 1) {
			return 1;	
		//go from left to p2:
		} else if(command == 4 ) {
			return currentNumForwards[P2];
			//go from p2 to p1
		} else if(command == 5 ) {
			return currentNumForwards[P1];
		//go from p1 to pickup
		}
		
		return 0;
	}
	
	
}
