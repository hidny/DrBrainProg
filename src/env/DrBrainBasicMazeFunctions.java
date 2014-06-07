package env;

public class DrBrainBasicMazeFunctions {
	//*******************************************************************************//	
	//*******************************************************************************//	
	//*******************************************************************************//	
			//counting lower bound on the # of actions 
			//and pickups you have to do to complete the maze:
			
			
		
			
			
		//post: returns the number of brains in the current maze:
		public static int countNumBrains(char curMaze[][]) {
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
		
		
		//TESTED TWICE!
		//post: gets a low lower bound on the # of actions
		//O(n^2) method though it looks O(n^4)
		//proof: every location could get scanned <10 times because hexagons bitch.
				//Therefore #scans < 10n^2 = O(n^2)
		
		//this basically sums up the manhattan distances from each brain to the nearest brain to them.
		//minus the longest distance just in case there is a way around going the longest distance.
		//example where this fails:
		//BBB     S                                                      BBB
		public static int getLowerBoundOnNumMoves(char curMaze[][], int startX, int startY) {
			int sumManhatanDist = 0;
			int currDist =0;
			int GreatestManhatanDist = 0;
			if(curMaze[startY][startX] == 'B') {
				//neverMind start location.
			} else {
				currDist = getClosestManhatanBrain(curMaze, startX, startY);
					//there's actually no logical way out of not doing this distance when you think about it.
				//GreatestManhatanDist = currDist;
				sumManhatanDist += currDist;
			}
			
			for(int i=0; i<curMaze.length; i++) {
				for(int j=0; j<curMaze[0].length; j++) {
					if(curMaze[i][j] == 'B') {
						currDist = getClosestManhatanBrain(curMaze, j, i);
						if(currDist > GreatestManhatanDist) {
							GreatestManhatanDist = currDist;
						}
						sumManhatanDist += currDist;
					}
				}
			}
			
			return sumManhatanDist - GreatestManhatanDist;
		}
		
		//returns the manhatan dist to the closest brain from startX, startY
		//note: don't check startX, startY because that doesn't make sense
		public static int getClosestManhatanBrain(char curMaze[][], int startX, int startY) {
			
			//max length possible:
			int widthPlusHeightMinustwo = curMaze[0].length + curMaze.length - 2;
			
			//these variable indicate where around the start location we are searching
			boolean currentpast12clockwise;
			boolean currentpast3clockwise;
			
			for(int currentMinDist =1; currentMinDist <= widthPlusHeightMinustwo; currentMinDist++) {
				
				int iFactor = -currentMinDist;
				int jFactor = 0;
				//note: exactly 12 oclock pos is not past 12 oclock pos.
				currentpast12clockwise=false;
				currentpast3clockwise=false;
				
				while(true) {
					
					if(startY + iFactor >=0 && startY + iFactor < curMaze.length) {
						if(startX + jFactor >=0 && startX + jFactor < curMaze[0].length) {
							if(curMaze[startY + iFactor][startX + jFactor] == 'B') {
								return currentMinDist;
							}
						}
					}
					
					//cycle through:
					
					//figure out along which diagonal to go:
					if(Math.abs(iFactor) == currentMinDist) {
						currentpast12clockwise = !currentpast12clockwise;
					}
					
					if(Math.abs(jFactor) == currentMinDist) {
						currentpast3clockwise = !currentpast3clockwise;
					}
					
					//increment to the next location.
					if(currentpast12clockwise == true) {
						iFactor++;
					} else {
						iFactor--;
					}
					
					if(currentpast3clockwise == true) {
						jFactor--;
					} else {
						jFactor++;
					}
				
					//check to see if the loop did a full rotation:
					if(iFactor== -currentMinDist) {
						//TESTING
						if(jFactor != 0) {
							System.out.println("ERROR: closest manhattan dist doesn't work!");
						}//END TESTING
						
						//break out of a specific distance:
						break;
					}
				}
			}
			
			//at this point, we no there's no other brain.
			return 0;
		}
		
}
