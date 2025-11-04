import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic implements GameInt
{
    // Variabler
    private List<Integer> puzzel;
    private int puzzleRow;
    private int puzzleCells;
    private int emptyIndex;
    private int moves;

    private GameStatus gameStatus;

    // Konstruktor
    GameLogic ()
    {
        puzzleRow = 4;
        puzzleCells = 4;
        puzzel = new ArrayList<>();
        emptyIndex = puzzleRow * puzzleCells - 1;
        for(int i = 0; i < (puzzleRow * puzzleCells); i++)
            puzzel.add(i + 1);
        puzzel.set(emptyIndex, 0);
        moves = 0;
    }
    // --------------------------------------------------------------
    // Geters
    // --------------------------------------------------------------
    public int getPuzzleRow()
    {
        return puzzleRow;
    }
    public int getPuzzleCells()
    {
        return puzzleCells;
    }
    public int getAmountOfPuzzles()
    {
        return puzzleRow * puzzleCells;
    }
    public int getEmptyIndex()
    {
        return emptyIndex;
    }
    public int getMoves()
    {
        return moves;
    }
    public GameStatus getGameStatus()
    {
        return gameStatus;
    }

    public List<Integer> getPuzzel()
    {
        return puzzel;
    }

    public int getPuzzel(int index)
    {
        return puzzel.get(index);
    }

    // --------------------------------------------------------------
    // Metoder
    // --------------------------------------------------------------
    // Skapar ett nytt spel med antal rader och celler
    public void newGame(int row, int cells)
    {

    }

    // En metod som löser spelet
    public void solve()
    {
        IO.println("Please enter the number of puzzles: ");
    }

    // En metod som resetar spelet
    public void reset()
    {
        puzzel = new ArrayList<>();
        /*
        puzzel = new int[puzzleRow * puzzleCells];
        for (int i = 0; i < (puzzleRow * puzzleCells); i++)
        {
            puzzel[i] = i + 1;
            if (i == (puzzleRow * puzzleCells) - 1)
                puzzel[i] = 0;
        }
        emptyIndex = puzzleRow * puzzleCells - 1;
        moves = 0;
        */
    }

    // Blanda alla siffror i arrayen
    public void shuffle()
    {

    }

    // inställningar
    public void settings()
    {

    }
}
