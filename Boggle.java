/*
 * This program runs a simulation of a Boggle Game and displays all the anwsers for a specific Board
 * By: Joseph Dandan
 * CSCI 340 March 17, 2015
 */
package boggle;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This is the Boggle Class It has the main that runs the program and also has
 * our solvedriver and our solve method we use to solve the boards
 *
 * @author Joseph
 */
public class Boggle {

    //this is a instance of the BoggleDice class we are going to work with named boarddice
    private static BoggleDice boarddice;

    //this is the hashset used to store found words
    private static final HashSet<String> foundWords = new HashSet<>();

    //this is a instance of our lexicon were using called ourlexi
    private static Lexicon ourLexi;

    /**
     * This is the main program, uses our boggledice class with the dice.txt
     * file It also uses our lexicon class with the enable1.txt file We use the
     * method print board to print the board We run the solve driver which in
     * turn runs the solve method and then we print the words found
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            boarddice = new BoggleDice("dice.txt");
            ourLexi = new Lexicon("enable1.txt");
            printBoard();
            solveDriver();
            printWords();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printWords() {
        //we want to tell the user how many words were found 
        System.out.println("The computer found " + foundWords.size() + "words");
        System.out.println(foundWords);
    }

    private static void printBoard() {
        //we want to print the board we are going to play our game with
        System.out.println("The 4x4 Board");
        //we use 2 loops to iterate across all 16 spots of the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //we assign the value at the position of the board
                System.out.print(" | " + boarddice.getValue(i, j));

            }
            System.out.print(" |");
            System.out.println("");

        }

    }

    /**
     * This method is the diver for our other method called solve
     * Before we can solve any of the spots, we need to have a way of knowing if we have been to a spot before 
     * We create a boolean array known as spots with the size of [4][4]
     * We set all those spots to true to help keep track of were we have been
     * Then for all those spots we call our solve method on the spot we are at in the board
     */
    public static void solveDriver() {
        //clear the foundwords hashset for every new instance of the board
        foundWords.clear();
       

        //we create a array of the board set to all true to keep track of were the program has been 
        Boolean spots[][] = new Boolean[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                spots[i][j] = true;
            }
        }
        //we now run the solve method below at every spot
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                solve(spots, "", i, j);
            }
        }

    }

    /**
     *
     * This is the solve method that will be called from the solvedriver method in the program and recursively inside itself
     * Our solve takes in the spot that we are on the board, the current word that is being attempted to be created, by the selection of dice across the board, 
     * and our position variables i and j. First it checks if it is a word in the lexicon, and if it is it adds it to the hashset to be printed out solutions
     * if the word being created is a prefix, then it hits the big solving if statements to move across the board, this is a recursive call and each time 
     * the prefix should increase in size until it is a found word or it is no longer a prefix, we are going to move all 16 spots because our solvedriver method
     * above is running across all 16 spots, it will attempt to move in all directions for each spot creating a word prefix each time until, if it is not a word 
     * prefix or a actual word it will then we will move on to the next spot, we also make each spot false before the recursive call because we dont want to go over
     * that spot twice when creating words, the path is only in one direction
     * NOTE All letters of the alphabet will be considered a prefix so that only our completed words will be passed onto the solutions set
     * it will continue to work through the if statements until we reach a word or move positions on the dice
     */
    public static void solve(Boolean spot[][], String word, int i, int j) {
        //if our word passed in is in our lexicon ourlexi then we add it to our found words set
        if (ourLexi.isWord(word)) {
            foundWords.add(word);
        }
        //this will always be hit because all characters are prefixes of words
        if (ourLexi.isPrefix(word)) {

            //This is our  move to the right, we pass on the value of i adding 1 to move it to the right
            if (i + 1 < 4 && spot[i + 1][j]) {
                //we set our spot to false cause we are located in the spot going over it
                spot[i][j] = false;
                //we recursively call our solve method on the spot we are located at and we add to our "word" or prefix the value at the spot we are at
                //if this word is not a prefix then it wont continue to try to solve
                solve(spot, word + boarddice.getValue(i, j), i + 1, j);
                //we set the spot back to true because we dont want to leave it at false when searching at a different position on the board
                spot[i][j] = true;
            }
            //This is our  move down to the right, we pass on the value of i adding 1 to move it to the right and adding 1 to j moving it down
            if (i + 1 < 4 && j + 1 < 4 && spot[i + 1][j + 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i + 1, j + 1);
                spot[i][j] = true;
            }
            //This is our  move down, we pass on the value of j adding 1 to move it down
            if (j + 1 < 4 && spot[i][j + 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i, j + 1);
                spot[i][j] = true;
            }
              //This is our  move left and down, we pass on the value of i subtracting 1 to move it left and add 1 to j moving it down
            if (i - 1 > 0 && j + 1 < 4 && spot[i - 1][j + 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i - 1, j + 1);
                spot[i][j] = true;
            }
              //This is our  move left, we pass on the value of i subtracting 1 to move it left
            if (i - 1 > 0 && spot[i - 1][j]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i - 1, j);
                spot[i][j] = true;
            }
            //This is our  move left and up, we pass on the value of i subtracting 1 to move it left and subtract 1 from j moving it up
            if (i - 1 > 0 && j - 1 > 0 && spot[i - 1][j - 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i - 1, j - 1);
                spot[i][j] = true;
            }
            //This is our  move left and up, we pass on the value of i subtracting 1 to move it left and subtract 1 from j moving it up
            if (j - 1 > 0 && spot[i][j - 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i, j - 1);
                spot[i][j] = true;
            }
            //This is our  move right and up, we pass on the value of i add 1 to move it right and subtract 1 from j moving it up
            if (i + 1 < 4 && j - 1 > 0 && spot[i + 1][j - 1]) {
                spot[i][j] = false;
                solve(spot, word + boarddice.getValue(i, j), i + 1, j - 1);
                spot[i][j] = true;
            }

        }

    }

}

class Lexicon {
    //Lexicon class should be based around two private hashsets, words and prefixes. 

    private HashSet<String> words = new HashSet<String>();
//HashSet to hold all of the words in the lexicon

    private HashSet<String> prefixes = new HashSet<String>();
//HashSet to hold all of the prefixes of the words

    public Lexicon(String filename) throws IOException {
//constructs a Lexicon containing the words in the file

        Scanner scan = new Scanner(new File(filename));
        //we need a scanner for the file data

        prefixes.add("");
        //We want to start by adding in a empty string into our prefixes hashset

        while (scan.hasNext()) {
            //While there are lines in the file we keep going through them

            String stuff = scan.next();
            //we put the lines into our string named stuff

            if (stuff.length() > 3) {
                //we wana limit the words to being greater than 4 characters so we set a limit if greater than 3 

                words.add(stuff);
                //we add those words 

                for (int i = 1; i <= stuff.length(); i++) {
                    //this loop goes throw each char in the word

                    prefixes.add(stuff.substring(0, i));
                    //we add each substring from position 0 to the position of the char of the word were at in the loop

                }

            }

        }
        scan.close();
        //close the scanner

    }

    public boolean isWord(String str) {

        return words.contains(str);
        //true if words contains our string

    }

    public boolean isPrefix(String str) {

        return prefixes.contains(str);
        //true is prefixes contains our string prefixes

    }

}
//Each of the 16 lines in the file consists of a six-character string in which the
//characters indicate what letters appear on the six faces of each cube.

class BoggleDice {

    //this is a double array variable to hold the 16 by 6 array
    String sixCubes[][] = new String[16][6];

    //we need another single dimentional array variable to hold the individual dices of 16 (there are 6 of them)
    String eachCube[] = new String[16];

    //read the data for each die from dice.txt
    public BoggleDice(String filename) throws IOException {
        
        Scanner fileScanner = new Scanner(new File(filename));
        //while the scanner still has data in the file we keep on scanning
        for (int i = 0; fileScanner.hasNext(); i++) {
            //we set those values into a string
            String fileChars = fileScanner.next();
            //for the length we loop through 
            for (int j = 0; j < fileChars.length(); j++) {
                //we set those substring values to a string chars
                String Chars = fileChars.substring(j, j + 1);

                //this is were we check for q
                if (Chars.equals("q")) {
                    //if its q we change it to qu
                    sixCubes[i][j] = "qu";
                } else {
                    //otherwise we send those chars to be our cubes
                    sixCubes[i][j] = Chars;
                }

            }

        }
        //we close the scanner
        fileScanner.close();
        //we call the shuffle method
        this.shuffle();

    }


    //this is the shuffle method used to shuffle the board 
    public void shuffle() {
        //we shuffle the array sixcubes as a list 
        Collections.shuffle(Arrays.asList(sixCubes));
        
        for (int i = 0; i < 16; i++) {
            //for all 16 spots
            int j = (int) Math.floor(Math.random() * 6);
            //we want random cubes so we do a math.random with our 6 possible cube possibilites to get a random one
            eachCube[i] = sixCubes[i][j];
            //we set each cube accordingly

        }

    }
    //this method gets the values for the row and col
    public String getValue(int row, int col) {
        //we return each cube with its row and col
        return eachCube[(row) * 4 + (col)];

    }

}
