package ru.nsu.ccfit.berdov.minesweeper.gui;

import ru.nsu.ccfit.berdov.minesweeper.Controller;
import ru.nsu.ccfit.berdov.minesweeper.HighScore;
import ru.nsu.ccfit.berdov.minesweeper.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageFrame
{
    private final JFrame frame;
    private Model field;
    private final Controller control;
    private final HighScore scores;

    private final GameField GameField;
    private final JLabel mineCounter;
    private final JLabel timeCounter;
    private final Timer timer;
    private int second;

    private static final int MINWIDTH = 207;
    private static final int MINHEIGHT = 282;

    ImageIcon createImageIcon(String path)
    {
        assert (null != path);
        java.net.URL imgURL = ImageFrame.class.getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    void giveSizeLocation()
    {
        final int SIZE_CELL = 23;
        frame.setTitle("Minesweeper");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int widthSize = field.getWidht() * SIZE_CELL;
        int heightSize = field.getHeight() * SIZE_CELL + 75;
        frame.setSize(widthSize, heightSize);
        frame.setLocation((screenWidth - widthSize) / 2, (screenHeight - heightSize) / 2);
    }

    private boolean isLevelStandart()
    {
        return "Beginner".equals(control.getLevel()) || "Amateur".equals(control.getLevel()) || "Professional".equals(control.getLevel());
    }

    public ImageFrame(Model gameField, Controller controller)
    {
        assert (null != gameField && null != controller);
        field = gameField;        
        control = controller;
        scores = new HighScore();
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(MINWIDTH, MINHEIGHT));
        giveSizeLocation();
        Image icon = createImage("/ru/nsu/ccfit/berdov/minesweeper/resources/icon.png");
        frame.setIconImage(icon);
        MainMenu menu = new MainMenu(this, scores);
        frame.setJMenuBar(menu.createMainMenu());
        GridBagLayout gbl = new GridBagLayout();
        frame.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.0;
        JLabel time = new JLabel("TIME:");
        gbl.setConstraints(time, gbc);
        frame.add(time);

        gbc.gridx = 3;
        JLabel mine = new JLabel("MINES:");
        gbl.setConstraints(mine, gbc);
        frame.add(mine);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(1, 0, 0, 0);
        gbc.gridx = 2;
        ImageIcon face = createImageIcon("/ru/nsu/ccfit/berdov/minesweeper/resources/roja.png");
        ImageIcon pressFace = createImageIcon("/ru/nsu/ccfit/berdov/minesweeper/resources/pressFace.png");
        JButton buttonFace = new JButton(face);
        buttonFace.setPressedIcon(pressFace);
        buttonFace.setPreferredSize(new Dimension(26, 26));
        buttonFace.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                generateField("Same");
            }
        });
        gbl.setConstraints(buttonFace, gbc);
        frame.add(buttonFace);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.gridx = 1;
        timeCounter = new JLabel("0");
        timeCounter.setPreferredSize(new Dimension(25, 16));
        gbl.setConstraints(timeCounter, gbc);
        frame.add(timeCounter);

        gbc.gridx = 4;
        mineCounter = new JLabel(Integer.toString(control.getFlagRemains()));
        mineCounter.setPreferredSize(new Dimension(25, 16));
        gbl.setConstraints(mineCounter, gbc);
        frame.add(mineCounter);

        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 0, 0, 0);
        GameField = new GameField(this, control, field, mineCounter);
        gbl.setConstraints(GameField, gbc);
        GameField.act();
        frame.add(GameField);

        timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                timeCounter.setText(Integer.toString(++second));
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Image createImage(String path)
    {
        assert (null != path);
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null)
        {
            return (new ImageIcon(imgURL).getImage());
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void generateField(int widthSize, int heightSize, int mines)
    {
        timer.stop();
        second = 0;
        field = control.generateField(widthSize, heightSize, mines);
        timeCounter.setText("0");
        mineCounter.setText(Integer.toString(mines));
        giveSizeLocation();
        GameField.setValues(control, field);
        GameField.setFlag(true);
        frame.repaint();
    }

    public void generateField(String level)
    {
        assert (null != level);
        timer.stop();
        second = 0;
        field = control.generateField(level);
        timeCounter.setText("0");
        mineCounter.setText(Integer.toString(control.getFlagRemains()));
        if (!"Same".equals(level))
        {
            giveSizeLocation();
        }
        GameField.setValues(control, field);
        GameField.setFlag(true);
        frame.repaint();
    }

    public void generateSameField()
    {
        timer.stop();
        second = 0;
        field = control.generateSameField();
        timeCounter.setText("0");
        mineCounter.setText(Integer.toString(control.getFlagRemains()));
        GameField.setValues(control, field);
        GameField.setFlag(true);
        frame.repaint();
    }

    public void win()
    {
        timer.stop();
        if (isLevelStandart())
        {
            String name = JOptionPane.showInputDialog(frame, "Enter your name, please", "Enter your name", JOptionPane.INFORMATION_MESSAGE);
            if (null == name)
            {
                generateField("Same");
                return;
            }
            if (1 == scores.setRecord(second, name, control.getLevel()))
            {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, "You set a NEW RECORD\nWould you like to restart game?", "YOU WIN, CONGRATULATION!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                {
                    generateField("Same");
                }
            }
            else
            {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, "You WIN\n would you like to restart game?", "YOU WIN, CONGRATULATION!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                {
                    generateField("Same");
                }
            }
        }
        else
        {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, "You win, would you like to restart game?", "YOU WIN, CONGRATULATION!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
            {
                generateField("Same");
            }
        }
    }

    public void lose()
    {
        timer.stop();
        String[] object = {"New Game", "Same", "Cancel"};
        int choice = JOptionPane.showOptionDialog(frame, "You Lose, would you like to restart game?", "YOU LOSE!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, object, null);
        if (0 == choice)
        {
            generateField("Same");
        }
        else if (1 == choice)
        {
            generateSameField();
        }
        else if (2 == choice)
        {
            GameField.setFlag(false);
        }
    }

    public Timer getTimer()
    {
        return timer;
    }

    public JFrame getFrame()
    {
        return frame;
    }
}
