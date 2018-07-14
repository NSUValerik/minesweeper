package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Controller;
import ru.nsu.ccfit.berdov.minesweeper.text.Console;

final class CommandOpen extends Command
{
    private final Controller control;

    public CommandOpen(Controller ctrl)
    {
        control = ctrl;
    }

    public boolean execute(String[] args)
    {
        try
        {
            int x = Integer.parseInt(args[XCoordinate]);
            int y = Integer.parseInt(args[YCoordinate]);
            if(control.open(x, y))
            {
                Console.startTime();
            }
        }
        catch(NumberFormatException exception)
        {
            System.out.println("NumberFormatException: " + exception.toString());
        }
        return false;
    }
}