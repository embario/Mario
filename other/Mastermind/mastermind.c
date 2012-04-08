/*
 * driver.c
 *
 *  Created on: Apr 28, 2009
 *      Author: Mario
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

// the 4-peg code chosen by the codemaker
char hidden_code[4];

//Game Variables
char RED = 'R';
char YELLOW = 'Y';
char GREEN = 'G';
char BLUE = 'B';
char PURPLE = 'P';
char ORANGE = 'O';

int BLACKCOUNT = 0;
int WHITECOUNT = 0;

int ATTEMPTS = 12;

/**** Function declarations ****/


/* int SetHiddenCode():
 * Given: A pointer to a character array
 * Effects: Sets the character array 'hidden_code' to the characters from array_ptr
 * Returns: 0 if successful, -1 otherwise */
int setHiddenCode (char *array_ptr);

/* int validateCode():
 * Given: A pointer to a character array
 * Effects: None
 * Returns: 0 if the character array 'array_ptr' contains a valid
 * 		Mastermind 4-peg code.  Otherwise returns -1. */

int validateCode (char *array_ptr);

/* inline void printCode():
 * Given: A pointer to a character array containing a valid 4-peg code
 *  Effects: Print the code to the screen */
inline void printCode(char *array_ptr);




/** Main bulk of where game functionality resides. Look for checkCode(), gameOver(), and gameFinish() here. **/
int playGame();



/* checkCode: checks the input user guess against the hidden code and fills in the whites and blacks
for the result pegs array.

 */
void checkCode(char *code);


/* gameOver: determines whether the current state of the game is done by analyzing the result pegs.

 -Returns a 1 if the game is done (i.e. either the user has a winning match or ran out of attempts)
 -Returns a 0 if the game is not done.
*/
int gameOver(int attempts);



/*******************************************************************************************************************************************************************/
/********************************************************************* Main Function *******************************************************************************/
/*******************************************************************************************************************************************************************/

int main(int argc, char **argv){

  int i = 0;

  // Set the codemaker's hidden code using the first argument to the executable
  if (argc != 2) {
    printf ("%s: Invalid number of arguments(%d).\n", argv[0], argc);
    exit(1);
  } else if (setHiddenCode(argv[1]) < 0)
    i = -1;
  
  
  char code [10];
  //Loop until we get a correct Hidden code...
  while(i == -1){

    //Prompt Again
    printf("\nEnter a correct Hidden code: ");
    fgets(code,10, stdin);  //Grab the hidden code

    if ((code[strlen(code)-1]) == '\n')
      code[strlen(code)-1] = '\0';

    //Try again!
    i = setHiddenCode(code);

  }

  //If we have no errors in our hidden code...
  if(i == 0){

    //PLAY THE GAME!
    if(playGame() == 0){
      printf("\nThanks for playing Mastermind!\n");
      return 0;

    }
    else{
      printf("GAME OVER: Code-Breaking Failed!!\n\n");
      return 0;

    }

  }

  // Sanity check
  // printCode(hidden_code);
}





/********************************************************** Function definitions **********************************************************/


/*
 * Main bulk of Gameplay. Directs Evaluation of Guesses against the Hidden Code (checkCode(char * ptr);), Determines Game Outcome (gameOver(int k);), and
 Displays results to the user on Outcome (gameFinish()). */

int playGame(){

    printf("-------------------------------------------------------------------------------");
    printf("\n\t\t\tWelcome to the Game of Mastermind!\n");
    printf("\tColor Pegs: R=RED, B=BLUE, G=GREEN, Y=YELLOW, O=ORANGE, P=PURPLE\n");

    //This is the array for the user to type in a guess!
    char inputCode [10];

    int k;
    //In the game, we have 12 Attempts to get it right...
    for(k = 1; k <= ATTEMPTS; k++){

	printf("Guess Round %d: ", k);
	fgets(inputCode, 10, stdin);

	inputCode[strlen(inputCode)-1] = '\0';

	//Validate the input code!
	if(validateCode(inputCode) == 0)
	  checkCode(inputCode); //Check the code!

	else{
	  k--; //No Penalty for providing incorrect input...
	  continue;
	}

	//Is the Game over?
	if(gameOver(k) > 0){
	  printf("You cracked the code! Succeeded after %d attempts!", k);
	  return 0;
	}
	
    }//End For Loop

    //LOST the game!
    return -1;

}

//Determines whether the game is done (i.e. did the player win, did the player run out of attempts?)
int gameOver(int attempts){


  //If we've attempted too much and our number of Black Pegs aren't yet 4...
  if(attempts >= 12 && BLACKCOUNT < 4)
    return -1;

  else if (BLACKCOUNT == 4)
    return 1;
  
  else
    return 0;


}


/*
 *Determines Result Pegs from the user input code!
 */
void checkCode(char * guess){

  //Guess Outcome
  int blackPegs = 0;
  int whitePegs = 0;

  //A char array for our guess
  char guessArray [strlen(hidden_code)];

  //Load our guess into an array for easier manipulation
  int index;
  for(index = 0; index < strlen(hidden_code); index++){
    guessArray[index] = *guess;
    guess++;
  }

  //If we don't find black pegs, then put the chars
  //in these arrays to find white pegs.
  char guessLeftOvers [strlen(hidden_code)];
  char hiddenLeftOvers[strlen(hidden_code)];

  int i;
  //Check against the hidden code... Do we have matches?
  for(i = 0; i < strlen(hidden_code); i++){

    /* Hidden Code:  GRBR
       GuessArray:   GYOB 
       GuessArray2:  GBRO */

    //If a char in guess equals a char in
    //hidden code in the SAME SPOT...
    if(guessArray[i] == hidden_code[i])
      blackPegs++;

    else{

      //Collect the ones that are not exact matches!
      guessLeftOvers[i] = guessArray[i];
      hiddenLeftOvers[i] = hidden_code[i];

    }//End Else Conditional

  }//End For Loop




  /** Time to find white pegs.. **/
  
  /** Let's Loop twice to analyze matching (but not exact) for white pegs.
      For every letter in our Guess, we loop through all the letters in the hiddenCode
      to find a match. If we find one, get out and analyze another letter. **/

  int j,m; //Indices
    for(j = 0; j < strlen(hidden_code); j++){

      if(guessLeftOvers[j] == 'X')
	continue;

      for(m = 0; m < strlen(hidden_code); m++){

	if(hiddenLeftOvers[m] == 'V')
	  continue;

	if(guessLeftOvers[j] == hiddenLeftOvers[m]){

	  guessLeftOvers[j] = 'X';
	  hiddenLeftOvers[m] = 'V';
	  whitePegs++;
	  continue;
	}


      }//End Inner For

    }//end Outer For



  printf("\n Black Pegs: %d\n", blackPegs);
  printf(" White Pegs: %d\n", whitePegs);

  //Set our Global variables for now...
  BLACKCOUNT = blackPegs;
  WHITECOUNT = whitePegs;

}

int setHiddenCode (char *array_ptr) {

  if (validateCode (array_ptr) < 0)
    return -1;

  int i;
  //Create the hidden code and set it in our global array
  for (i = 0; i < 4; i++)
    hidden_code[i] = array_ptr[i];

  return 0;
}

//General Purpose function used to validate user input for gameplay/initial hidden code.
int validateCode (char *array_ptr) {
  /*
  char * code = ""; //The copy that is important for later analysis.

  //For now, we check if there is any extra "garbage" we need to take care of.
  int k;
  for(k = 0; k < strlen(array_ptr); k++){

    char letter = *array_ptr;

    if(*array_ptr == '\n'){
      array_ptr++;
      continue;
    }
    else{

     code += letter;
    }
      
  }
  */

  //Is the size of the code 4?
  if (strlen (array_ptr) != 4) {
    printf ("Invalid number of characters in code.\n");
    return -1;
  }


  int i;
  //Check the Validity of the Hidden Code
  //I.E: Check the letters!
  for(i = 0; i < 4; i++){

    char letter = *array_ptr++;

    if(letter == RED)
      continue;
    if(letter == GREEN)
      continue;
    if(letter == BLUE)
      continue;
    if(letter == YELLOW)
      continue;
    if(letter == PURPLE)
      continue;
    if(letter == ORANGE)
      continue;

    else{
      printf("Incorrect letter typed! Appropriate Letters are R=RED, G=GREEN, B=BLUE, Y=YELLOW, P=PURPLE, and O=ORANGE\n");
      return -1;
    }

  }

  //Everything seems okay!
  return 0;
}

inline void printCode(char *array_ptr)
{
  printf("%s\n", array_ptr);
}

// Your other function definitions here
