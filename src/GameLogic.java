public class GameLogic
{
    // Variabler
    private int[] puzzel;
    private int puzzleRow;
    private int puzzleCells;
    private int emptyIndex;

    // Konstruktor
    GameLogic ()
    {
        puzzleRow = 4;
        puzzleCells = 4;
        puzzel = new int[puzzleRow * puzzleCells];
        emptyIndex = puzzleRow * puzzleCells - 1;
    }
}
