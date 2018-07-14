package ru.nsu.ccfit.berdov.minesweeper.gui;

import ru.nsu.ccfit.berdov.minesweeper.Controller;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class LevelDialod
{
    private final ImageFrame frame;
    private int widthSize;
    private int heightSize;
    private int mineCount;
    private boolean state = true;
    private final JDialog dialog;

    public LevelDialod(ImageFrame imageFrame)
    {
        assert (null != imageFrame);
        frame = imageFrame;
        dialog = new JDialog(frame.getFrame(),"Special Settings",true);
        dialog.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        dialog.setLocation(screenWidth / 2, screenHeight / 2);
        GridBagLayout gbl = new GridBagLayout();
        dialog.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.0;

        JButton beginner = new JButton("Beginner");
        beginner.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Beginner");
                state = false;
                dialog.setVisible(false);
            }
        });
        gbl.setConstraints(beginner, gbc);
        dialog.add(beginner);

        gbc.gridy = 1;
        JButton amateur = new JButton("Amateur");
        amateur.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Amateur");
                state = false;
                dialog.setVisible(false);
            }
        });
        gbl.setConstraints(amateur, gbc);
        dialog.add(amateur);

        gbc.gridy = 2;
        JButton professional = new JButton("Professional");
        professional.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.generateField("Professional");
                state = false;
                dialog.setVisible(false);
            }
        });
        gbl.setConstraints(professional, gbc);
        dialog.add(professional);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(10, 0, 0, 5);
        gbc.gridy = 3;
        gbc.gridx = 1;
        JButton ok = new JButton("OK");
        gbl.setConstraints(ok, gbc);
        dialog.add(ok);

        gbc.gridx = 2;
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                dialog.setVisible(false);
                state = false;
            }
        });
        gbl.setConstraints(cancel, gbc);
        dialog.add(cancel);

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 0;
        final JTextField width = new JTextField("Enter your parameter", 11);
        width.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                width.setText("");
            }
        });
        gbl.setConstraints(width, gbc);
        dialog.add(width);

        gbc.gridx = 2;
        gbc.gridy = 1;
        final JTextField height = new JTextField("Enter your parameter", 11);
        height.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                height.setText("");
            }
        });
        gbl.setConstraints(height, gbc);
        dialog.add(height);

        gbc.gridx = 2;
        gbc.gridy = 2;
        final JTextField mines = new JTextField("Enter your parameter", 11);
        mines.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent event)
            {
                mines.setText("");
            }
        });
        gbl.setConstraints(mines, gbc);
        dialog.add(mines);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JLabel labelWidth = new JLabel("Width:");
        gbl.setConstraints(labelWidth, gbc);
        dialog.add(labelWidth);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel labelHeight = new JLabel("Height:");
        gbl.setConstraints(labelHeight, gbc);
        dialog.add(labelHeight);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel labelMines = new JLabel("Mines:");
        gbl.setConstraints(labelMines, gbc);
        dialog.add(labelMines);

        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                int MAX_MINES = Integer.MAX_VALUE;
                try
                {
                    widthSize = Integer.parseInt(width.getText());
                    heightSize = Integer.parseInt(height.getText());
                    mineCount = Integer.parseInt(mines.getText());
                    MAX_MINES = (int) (widthSize * heightSize * Controller.FACTOR);
                }
                catch (NumberFormatException exception)
                {
                    System.out.println("NumberFormatException " + exception.toString());
                }
                if (Controller.MAX_HEIGHT < heightSize || Controller.MAX_WIDHT < widthSize || Controller.MIN_SIZE > widthSize || Controller.MIN_SIZE > heightSize)
                {
                    JOptionPane.showMessageDialog(frame.getFrame(), "You enter wrong parameters of game\nWidth - [9,30]\nHeight - [9,24]\n", "Wrong Parameter", JOptionPane.WARNING_MESSAGE);
                }
                else if (Controller.MIN_MINES > mineCount || MAX_MINES < mineCount)
                {
                    JOptionPane.showMessageDialog(frame.getFrame(), "You enter, wrong count of mines for game with your parameters Width and Height\nMines - [10," + MAX_MINES + "]", "Wrong Parameter", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    dialog.setVisible(false);
                }
            }
        });

        dialog.pack();
        dialog.setVisible(true);
    }

    public int getWidthSize()
    {
        return widthSize;
    }

    public int getHeightSize()
    {
        return heightSize;
    }

    public int getMineCount()
    {
        return mineCount;
    }

    public boolean getState()
    {
        return state;
    }
}
