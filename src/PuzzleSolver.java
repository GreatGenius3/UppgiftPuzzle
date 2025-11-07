import java.util.*;

public class PuzzleSolver
{
    private static final int[] ROW_OFFSETS = {-1, 1, 0, 0};  // Upp, ner, vänster, höger
    private static final int[] COL_OFFSETS = {0, 0, -1, 1};   // Upp, ner, vänster, höger

    public enum BrickDir { MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT }

    // Representerar ett tillstånd i pusslet med prioritet för A* //
    public static class Node implements Comparable<Node> {
        int[] state;
        List<BrickDir> path;
        int emptyIndex;
        int cost;  // Kostnad hittills (längden på path)
        int priority;  // Prioritet för A*: cost + heuristik

        Node(int[] state, List<BrickDir> path, int emptyIndex, int cost, int priority) {
            this.state = state.clone();
            this.path = new ArrayList<>(path);
            this.emptyIndex = emptyIndex;
            this.cost = cost;
            this.priority = priority;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return Arrays.equals(state, node.state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }
    }

    // Konverterar en lista till en array för effektivare jämförelser
    public static int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    // Beräknar Manhattan-avståndet för en bricka
    public static int calculateManhattanDistance(int index, int value, int size) {
        if (value == 0) return 0;  // Tom ruta
        int targetRow = (value - 1) / size;
        int targetCol = (value - 1) % size;
        int row = index / size;
        int col = index % size;
        return Math.abs(row - targetRow) + Math.abs(col - targetCol);
    }

    // Beräknar det totala Manhattan-avståndet för ett tillstånd
    public static int calculateTotalManhattanDistance(int[] state, int size) {
        int total = 0;
        for (int i = 0; i < state.length; i++) {
            total += calculateManhattanDistance(i, state[i], size);
        }
        return total;
    }

    // Kontrollerar om pusslet är löst
    public static boolean isSolved(int[] state) {
        for (int i = 0; i < state.length - 1; i++) {
            if (state[i] != i + 1) {
                return false;
            }
        }
        return state[state.length - 1] == 0;
    }

    // Hjälpmetod för att konvertera index till rad/kolumn
    public static int[] indexToPosition(int index, int size) {
        return new int[]{index / size, index % size};
    }

    // Hjälpmetod för att konvertera rad/kolumn till index
    public static int positionToIndex(int row, int col, int size) {
        return row * size + col;
    }

    // Hjälpmetod för att kontrollera om en position är giltig
    public static boolean isValid(int row, int col, int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // Löser pusslet med A* algoritm
    public static List<BrickDir> solvePuzzle(List<Integer> initialPuzzle, int emptyIndex, int puzzleSize) {
        // Konvertera till array för effektivare jämförelser
        int[] initialState = toArray(initialPuzzle);

        // Skapa en prioritetskö för A*
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        // Beräkna initial heuristik
        int initialHeuristic = calculateTotalManhattanDistance(initialState, puzzleSize);

        // Skapa startnoden
        Node startNode = new Node(
                initialState,
                new ArrayList<>(),
                emptyIndex,
                0,  // Initial kostnad
                initialHeuristic  // Prioritet = kostnad + heuristik
        );

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            // Hämta noden med lägst prioritet
            Node current = openSet.poll();

            // Om pusslet är löst, returnera vägen hit
            if (isSolved(current.state)) {
                return current.path;
            }

            // Lägg till i stängd mängd
            closedSet.add(Arrays.toString(current.state));

            // Hämta positionen för den tomma rutan
            int emptyRow = current.emptyIndex / puzzleSize;
            int emptyCol = current.emptyIndex % puzzleSize;

            // Testa alla fyra möjliga drag
            for (int i = 0; i < 4; i++) {
                int newRow = emptyRow + ROW_OFFSETS[i];
                int newCol = emptyCol + COL_OFFSETS[i];

                // Kontrollera om det nya draget är giltigt
                if (isValid(newRow, newCol, puzzleSize)) {
                    // Skapa en kopia av det nuvarande tillståndet
                    int[] newState = current.state.clone();
                    int newEmptyIndex = positionToIndex(newRow, newCol, puzzleSize);

                    // Byta plats på den tomma rutan och dess granne
                    int temp = newState[current.emptyIndex];
                    newState[current.emptyIndex] = newState[newEmptyIndex];
                    newState[newEmptyIndex] = temp;

                    // Skapa en ny väg
                    List<BrickDir> newPath = new ArrayList<>(current.path);
                    BrickDir direction = BrickDir.values()[i];
                    newPath.add(direction);

                    // Beräkna ny kostnad och heuristik
                    int newCost = current.cost + 1;
                    int newHeuristic = calculateTotalManhattanDistance(newState, puzzleSize);
                    int priority = newCost + newHeuristic;

                    // Skapa ny nod
                    Node neighbor = new Node(
                            newState,
                            newPath,
                            newEmptyIndex,
                            newCost,
                            priority
                    );

                    // Kontrollera om detta tillstånd redan har besökts
                    String stateString = Arrays.toString(newState);
                    if (!closedSet.contains(stateString)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // Om ingen lösning hittades
        return new ArrayList<>();
    }
}