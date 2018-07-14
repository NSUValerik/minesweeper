package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Model;

public abstract class Command
{
    private Model field;

    static final int XCoordinate = 1;
    static final int YCoordinate = 2;
    static final int WIDTHSIZE = 1;
    static final int HEIGHTSIZE = 2;
    static final int MINES = 3;
    static final int LEVEL = 1;

    void setField(Model gameField)
    {
        field = gameField;
    }

    public Model getField()
    {
        return field;
    }

    public abstract boolean execute(String[] args);
}