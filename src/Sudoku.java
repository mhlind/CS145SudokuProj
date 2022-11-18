public class Sudoku {
    public static boolean checkPuzzle(char[][] puzzle){
        //TODO Implement check puzzle method, probably at the end
        return false;
    }
    public static void main(String[] args) {
        //generates our puzzle
        char[][] puzzle;
        while (!ValidityChecker.checkPuzzleValidity()){
            puzzle = SudokuP.puzzle();
        }

    }
}