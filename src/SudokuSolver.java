import java.util.ArrayList;
import java.util.Arrays;

//SudokuSolver is it's own class to avoid clutter in the main file
public class SudokuSolver {
    //the puzzle to be solved
    char[][] puzzle;

    //these two equal the coordinates of the position we are currently checking
    int xCoord;
    int yCoord;

    //constructor to allow passing in the puzzle, so i don't have to do it for each method
    public SudokuSolver (char[][] puzzle){
        this.puzzle = puzzle;
    }

    public char[][] solvePuzzle(){

    }

    //generates an array of all values 1-9
    //if any of those values occur within the row, column, or box of the space we are checking
    //it will remove those values from the array
    //a performance light way to whittle down our options very quickly
    private int[] checkOptions(){
        //creates an ArrayList of ints and appends all possible values for a cell
        ArrayList<Integer> options = new ArrayList<>();
        for (int i = 1; i < 9; i++){
            options.add(i);
        }
        //removes any values that already occur in the given row
        ArrayList<Integer> valuesInRow = getValuesInRow();
        for (int i: valuesInRow) {
            if (options.contains(valuesInRow.get(i))){
                options.remove(valuesInRow.get(i));
            }
        }

        //reomves any values that already occur in the given column
        ArrayList<Integer> valuesInColumn = getValuesInColumn();
        for (int j : valuesInColumn){
            if (options.contains(valuesInColumn.get(j))){
                options.remove(valuesInColumn);
            }
        }
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
            if (puzzle[yCoord][i] != '.'){
                currentval = Character.getNumericValue(puzzle[yCoord][i]);
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
            if (puzzle[i][xCoord] != '.'){
                currentval = Character.getNumericValue(puzzle[i][xCoord]);
            }

            //if currentval isnt already in valuesInColumn, add it
            isNumInList = valuesInColumn.contains(currentval);
            if (!isNumInList && currentval != 0){
                valuesInColumn.add(currentval);
            }
        }

        return valuesInColumn;
    }

    private ArrayList<Integer> getValuesInBox(){
        ArrayList<Integer> valuesInBox = new ArrayList<>();
        int[] coords = {xCoord, yCoord};
        int[] whichBox = {xCoord / 3, yCoord / 3};

    }

    private boolean unsolvedValues(){
        //TODO Implement this, returns true if there are any '.' in the puzzle and false if not
    }
}
