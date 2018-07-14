package ru.nsu.ccfit.berdov.minesweeper;

import java.util.Random;

public class Controller
{
    private Model field;
    private int mineCount;
    private int openPositions;
    private int flagCount;
    private String level;

    public static final int MIN_SIZE = 9;
    public static final int MAX_WIDHT = 30;
    public static final int MAX_HEIGHT = 24;
    public static final int MIN_MINES = 10;
    public static final double FACTOR = 0.8;

    public static final int EMPTY = 0;
    public static final int MINE = -1;

    public static final int WIN = 2;
    public static final int OPEN = 1;
    private static final int CLOSED = 0;
    public static final int FLAG = -1;
    public static final int LOSE = -2;
    public static final int QUESTION = -3;

    private boolean first;
    private static final int ZERO = 0;

    private void arrangeMines(int x, int y)
    {
        assert (ZERO <= x && field.getWidht() > x && ZERO <= y && field.getHeight() > y);
        int left = Math.max(0, x - 1);
        int right = Math.min(field.getWidht() - 1, x + 1);
        int top = Math.max(0, y - 1);
        int bottom = Math.min(field.getHeight() - 1, y + 1);
        for (int k = left; k <= right; k++)
        {
            for (int l = top; l <= bottom; l++)
            {
                field.setValue(k, l, MINE);
            }
        }
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < mineCount; i++)
        {
            int dx = random.nextInt(field.getWidht());
            int dy = random.nextInt(field.getHeight());
            if (EMPTY != field.getValue(dx, dy))
            {
                i--;
                continue;
            }
            field.setValue(dx, dy, MINE);
        }
        for (int k = left; k <= right; k++)
        {
            for (int l = top; l <= bottom; l++)
            {
                field.setValue(k, l, EMPTY);
            }
        }
        createField();
    }

    private void createField()
    {
        for (int i = 0; i < field.getWidht(); i++)
        {
            for (int j = 0; j < field.getHeight(); j++)
            {
                if (MINE == field.getValue(i, j))
                {
                    int left = Math.max(0, i - 1);
                    int right = Math.min(field.getWidht() - 1, i + 1);
                    int top = Math.max(0, j - 1);
                    int bottom = Math.min(field.getHeight() - 1, j + 1);
                    for (int k = left; k <= right; k++)
                    {
                        for (int l = top; l <= bottom; l++)
                        {
                            if (MINE != field.getValue(k, l))
                            {
                                field.setValue(k, l, field.getValue(k, l) + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void openEmpty(int x, int y)
    {
        assert (ZERO <= x && field.getWidht() > x && ZERO <= y && field.getHeight() > y);
        if (OPEN == field.getState(x, y))
        {
            return;
        }
        field.setState(x, y, OPEN);
        openPositions++;
        if (EMPTY != field.getValue(x, y))
        {
            return;
        }
        int left = Math.max(0, x - 1);
        int right = Math.min(field.getWidht() - 1, x + 1);
        int top = Math.max(0, y - 1);
        int bottom = Math.min(field.getHeight() - 1, y + 1);
        for (int i = left; i <= right; i++)
        {
            for (int j = top; j <= bottom; j++)
            {
                openEmpty(i, j);
            }
        }
    }

    public Model generateField(int widthSize, int heightSize, int mines)
    {
        if (MIN_SIZE > widthSize)
        {
            widthSize = MIN_SIZE;
        }
        if (MIN_SIZE > heightSize)
        {
            heightSize = MIN_SIZE;
        }
        if (MAX_WIDHT < widthSize)
        {
            widthSize = MAX_WIDHT;
        }
        if (MAX_HEIGHT < heightSize)
        {
            heightSize = MAX_HEIGHT;
        }
        if (MIN_MINES > mines)
        {
            mines = MIN_MINES;
        }
        if (mines > widthSize * heightSize * FACTOR)
        {
            mines = (int) (widthSize * heightSize * FACTOR);
        }
        mineCount = mines;
        level = "Special";
        openPositions = 0;
        flagCount = 0;
        field = new Model(widthSize, heightSize);
        first = true;
        return field;
    }

    public Model generateField(String level)
    {
        assert (null != level);
        if ("Beginner".equals(level))
        {
            Model beginner = generateField(9, 9, 10);
            this.level = level;
            return beginner;
        }
        else if ("Amateur".equals(level))
        {
            Model amateur = generateField(16, 16, 40);
            this.level = level;
            return amateur;
        }
        else if ("Professional".equals(level))
        {
            Model professional = generateField(30, 16, 99);
            this.level = level;
            return professional;
        }
        else if ("Same".equals(level))
        {
            String tmp = this.level;
            Model same = generateField(field.getWidht(), field.getHeight(), mineCount);
            this.level = tmp;
            return same;
        }
        return null;
    }

    public Model generateSameField()
    {
        openPositions = 0;
        flagCount = 0;
        first = false;
        field.setGameState(0);
        for(int i=0;i<field.getWidht();i++)
        {
            for(int j =0;j<field.getHeight();j++)
            {
                field.setState(i,j,CLOSED);
            }
        }        
        return field;
    }

    public String getLevel()
    {
        return level;
    }

    public int getFlagRemains()
    {
        return mineCount - flagCount;
    }

    public boolean open(int x, int y)
    {
        if (field.getWidht() <= x || field.getHeight() <= y)
        {
            return false;
        }
        if (first)
        {
            arrangeMines(x, y);
            first = false;
        }
        if (FLAG == field.getState(x, y))
        {
            return true;
        }
        if (MINE == field.getValue(x, y))
        {
            field.setState(x, y, OPEN);
            field.setGameState(LOSE);
            return true;
        }
        openEmpty(x, y);
        if (mineCount == field.getHeight() * field.getWidht() - openPositions)
        {
            field.setGameState(WIN);
        }
        return true;
    }

    public void openAll(int x, int y)
    {
        if (field.getWidht() <= x || field.getHeight() <= y)
        {
            return;
        }
        if (OPEN == field.getState(x, y))
        {
            int top = Math.max(0, y - 1);
            int bottom = Math.min(field.getHeight() - 1, y + 1);
            int left = Math.max(0, x - 1);
            int right = Math.min(field.getWidht() - 1, x + 1);
            int count = 0;
            for (int i = left; i <= right; i++)
            {
                for (int j = top; j <= bottom; j++)
                {
                    if (FLAG == field.getState(i, j))
                    {
                        count++;
                    }
                }
            }
            if (count == field.getValue(x, y))
            {
                for (int i = left; i <= right; i++)
                {
                    for (int j = top; j <= bottom; j++)
                    {
                        open(i, j);
                    }
                }
            }
        }
    }

    public void putFlag(int x, int y)
    {
        if (field.getWidht() <= x || field.getHeight() <= y)
        {
            return;
        }
        if (CLOSED == field.getState(x, y))
        {
            flagCount++;
            field.setState(x, y, FLAG);
        }
        else if (FLAG == field.getState(x, y))
        {
            flagCount--;
            field.setState(x, y, QUESTION);
        }
        else if (QUESTION == field.getState(x, y))
        {
            field.setState(x, y, CLOSED);
        }
    }
}