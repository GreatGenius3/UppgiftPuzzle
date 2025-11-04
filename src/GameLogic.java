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

    private BrickDirection brickLastMoveDir;

    // Konstruktor
    GameLogic ()
    {
        brickLastMoveDir = BrickDirection.MOVE_UP;
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

    public int findFromDirection(int index, BrickDirection dir)
    {
        // Hämta ett index beroende på vilken rikning
        // vi har valt
        // Ta fram den raden och kolumnen som indexet
        // ligger på
        int row = index / puzzleCells;
        int col = index % puzzleCells;
        int resultNextIndex = 0;

        // Beräknar indexet beroende på rikning
        switch (dir)
        {
                // Upp?
            case MOVE_UP:
                row -= 1;
                break;
                // Ner?
            case MOVE_DOWN:
                row += 1;
                break;
                // Vänster
            case MOVE_LEFT:
                col -= 1;
                break;
                // Höger
            case MOVE_RIGHT:
                col += 1;
                break;

        }

        // Sen räknar vi ut den nya indexen
        resultNextIndex = row * puzzleCells + col;

        return resultNextIndex;

    }

    // Byt plats på brickor
    public void switchBricks(int index1, int index2)
    {
        int temp = puzzel.get(index1);
        puzzel.set(index1, puzzel.get(index2));
        puzzel.set(index2, temp);
    }

    // --------------------------------------------------------------
    // Metoder
    // --------------------------------------------------------------
    // Skapar ett nytt spel med antal rader och celler
    public void newGame(int row, int cells)
    {

    }

    void incrementMoves()
    {
        moves++;
    }

    // En metod som löser spelet
    public void solve()
    {
        IO.println("Please enter the number of puzzles: ");
    }

    public boolean isSolved()
    {
        int counter = 1;
        for (int i = 0; i < getAmountOfPuzzles() - 1; i++)
        {
            if (puzzel.get(i) != counter)
                return false;
            counter++;
        }
        return true;
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
}
