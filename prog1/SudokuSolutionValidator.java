/*
Sean Marino
CSC 4103 - Chen
Sudoku Solution Validator
16 February 2017
 */
package sudokusolutionvalidator;
public class SudokuSolutionValidator 
{
    private static final int[][] sudokuPuzzle = 
       {{6, 2, 4, 5, 3, 9, 1, 8, 7},
        {5, 1, 9, 7, 2, 8, 6, 3, 4},
        {8, 3, 7, 6, 1, 4, 2, 9, 5},
        {1, 4, 3, 8, 6, 5, 7, 2, 9},
        {9, 5, 8, 2, 4, 7, 3, 6, 1},
        {7, 6, 2, 3, 9, 1, 4, 5, 8},
        {3, 7, 1, 9, 5, 6, 8, 4, 2},
        {4, 9, 6, 1, 8, 2, 5, 7, 3},
        {2, 8, 5, 4, 7, 3, 9, 1, 6}};
    private static boolean[] valid;
    private static int threadCount = 27;
    public static class RowsAndColumns
    {
        int row;
        int col;
        RowsAndColumns(int row, int column)
        {
            this.row = row;
            this.col = column;
        }
    }
    
    public static void main(String[] args)
    {
        int thread = 0;
        valid = new boolean[threadCount];
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (i == 0)
                {
                    threads[thread++] = new Thread(new ColumnValidator(i, j));
                }
                if (j == 0)
                {
                    threads[thread++] = new Thread(new RowValidator(i, j));
                }
                if (i%3 == 0 && j%3 == 0)
                {
                    threads[thread++] = new Thread(new BoxValidator(i, j));
                }
            }
        }
        
        for (Thread thread1 : threads) {
            thread1.start();
        }
        
        for (Thread thread1 : threads) {
            try {
                thread1.join();
            }catch(InterruptedException e)
            {
            }
        }
        
        for (int i = 0; i < valid.length; i++)
        {
            if (!valid[i])
            {
                System.out.println("Invalid Solution");
                return;
            }
        }
        System.out.println("Valid Solution");
    }
    
    //Checks that each column contains digits 1..9
    public static class ColumnValidator extends RowsAndColumns implements Runnable
    {
        ColumnValidator(int row, int column){
                super(row, column);}
        
        @Override
        public void run()
        {
            boolean[] validSolution = new boolean[9];
            
            if ((row != 0 || col > 8) && (col != 0 || row > 8))
            {
                System.out.println("Invalid Solution");
                return;
            }
            for (int i = 0; i < 9; i++)
            {
                int num = sudokuPuzzle[i][col];
                if (num < 1 || num > 9 || validSolution[num - 1])
                {
                    return;
                }
                else if (!validSolution[num - 1])
                {
                    validSolution[num - 1] = true;
                }
            }
            valid[18 + col] = true;
        }
    }
    //Checks that each row contains digits 1..9
    public static class RowValidator extends RowsAndColumns implements Runnable
    {
        RowValidator(int row, int column){
                super(row, column);}
        
        @Override
        public void run()
        {
            boolean[] validSolution = new boolean[9];
            for (int i = 0; i < 9; i++)
            {
                int num = sudokuPuzzle[row][i];
                if (num < 1 || num > 9 || validSolution[num - 1])
                {
                    return;
                }
                else if (!validSolution[num - 1])
                {
                    validSolution[num - 1] = true;
                }
            }
            valid[9 + row] = true;
        }
    }  
    //Checks that each of the 3x3 subgrids contain digits 1..9
    public static class BoxValidator extends RowsAndColumns implements Runnable
    {
        BoxValidator(int row, int column)
        {
            super(row, column);
        }
        
        @Override
        public void run()
        {
            boolean[] validSolution = new boolean[9];
            if(row > 6 || row % 3 != 0 || col > 6 || col % 3 != 0)
            {
                System.out.println("Invalid Solution");
                return;
            }
            for (int i = row; i < row + 3; i++)
                for (int j = col; j < col + 3; j++)
                {
                    int num = sudokuPuzzle[i][j];
                    if (num < 1 || num > 9 || validSolution[num - 1])
                        return;
                    else
                        validSolution[num - 1] = true;
                }
            valid[row + col / 3] = true;
        }
    }
}
