package ru.nsu.ccfit.berdov.minesweeper;

import ru.nsu.ccfit.berdov.minesweeper.gui.ImageFrame;
import ru.nsu.ccfit.berdov.minesweeper.text.Console;

import java.awt.*;

public class MainClass
{
    public static void main(String[] args)
    {
        if (0 < args.length)
        {
            Controller control = new Controller();
            Console textGame = new Console(control, control.generateField("Beginner"));
            textGame.start();
        }
        else
        {
            final Controller control = new Controller();
            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    new ImageFrame(control.generateField("Beginner"), control);
                }
            });
        }
    }
}