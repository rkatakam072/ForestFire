
public class Simulator {
    public static final int LIVE_TREE = 1;
    public static final int EMPTY = 0;
    public static final int FIRE = 2;
    public static final int ASH = 3;

    private int[][] grid;
    private int time = 0;

    /***
     * Create a new simulator.  The simulator creates a new Forest of size (r, c).
     *
     * @param r
     * @param c
     */
    public Simulator(int r, int c) {
        grid = new int[r][c];
    }

    public void initialize(double probability) {
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (Math.random() < probability) {
                    grid[r][c] = LIVE_TREE;
                }
            }
        }

        time = 0;
    }

    public int count(int state) {
        int count = 0;
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (grid[r][c] == state) count++;
            }
        }

        return count;
    }

    public void runOneStep() {
        if (count(FIRE) == 0) return;

        int[][] next = makeCopy(grid);

        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                int state = grid[r][c];

                if (state == LIVE_TREE && countAdjacent(r, c, FIRE) > 0) {
                    next[r][c] = FIRE;
                } else if (state == FIRE) {
                    next[r][c] = ASH;
                }

            }
        }

        grid = next;
        time++;
    }

    private int[][] makeCopy(int[][] grid) {
        int[][] next = new int[getHeight()][getWidth()];
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                next[r][c] = grid[r][c];
            }
        }

        return next;
    }

    private int countAdjacent(int r, int c, int state) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (!(rowOffset == 0 && colOffset == 0)) {
                    if (getStateOf(r + rowOffset, c + colOffset) == state) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private int getStateOf(int r, int c) {
        if (!inBounds(r, c)) return -1;
        return grid[r][c];
    }

    private boolean inBounds(int r, int c) {
        if (r < 0 || c < 0) return false;
        if (r >= getHeight() || c >= getWidth()) return false;
        return true;
    }

    public void setPointFire() {
        if (count(LIVE_TREE) == 0) return;

        int r, c;
        do {
            r = (int) (Math.random() * getHeight());
            c = (int) (Math.random() * getWidth());
        } while (grid[r][c] != LIVE_TREE);

        grid[r][c] = FIRE;
    }

    // TODO: add methods outlines in assignment sheet
    // * way to get statistical information about the current state of the simulation
    // * way to run simulation for one ste
    // * way to (re-)initialize your forest with particular tree density
    //

    public void fullRun(double prob) {
        initialize(prob);
        setPointFire();
        while (count(FIRE) > 0) {
            runOneStep();
        }
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

    public int[][] getDisplayGrid() {
        return grid;
    }

    public double size() {
        return getWidth() * getHeight();
    }
}