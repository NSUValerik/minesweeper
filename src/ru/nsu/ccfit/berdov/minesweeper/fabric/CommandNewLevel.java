package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Controller;

final class CommandNewLevel extends Command
{
    private final Controller control;

    public CommandNewLevel(Controller ctrl)
    {
        control = ctrl;
    }

    public boolean execute(String[] args)
    {
        setField(control.generateField(args[LEVEL]));
        return true;
    }
}