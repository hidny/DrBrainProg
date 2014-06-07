package incrementer;

import env.DrBrainMazeRunner;

public class DrBrainSimpleIncrementer implements SolutionIncrementer {
	
	
	//soft copies of the commands:
	protected int m[];
	protected int p1[];
	protected int p2[];
	

	public int[] getM() {
		return m;
	}

	public int[] getP1() {
		return p1;
	}

	public int[] getP2() {
		return p2;
	}
	
	//booleans to determine if we could change p1 or p2
	// note p1IsConst implies p2IsConst
	private boolean p1IsConst;
	private boolean p2IsConst;
	
	//TODO: make hard copies of the arrays.
	public DrBrainSimpleIncrementer(int m[], int p1[], int p2[], boolean p1IsConst, boolean p2IsConst) {
		this.m = m;
		this.p1 = p1;
		this.p2 = p2;
		this.p1IsConst = p1IsConst;
		this.p2IsConst = p2IsConst;
		if(this.p1IsConst && !this.p2IsConst) {
			System.out.println("Error: p2 can't be constant while P1 isn't constant.");
		}
	}
	
	//changes command #index to see if that will bring a solution:
	//this will normally start with command #7 then #6 then... #1
	public int incrementMain(int index) {
		//if index <0, all the possible main functions have been tried:
		if(index < 0) {
			return incrementP1resetMain();
		}
		
		m[index]++;
		
		//in case of roll over: ("carry the one")
		if(m[index] > 5) {
			m[index] = 0;
			
			setMainCommandsAfterIndexto0(index);
			//recursively call increment main to change the previous command.
			return incrementMain(index - 1);
		}
		
		return 0;
	}
	
	//pre: index < 0
	//post increments P1 and resets main to 0, 0, 0, 0, 0, 0, 0.
	protected int incrementP1resetMain() {
		if(p1IsConst) {
			System.out.println("All possibilities checked for main!");
			return SolutionIncrementer.ALL_POSSIBILITIES_CHECKED;
		} else {
			
			setMainCommandsAfterIndexto0(-1);
			return incrementP1(DrBrainMazeRunner.LAST_COMMAND_INDEX);
		}
	}

	//pre: index >= -1
	//If the program decided to change main command #4 because of a crash
	//set the commands below it to 0(pickup) in order not to miss any.
	public void setMainCommandsAfterIndexto0(int index) {
		index = index+1;
		
		while(index < DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION) {
			m[index] = 0;
			index++;
		}
	}
	
	public int incrementP1(int index) {
		
		if(index < 0) {
			if(p2IsConst) {
				System.out.println("All possibilities checked for P1!");
				return SolutionIncrementer.ALL_POSSIBILITIES_CHECKED;
			} else {
				return incrementP2(DrBrainMazeRunner.LAST_COMMAND_INDEX);
			}
		}
		
		p1[index]++;
		if(p1[index] > 4) {
			p1[index] = 0;
			//recursively call increment P1 to change the previous command.
			return incrementP1(index - 1);
		}
		
		//TODO: change this back to 0 later
		return SolutionIncrementer.TEST_CHANGED_P1;
	}
	
	//TODO: actually run code that will run this function so you could test it.
	private int incrementP2(int index) {
		if(index < 0) {
			System.out.println("All possibilities checked for P2! All solutions found!");
			return SolutionIncrementer.ALL_POSSIBILITIES_CHECKED;
		}
		//TESTING
		System.out.println("Testing Increment P2:");
		System.out.print("P2:  ");
		for(int i=0; i<DrBrainMazeRunner.NUM_COMMANDS_PER_FUNCTION; i++) {
			System.out.print(p2[i] + " ");
		}
		System.out.println();
		//END TESTING
		
		p2[index]++;
		if(p2[index] > 3) {
			p2[index] = 0;
			//recursively call increment P2 to change the previous command.
			return incrementP2(index - 1);
		}
		
		return 0;
	}

	public boolean isP1IsConst() {
		return p1IsConst;
	}

	public boolean isP2IsConst() {
		return p2IsConst;
	}

}
