# sudokuSolve

A java application to help you solve any Sudoku puzzles. Built in 2018.

### Code logic
Built on object-oriented principles learned from introductory CS courses. 
The application transfers the board to a double array Board object, with parts abstracted to the Block object.
The Block object is then easily able to test against a Sudoku puzzle's row, column, and square limitations.
To solve the puzzle, the application follows these steps:
1. The Board runs through all of the basic Sudoku limitations to add correct values to the board.
2. When it runs out of the naive solutions, it uses a game tree search to save the current state, and then selects a potential solution value.
3. Repeats Steps 1 and 2, until one of two things happens:
  1. It reaches an error and will then go back to the last saved state from Step 3 to try the other potential solution value.
  2. It reaches the complete puzzle solution.

### How to use
To use, compile Main.java, run it, and then input your sudoku puzzle with space-separated numbers (with 0 as empty space), row by row from left to right
