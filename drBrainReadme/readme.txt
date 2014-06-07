

This program finds solutions to a kid's game that is very hard to solve. There's a youtube video about it here:
http://www.youtube.com/watch?v=xqDGPYMcWGk called 
Lost Mind of Dr. Brain - Part 5 - Motor Programming.

FAQ:

What does it do?

It goes through every possible solution. It starts by going through all of the main procedure.
 Once all of main has been tried, it tries the next possible Procedure 1 and retries all of main.
 Once all of procedure 1 has been tried, it tries next the possible Procedure 2 and retries all of main/procedure 1.


Doesn't that take forever?

If we run it without guessing what procedure 2 is, it will take about 5 hours. :(
When we guess at procedure 2, it only takes 30 seconds.
The reason it won't take over a year is because I created a few tools that will allow the program to skip many obviously wrong solutions.


Does it find all solutions?

It has successfully found solutions to all of the problems in the Dr. Brain kid's game, but I'm not sure if there's a bug that causes the program to miss a few solutions. Also, the game is slightly more complex than the model of the game I created.


How did you tackle the logic behind the enemy bugs in the Dr. Brain game?

I didn't. :P
It was too complicated. I just forced my robot to avoid any location where the "bugs" are patrolling.


How did you handle red wall/yellow wall logic?

I didn't. :P
I just hoped there wasn't a solution that involved purposefully hitting a wall and losing time.


Plans for the future:

I don't think I will come back to this program any time soon.
If I ever do come back to this, I will either make it 100 times faster or, implement logic for walls, patrolling "bugs" and the messed-up hot delete-code option. What I mean by the delete-code option is that you could apparently delete the instructions  of a whole procedure as the robot is being run.
I could maybe make it go much faster if I explain to the computer how some lines of code equate to other lines or how some lines are useless allowing us to shorten it.
maybe I could make it crank program that have only effective code to make it go faster...

There are probably some crazy solutions that involve deleting instructing mid-way through the execution that I'd like to find.


Where can I get The game for this program? (The lost mind of Dr Brain)

This is complicated. :(

They probably sell on Amazon or you could go to abandonware and get it by subscribing.
I used my really old '94 Macintosh to run it.
To get it to run on windows 7, I think you have to install a virtual windows PC.

-------------------------------
Instructions on how to run:

0) Install java.

1) Extract zip file.

2) Go to cmd console -> go to the directory of drBrain.jar.

3) To find all solutions to HARDPROB3 with a guess at procedure 2 type: 
java -jar drBrain.jar drBrainInputFiles\HARDPROB3 1021010
(This takes 30 seconds)

If you're curious, 1021010 is just the number code for the commands:
1)forward
2)pickup
3)right
4)forward
5)pickup
6)forward
7)pickup

OR
3) To find all solutions to HARDPROB3 type: 
Type:
"java -jar drBrain.jar drBrainInputFiles\HARDPROB3 > outputBrain.txt"

(Takes up to 30 hours) :(

4) see the results in outputBrain.txt or in the console depending on whether you included the 
 "> outputBrain.txt"   tag.
------------


format of input file:
Random line...

#columns:c #rows:r

table with c columns and r rows.
where D = Death
B = Brain to collect
S = Start position
R = Start position with a brain to collect
underneath it.

Important Note: Because I'm a lazy programmer,
Dr. Brain always starts with pointing to the right.
You have to build the maze around the
 fact that dr Brain starts with pointing to the right.
:(


Example input file:

HARD prob 9.

9 13

DDDDDDDDD
D       D
D BBBBDDD
DDBDBBBBD
DBBBBBDBD
DBBBBBBBD
DBDBRBBBD
DBBBBBBBD
D DBBBB D
D  DBB  D
D       D
D       D
DDDDDDDDD

------
In the output file, every command has a number associated with it:
-1: pause
0: pickup
1: forward
2: right
3: left
4: do procedure 2
5: do procedure 1
I don't actually use -1/pause because if the robot could pause, he could pickup instead.

Every procedure is abbreviated:
M = main procedure that could call procedure 1 and procedure 2.
P1: procedure 1 that could call procedure 2.
P2: procedure 2.


---------------------
Format of output file:

***redrawing of the Maze***
***Random title of the program executed***
***count of the number of brains***
***count of the amount of moves the program knows Dr. Brain must do***
	note: the count of the amount of moves is probably not optimal.

Initial P2:  0 0 0 0 0 0 0 
(initial guess at Procedure 2.)
(this could be customised in argument #2)
(for example in step 3 to run 1021010 makes Initial P2: 1 0 2 1 0 1 0)
...
Testing Increment P2:
P2:  0 0 0 3 0 3 3 
(This just says which procedure 2 the computer is current looking through.)
(This will go to P2 3 3 3 3 3 3 3 and finally end.)


Solution:
M:  5 2 4 4 3 5 5 
P1:  0 3 4 3 4 2 4 
P2:  1 0 2 1 0 1 0 
(x,y) = (7, 4)	(**Location of the robot at the end.)
Orientation: 0	(**direction robot is facing at the end. 0 = right, 1=up, 2=left, 3=down.)
DDDDDDDDDDDDD
D DD DDDDDDDD
D D         D
D           D
D          DD
DDD       DDD
DDDDD DDDDDDD
DDDDD   DDDDD
DDDDDDDDDDDDD
(**This is pretty self-explanatory...)

----------------------------------

if you have any questions or comments, email me at mtardibuono@hotmail.com


TODO: 
0) rejar edited code
1)input file for problem 1, problem 2 and real problem 3.
	2)run prob 1, 2, 3
and make sure it works (against the mac)
	3) get rich to read it and note his confusions.

	4) Create a zip file or tar file or 7-zip file
5) put readme in word doc?