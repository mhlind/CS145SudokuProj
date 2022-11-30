import java.awt.*;
import java.util.ArrayList;

//SudokuSolver is its own class to avoid clutter in the main file
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

    //itertates through each value on the board, guessing values that have not been guessed yet
    //and are also not already present on their row/column/box
    //if it runs out of values to guess in a certain square
    //it goes back to the square before, and guesses the next available value
    public boolean backtrack(){
        ArrayList<UnsolvedPoints> unknownPoints = new ArrayList<>();
        ArrayList<Point> knownCoords = new ArrayList<>();
        //iterate through puzzle, logging all known numbers to the ArrayList
        //this will keep us from overriding these values
        for (int i = 0; i < puzzle.length; i++){
            for (int j = 0; j < puzzle[i].length; j++){
                if (puzzle[i][j] != '.'){
                    knownCoords.add(new Point(j , i));
                }
                else {
                    unknownPoints.add(new UnsolvedPoints(new Point(j , i)));
                }
            }
        }



        //stores whether the last movement was to decrement
        boolean hasDecremented = false;
        //iterates through all available spots on the board
        //will exit if it runs out of spots on the board, whether it be through the beginning or end
        while ((currentLocation.y < 9) && (currentLocation.y > -1)){

            //if the current values was not one of our given values
            if (!knownCoords.contains(currentLocation)){
                int valueOfCurrentSquare;
                //if we've already guessed this square, start our guesses at the value of the square
                if (puzzle[currentLocation.y][currentLocation.x] != '.'){
                     valueOfCurrentSquare =
                            Character.getNumericValue(puzzle[currentLocation.y][currentLocation.x]);
                     //otherwise, start the guesses at 1
                }else {
                    valueOfCurrentSquare = 1;
                }
                //generates our list of possible guesses
                //might be possible to store the list for every single square on the board
                //but its not that expensive to regenerate

                //ArrayList<Integer> options = checkOptions(valueOfCurrentSquare);
                ArrayList<Integer> options = new ArrayList<>();
                options.addAll(checkOptions(valueOfCurrentSquare));
                //if we aren't out of options
                if (!options.isEmpty()){
                    //gets our next available guess, and casts it to a char
                    int nextGuessInt = options.get(0);
                    char nextGuess = Character.forDigit(nextGuessInt, 10);
                    //then sets our current square to that guess
                    puzzle[currentLocation.y][currentLocation.x] = nextGuess;

                    //moves to the next square;
                    currentLocation = incrementLocation(currentLocation);
                    hasDecremented = false;
                }
                //otherwise, if we are out of guesses for this point
                //go back to the last one and try a new guess
                else {
                    currentLocation = decrementLocation(currentLocation);
                    hasDecremented = true;
                }
            }
            //these protect from infinite loop if we guess a wrong value next to a known value
            //it will just decrement again
            else if (!hasDecremented){
                currentLocation = incrementLocation(currentLocation);
                hasDecremented = false;
            }
            else {
                currentLocation = decrementLocation(currentLocation);
                hasDecremented = true;
            }
        }

        //if we have decremented back to the beginning of the board
        //that means there are no possible values for the first square that make some other square
        //solvable, and therefore, the puzzle cannot be solved
        if (currentLocation.y == -1){
            return false;
        }
        //Otherwise we will have iterated through the entire board with solutions
        //and the puzzle will be solved!
        else{
            return true;
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
        for (int i = thisValue; i <= 9; i++){
            options.add(i);
        }
        //removes any values that already occur in the given row
        ArrayList<Integer> valuesInRow = getValuesInRow();
        for (int i = 0; i < valuesInRow.size(); i++) {
            if (options.contains(valuesInRow.get(i))){
                int valueIndex = options.indexOf(valuesInRow.get(i));
                options.remove(valueIndex);
            }
        }

        //reomves any values that already occur in the given column
        ArrayList<Integer> valuesInColumn = getValuesInColumn();
        for (int j = 0; j < valuesInColumn.size(); j++){
            //remove deletes the value at index
            //TODO replace all occurences of remove with a method that deletes that value
            if (options.contains(valuesInColumn.get(j))){
                int valueIndex = options.indexOf(valuesInColumn.get(j));
                options.remove(valueIndex);
            }

        }

        //removes any values that already occur in the given box
        ArrayList<Integer> valuesInBox = getValuesInBox();
        for (int k = 0; k < valuesInBox.size(); k++){
            if (options.contains(valuesInBox.get(k))){
                int valueIndex = options.indexOf(valuesInBox.get(k));
                options.remove(valueIndex);
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
        for (char[] chars : puzzle) {
            int currentval = 0;
            //if we know the value of the current square, set currentval equal to it
            if (chars[currentLocation.x] != '.') {
                currentval = Character.getNumericValue(chars[currentLocation.x]);
            }

            //if currentval isnt already in valuesInColumn, add it
            isNumInList = valuesInColumn.contains(currentval);
            if (!isNumInList && currentval != 0) {
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
        //stores which of the 9 boxes our coordinate is located in
        //int[] whichBox = {currentLocation.x / 3, currentLocation.y / 3};
        Point whichBox = new Point(currentLocation.x / 3, currentLocation.y / 3);
        //stores the location of the top left coord of the box
        //int[] boxTopLeft = {whichBox[0] * 3, whichBox[1] * 3};
        Point boxTopLeft = new Point(whichBox.x * 3 , whichBox.y * 3);

        int currentval = 0;
        boolean isNumInList;

        //iterates through the 3x3 box we are looking at
        for (int i = boxTopLeft.y; i < (boxTopLeft.y + 3); i++){
            for (int j = boxTopLeft.x; j < (boxTopLeft.x + 3); j++){
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

    //handles the backtracking when needed
   //returns a point which we can get the x and y values of to set the values of i and j in the while loop
   private Point decrementLocation(Point currentLocation){
        Point newLocation = new Point();
        //easiest case, if our x value is greater than 0, subtract one from the x value and move on
        if (currentLocation.x > 0){
            newLocation.setLocation(currentLocation.x - 1, currentLocation.y);
        }
        //otherwise if it is equal to 0 (or less somehow), we decrement the y instead, and reset the x to its max, 8
        else {
            newLocation.setLocation(8, currentLocation.y - 1);
        }
        return newLocation;
    }

    //handles movement to the next square once we've made a guess
    //returns the new point, does not automatically set currentlocation equal to it
    private Point incrementLocation(Point currentLocation){
        Point newLocation = new Point();
        //easiest case, if x value is < 8, add one to the x value and move on
        if (currentLocation.x < 8){
            newLocation.setLocation(currentLocation.x + 1, currentLocation.y);
        }
        //otherwise, set x back to 0, and increment y instead
        else {
            newLocation.setLocation(0 , currentLocation.y + 1);
        }

        return newLocation;
    }
}
