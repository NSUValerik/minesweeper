package ru.nsu.ccfit.berdov.minesweeper;

public class    Model
{
    private final int[][] field;
    private final int[][] gameField;
    private final int width;
    private final int height;
    private int gameState;

    private static final int ZERO = 0;

    public Model(int widthSize, int heightSize)
    {
        width = widthSize;
        height = heightSize;
        field = new int[width][height];
        gameField = new int[width][height];
    }

    public int getWidht()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setValue(int x, int y, int value)
    {
        assert (ZERO <= x && width > x && ZERO <= y && height > y);
        field[x][y] = value;
    }

    public int getValue(int x, int y)
    {
        assert (ZERO <= x && width > x && ZERO <= y && height > y);
        return field[x][y];
    }

    public void setState(int x, int y, int state)
    {
        assert (ZERO <= x && width > x && ZERO <= y && height > y);
        gameField[x][y] = state;
    }

    public int getState(int x, int y)
    {
        assert (ZERO <= x && width > x && ZERO <= y && height > y);
        return gameField[x][y];
    }

    public int getGameState()
    {
        return gameState;
    }

    public void setGameState(int state)
    {
        gameState = state;
    }
}
