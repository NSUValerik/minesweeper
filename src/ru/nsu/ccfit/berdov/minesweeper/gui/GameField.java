package ru.nsu.ccfit.berdov.minesweeper.gui;

import ru.nsu.ccfit.berdov.minesweeper.Controller;
import ru.nsu.ccfit.berdov.minesweeper.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class GameField extends JPanel
{
    private final Image cell;
    private final Image mine;
    private final Image boom;
    private final Image flag;
    private final Image[] array;
    private final Image question;
    private Controller control;
    private Model field;
    private final ImageFrame frame;
    private final JLabel MineCount;
    private boolean flagMouse = true;

    private void start()
    {
        Timer time = frame.getTimer();
        if (!time.isRunning())
        {
            time.start();
        }
    }

    private void getSituation()
    {
        if (Controller.LOSE == field.getGameState())
        {
            repaint();
            frame.lose();
        }
        else if (Controller.WIN == field.getGameState())
        {
            repaint();
            frame.win();
        }
    }

    public GameField(ImageFrame imageFrame, Controller ctrl, Model gameField, JLabel mines)
    {
        assert (null != imageFrame && null != ctrl && null != gameField && null != mines);
        frame = imageFrame;
        control = ctrl;
        field = gameField;
        MineCount = mines;
        cell = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/cell.png");
        mine = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/mine.png");
        boom = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/boom.png");
        flag = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/flag.png");
        question = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/question.png");
        Image empty = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/empty.png");
        Image one = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/1.png");
        Image two = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/2.png");
        Image three = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/3.png");
        Image four = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/4.png");
        Image five = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/5.png");
        Image six = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/6.png");
        Image seven = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/7.png");
        Image eight = frame.createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/8.png");
        array = new Image[]{empty, one, two, three, four, five, six, seven, eight};
    }

    public void setValues(Controller ctrl, Model newField)
    {
        assert (null != ctrl && null != newField);
        control = ctrl;
        field = newField;        
    }

    public void paintComponent(Graphics g)
    {
        assert (null != g);
        int widthSize = field.getWidht();
        int heightSize = field.getHeight();
        int widthCell = getWidth() / widthSize;
        int heightCell = getHeight() / heightSize;
        for (int i = 0; i < widthSize; i++)
        {
            for (int j = 0; j < heightSize; j++)
            {
                if (Controller.OPEN == field.getState(i, j))
                {
                    if (Controller.MINE == field.getValue(i, j))
                    {
                        g.drawImage(boom, i * widthCell, j * heightCell, widthCell, heightCell, this);
                    }
                    else
                    {
                        g.drawImage(array[field.getValue(i, j)], i * widthCell, j * heightCell, widthCell, heightCell, this);
                    }
                }
                else
                {
                    if (Controller.LOSE != field.getGameState())
                    {
                        if (Controller.FLAG == field.getState(i, j))
                        {
                            g.drawImage(flag, i * widthCell, j * heightCell, widthCell, heightCell, this);
                        }
                        else if (Controller.QUESTION == field.getState(i, j))
                        {
                            g.drawImage(question, i * widthCell, j * heightCell, widthCell, heightCell, this);
                        }
                        else
                        {
                            g.drawImage(cell, i * widthCell, j * heightCell, widthCell, heightCell, this);
                        }
                    }
                    else
                    {
                        if (Controller.MINE == field.getValue(i, j))
                        {
                            g.drawImage(mine, i * widthCell, j * heightCell, widthCell, heightCell, this);
                        }
                        else
                        {
                            g.drawImage(cell, i * widthCell, j * heightCell, widthCell, heightCell, this);
                        }
                    }
                }
            }
        }
    }

    public void act()
    {
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                int widthSize = field.getWidht();
                int heightSize = field.getHeight();
                int widthCell = getWidth() / widthSize;
                int heightCell = getHeight() / heightSize;
                int x = event.getX() / widthCell;
                int y = event.getY() / heightCell;
                if (flagMouse)
                {
                    if (MouseEvent.BUTTON1 == event.getButton())
                    {
                        if (control.open(x, y))
                        {
                            start();
                        }
                        getSituation();
                    }
                    else if (MouseEvent.BUTTON3 == event.getButton())
                    {
                        control.putFlag(x, y);
                        MineCount.setText(Integer.toString(control.getFlagRemains()));
                    }
                    else if (MouseEvent.BUTTON2 == event.getButton())
                    {
                        control.openAll(x, y);
                        getSituation();
                    }
                    repaint();
                }
            }
        });
    }

    public void setFlag(boolean value)
    {
        flagMouse = value;        
    }
}

