package incrementer;

public interface SolutionIncrementer {
	
	//return values to signify how the increment Main command is doing:
	public static int TEST_CHANGED_P2 = -3;
	public static int TEST_CHANGED_P1 = -4;
	public static int ALL_POSSIBILITIES_CHECKED = -2;

	//basic increment command
	public int incrementMain(int index);
	
	public void setMainCommandsAfterIndexto0(int indexOfCrash);
	
	//getters for all 3 sets of commands:
	public int[] getM();

	public int[] getP1();

	public int[] getP2();
	
}
