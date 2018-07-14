package ru.nsu.ccfit.berdov.minesweeper.gui;

import ru.nsu.ccfit.berdov.minesweeper.HighScore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainMenu
{
    private final ImageFrame frame;
    private final HighScore score;

    public MainMenu(ImageFrame imageFrame, HighScore scores)
    {
        assert (null != imageFrame && null != scores);
        score = scores;
        frame = imageFrame;
    }

    public JMenuBar createMainMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenu help = new JMenu("Help");
        menuBar.add(menu);
        menuBar.add(help);

        JMenuItem NewGame = new JMenuItem("New Game");
        NewGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                String[] object = {"New Game", "Same", "Cancel"};
                int choice = JOptionPane.showOptionDialog(null, "You Lose, would you like to restart game?", "YOU LOSE!!!",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,object,null);
                if (0 == choice)
                {
                    frame.generateField("Same");
                }
                else if (1 == choice)
                {
                    frame.generateSameField();
                }
            }
        });
        menu.add(NewGame);
        menu.addSeparator();

        JMenuItem HighScores = new JMenuItem("High Scores");
        HighScores.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                new HighScoreDialog(frame, score);
            }
        });
        menu.add(HighScores);

        JMenu Settings = new JMenu("Settings");
        menu.add(Settings);
        JMenuItem Beginner = new JMenuItem("Beginner");
        JMenuItem Amateur = new JMenuItem("Amateur");
        JMenuItem Professional = new JMenuItem("Professional");
        JMenuItem Special = new JMenuItem("Special");
        Beginner.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Beginner");
            }
        });
        Settings.add(Beginner);
        Amateur.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Amateur");
            }
        });
        Settings.add(Amateur);
        Professional.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Professional");
            }
        });
        Settings.add(Professional);
        Special.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                LevelDialod special = new LevelDialod(frame);
                if (special.getState())
                {
                    frame.generateField(special.getWidthSize(), special.getHeightSize(), special.getMineCount());
                }
            }
        });
        Settings.add(Special);
        menu.addSeparator();

        JMenuItem About = new JMenuItem("About");
        About.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(frame.getFrame(), "Minesweeper is developed by Berdov Valera", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(About);

        JMenuItem Exit = new JMenuItem("Exit");
        Exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        menu.add(Exit);

        return menuBar;
    }
}
