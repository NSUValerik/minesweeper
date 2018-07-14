package ru.nsu.ccfit.berdov.minesweeper.text;

import ru.nsu.ccfit.berdov.minesweeper.Controller;
import ru.nsu.ccfit.berdov.minesweeper.HighScore;
import ru.nsu.ccfit.berdov.minesweeper.Model;
import ru.nsu.ccfit.berdov.minesweeper.fabric.Chooser;
import ru.nsu.ccfit.berdov.minesweeper.fabric.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.Timer;

public class Console
{
    private final Controller control;
    private Model field;
    private final HighScore scores;
    private static Timer timer;
    private Chooser chooser;
    private int time;

    private static final int COMMAND = 0;

    private void print()
    {
        System.out.print("  ");
        for (int i = 0; i < field.getWidht(); i++)
        {
            System.out.print(i);
        }
        System.out.println();
        for (int i = 0; i < field.getHeight(); i++)
        {
            System.out.print(i + " ");
            for (int j = 0; j < field.getWidht(); j++)
            {
                if (Controller.LOSE != field.getGameState())
                {
                    if (Controller.OPEN == field.getState(j, i))
                    {
                        if (Controller.MINE == field.getValue(j, i))
                        {
                            System.out.print("*");
                        }
                        else if (Controller.EMPTY == field.getValue(j, i))
                        {
                            System.out.print(' ');
                        }
                        else
                        {
                            System.out.print(field.getValue(j, i));
                        }
                    }
                    else if (Controller.FLAG == field.getState(j, i))
                    {
                        System.out.print("f");
                    }
                    else if (Controller.QUESTION == field.getState(j, i))
                    {
                        System.out.print("?");
                    }
                    else
                    {
                        System.out.print("#");
                    }
                }
                else
                {
                    if (Controller.MINE == field.getValue(j, i))
                    {
                        System.out.print("*");
                    }
                    else if (Controller.EMPTY == field.getValue(j, i))
                    {
                        System.out.print(' ');
                    }
                    else
                    {
                        System.out.print(field.getValue(j, i));
                    }
                }
            }
            System.out.println();
        }
    }

    private boolean isLevelStandart()
    {
        return "Beginner".equals(control.getLevel()) || "Amateur".equals(control.getLevel()) || "Professional".equals(control.getLevel());
    }

    private boolean getSituation()
    {
        if (Controller.LOSE == field.getGameState())
        {
            timer.stop();
            print();
            System.out.println("YOU LOSE!!!");
            time = 0;
            return true;
        }
        else if (Controller.WIN == field.getGameState())
        {
            timer.stop();
            print();
            if (isLevelStandart())
            {
                BufferedReader inStream = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter your name, please:");
                String name = "";
                try
                {
                    name = inStream.readLine();
                }
                catch (IOException exception)
                {
                    System.out.println("IOException: " + exception.toString());
                }
                if (1 == scores.setRecord(time, name, control.getLevel()))
                {
                    System.out.println("YOU SET NEW RECORD, CONGRATULATION!!!\nYour time - " + time);
                }
                else
                {
                    System.out.println("YOU WIN, CONGRATULATION!!!\nYour time - " + time);
                }
            }
            else
            {
                System.out.println("YOU WIN, CONGRATULATION!!!");
            }
            time = 0;
            return true;
        }
        return false;
    }

    public static void startTime()
    {
        if (!timer.isRunning())
        {
            timer.start();
        }
    }

    public Console(Controller ctrl, Model gameField)
    {
        assert (null != ctrl && null != gameField);
        control = ctrl;
        field = gameField;
        scores = new HighScore();
        chooser = new Chooser(control, scores);
        timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                ++time;
            }
        });
    }

    public void start()
    {
        BufferedReader inStream = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You can choose level: Beginner, Amateur, Professional\nUse this commands: new_game; new_level; end_game; open; high_score; open_all; flag;");
        for (; ;)
        {
            print();
            String[] expr;
            String command = "";
            try
            {
                command = inStream.readLine();
            }
            catch (IOException exception)
            {
                System.out.println("IOException: " + exception.toString());
            }
            expr = command.split(" ");
            Command instruction = chooser.getCommand(expr[COMMAND]);
            try
            {
                if (instruction.execute(expr))
                {
                    field = instruction.getField();
                    if (null == field)
                    {
                        System.out.println("You enter wrong level\n");
                        field = control.generateField("Beginner");
                    }
                }
                if (getSituation())
                {
                    return;
                }
            }
            catch (NullPointerException exception)
            {
                System.out.println("NullPointerException: " + exception.toString());
            }
        }
    }
}
