import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//SudokuSolver is it's own class to avoid clutter in the main file
public class SudokuSolver {
    //the puzzle to be solved
    char[][] puzzle;

    //this equals the coordinates of the position we are currently checking
    //(0 , 0) is the top left of the board
    //the bottom right of the board is (8 , 8)
    // the values increase as you go down and to the right
    Point currentLocation = new Point();

    //constructor to allow passing in the puzzle, so i don't have to do it for each method
    public SudokuSolver (char[][] puzzle){
        this.puzzle = puzzle;
        this.currentLocation.setLocation(0 , 0);
    }

    public boolean backtrack(){
        //iterates through each square of the board
        /*TODO make sure this does not overwrite the given values of the board
        *  Maybe add those coordinates to an array, and check that array before 'solving' a location
        * in order to make sure it wasnt one of our given vals?*/

        ArrayList<Point> knownCoords = new ArrayList<>();
        //iterate through puzzle, logging all known numbers to the ArrayList
        //this will keep us from overriding these values
        for (int i = 0; i < puzzle.length; i++){
            for (int j = 0; j < puzzle[i].length; j++){
                if (puzzle[i][j] != '.'){
                    knownCoords.add(new Point(i , j));
                }
            }
        }
        for (int i = 1; i <= 9; i++){
            for (int j = 0; j < puzzle.length; j++){
                for (int k = 0; k < puzzle[j].length; k++){
                    Point currentlyCheckedLocation = new Point(j , k);
                    if (!knownCoords.contains(currentlyCheckedLocation)){

                    }
                }
            }
        }
    }

    //generates an array of all values 1-9
    //if any of those values occur within the row, column, or box of the space we are checking
    //it will remove those values from the array
    //a performance light way to whittle down our options very quickly
    //thisValue is the value of the current square we are on, used to determine what values to start the ArrayList with
    //I.E. If the location we are currently checking has a 6 in it, we only need to check 7, 8 & 9
    //because we will have already checked 1 , 2 , 3 , 4 & 5
    //Within the backtrack method, it will set thisValue to 1, if the space is currently a '.'
    private ArrayList<Integer> checkOptions(int thisValue){

        //creates an ArrayList of ints and appends all possible values for a cell
        ArrayList<Integer> options = new ArrayList<>();
        for (int i = thisValue; i < 9; i++){
            options.add(i);
        }
        //removes any values that already occur in the given row
        ArrayList<Integer> valuesInRow = getValuesInRow();
        for (int i: valuesInRow) {
            options.remove(valuesInRow.get(i));
        }

        //reomves any values that already occur in the given column
        ArrayList<Integer> valuesInColumn = getValuesInColumn();
        for (int j : valuesInColumn){
            if (options.contains(valuesInColumn.get(j))){
                options.remove(valuesInColumn);
            }
        }

        //removes any values that already occur in the given box
        ArrayList<Integer> valuesInBox = getValuesInBox();
        for (int k : valuesInColumn){
            if (options.contains(valuesInBox.get(k))){
                options.remove(valuesInColumn);
            }
        }

        return options;
    }

    //quick and cheap way to remove values
    //if a value already appears in the row, adds it to an arraylist
    //this arraylist will be used to remove those values from our options
    private ArrayList<Integer> getValuesInRow(){
        ArrayList<Integer> valuesInRow = new ArrayList<>();
        boolean isNumInList;
        //iterates through each value in the row
        for (int i = 0; i < puzzle.length; i++){
            int currentval = 0;

            //if the value of this space is known, convert it to an int, and set currentval = to it
            if (puzzle[currentLocation.y][i] != '.'){
                currentval = Character.getNumericValue(puzzle[currentLocation.y][i]);
            }

            //if the currentval isn't already in valuesInRow, add it to valuesInRow
            isNumInList = valuesInRow.contains(currentval);
            if (!isNumInList && currentval != 0){
                valuesInRow.add(currentval);
            }
        }

        return valuesInRow;
    }

    //quick and cheap way to remove values
    //if the value has already appeared in the column, adds it to an arrayList
    //this arrayList will be used to remove those values from our options
    private ArrayList<Integer> getValuesInColumn(){
        ArrayList<Integer> valuesInColumn = new ArrayList<>();
        boolean isNumInList;

        //iterates through all values in the column
        for (int i = 0; i < puzzle.length; i++){
            int currentval = 0;
            //if we know the value of the current square, set currentbal equal to it
            if (puzzle[i][currentLocation.x] != '.'){
                currentval = Character.getNumericValue(puzzle[i][currentLocation.x]);
            }

            //if currentval isnt already in valuesInColumn, add it
            isNumInList = valuesInColumn.contains(currentval);
            if (!isNumInList && currentval != 0){
                valuesInColumn.add(currentval);
            }
        }

        return valuesInColumn;
    }

    //quick and cheap way to remove values
    //if the value has already appeared in the list, adds it to an arrayList
    //this arrayList will be used to remove those values from our options
    private ArrayList<Integer> getValuesInBox(){
        ArrayList<Integer> valuesInBox = new ArrayList<>();
        int[] coords = {currentLocation.x, currentLocation.y};
        //stores which of the 9 boxes our coordinate is located in
        int[] whichBox = {currentLocation.x / 3, currentLocation.y / 3};
        //stores the location of the top left coord of the box
        int[] boxTopLeft = {whichBox[0] * 3, whichBox[1] * 3};

        int currentval = 0;
        boolean isNumInList;

        //iterates through the 3x3 box we are looking at
        for (int i = boxTopLeft[0]; i < (boxTopLeft[0] + 3); i++){
            for (int j = boxTopLeft[1]; j < (boxTopLeft[1] + 3); j++){
                //if we know the value of the square we are checking, set currentval equal to it
                if (puzzle[i][j] != '.'){
                    currentval = Character.getNumericValue(puzzle[i][j]);
                    }

                //if the currentval is not in the list, and not 0, add it to the list
                isNumInList = valuesInBox.contains(currentval);
                if (!isNumInList && currentval != 0){
                    valuesInBox.add(currentval);
                }

            }
        }
        return valuesInBox;
    }

    private boolean unsolvedValues(){
        //TODO Implement this, returns true if there are any '.' in the puzzle and false if not
    }
}
