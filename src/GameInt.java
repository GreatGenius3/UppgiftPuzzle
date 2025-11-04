public interface GameInt
{
    enum GameStatus { IN_PROGRESS, SOLVED }
    void newGame(int row, int cells);
    void solve();
    void reset();
    void settings();
}
