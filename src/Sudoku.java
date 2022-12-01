public class Sudoku {

    public static void main(String[] args) {
            //generates our puzzle
            char[][] puzzle = SudokuP.puzzle();
            System.out.println("Unsolved Puzzle: ");
            printPuzzle(puzzle);
            if (solve(puzzle)) {
                System.out.println("The Puzzle has been solved!");
                printPuzzle(puzzle);
            } else {
                printPuzzle(puzzle);
                System.out.println("The puzzle is unsolvable :(");
            }
    }



    public static void printPuzzle(char[][] puzzle){
        //sb to allow modifying the output until it is fully created and ready to print
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < puzzle.length; i++){
            for (int j = 0; j < puzzle[i].length; j++){
                output.append(puzzle[i][j] + " ");
                //puts a space between groupings of 3 to visually separate the "blocks"
                if (((j + 1) % 3 == 0)) {
                    output.append("  ");
                }
            }
            //newlines each row of the puzzle
            output.append("\n");
            //after 3 rows, it will add an extra newline to visually separate "blocks"
            if (((i + 1) % 3 == 0) && (i != 0)){
                output.append("\n");
            }
        }

        System.out.print(output);
    }

    public static boolean solve (char[][] puzzle){
        SudokuSolver solver = new SudokuSolver(puzzle);
        return solver.backtrack();
    }
}
