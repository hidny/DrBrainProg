package incrementer;

import env.DrBrainMazeRunner;

public class DrBrainForcedSlowIncrementer extends DrBrainSimpleIncrementer {

	
	public DrBrainForcedSlowIncrementer(int m[], int p1[], int p2[],
			boolean p1IsConst, boolean p2IsConst) {
		super(m, p1, p2, p1IsConst, p2IsConst);
		// TODO Auto-generated constructor stub
	}
	
	//this is set to true if increment Main is not called recursively:
	private boolean incrementCallFromOutSide = true;
	
	//if increment Main is called recursively, allow index !=0
	// else, force index to be DrBrainMazeRunner.LAST_COMMAND_INDEX.
	public int incrementMain(int index) {
		int ret;
		if(incrementCallFromOutSide == true) {
			incrementCallFromOutSide = false;
			ret = super.incrementMain(DrBrainMazeRunner.LAST_COMMAND_INDEX);
			incrementCallFromOutSide = true;
		} else {
			ret = super.incrementMain(index);
		}
		return ret;
	}
	
	public void setMainCommandsAfterIndexto0(int indexOfCrash) {
		//Do nothing. Just pretend to the runner that this is happening.
	}
	
}
