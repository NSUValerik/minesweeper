package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Controller;

class CommandNewGame extends Command
{
    private final Controller control;

    public CommandNewGame(Controller ctrl)
    {
        control = ctrl;
    }

    public boolean execute(String[] args)
    {
        try
        {
            int width = Integer.parseInt(args[WIDTHSIZE]);
            int height = Integer.parseInt(args[HEIGHTSIZE]);
            int mines = Integer.parseInt(args[MINES]);
            setField(control.generateField(width, height, mines));
            return true;
        }
        catch (NumberFormatException exception)
        {
            System.out.println("NumberFormatException: " + exception.toString());
        }
        return false;
    }
}