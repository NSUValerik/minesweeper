package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Controller;

final class CommandFlag extends Command
{
    private final Controller control;

    public CommandFlag(Controller ctrl)
    {
        control = ctrl;
    }

    public boolean execute(String[] args)
    {
        try
        {
            int x = Integer.parseInt(args[XCoordinate]);
            int y = Integer.parseInt(args[YCoordinate]);
            control.putFlag(x, y);
        }
        catch (NumberFormatException exception)
        {
            System.out.println("NumberFormatException: " + exception.toString());
        }
        return false;
    }
}
