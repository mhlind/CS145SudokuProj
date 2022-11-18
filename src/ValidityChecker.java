public class ValidityChecker {
    //Checks to make sure there are no repeat values in any of the rows
    private static boolean checkValidRows(){
        //TODO implement the row checker
    }

    //Checks to make sure there are no repeat values in any of the columns
    private static boolean checkValidColumns(){
        //TODO Implement the column checker
    }

    //Checks that there are no repeat values in any given box
    private static boolean checkValidBox(){
        //TODO Implement the box checker
    }

    //A Sudoku is unsolvable due to having more than one valid solution
    //I left this as optional, because technically there is a valid solution
    //but a proper Sudoku should only have one solution, not multiple
    //17 is the minimum # of values needed to have one and only one valid solution
    private static boolean checkEnoughValues(){
        //TODO Implement the amount of values checker
    }

    //Calls all of the checker methods and returns one single boolean of puzzle validity
    //Pretty much just one overglorified && statement
    public static boolean checkPuzzleValidity(){}


}
