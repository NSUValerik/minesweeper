package ru.nsu.ccfit.berdov.minesweeper.gui;

import ru.nsu.ccfit.berdov.minesweeper.HighScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

class HighScoreDialog
{
    private final HighScore score;
    private final JLabel[] table;
    private final JDialog dialog;

    private void setLabel(String level)
    {
        assert (null != level);
        ArrayList<String> results = score.getTable(level);
        StringTokenizer tokenizer;
        String tmp1, tmp2;
        int i = 0;
        try
        {
            for (String string : results)
            {
                tokenizer = new StringTokenizer(string);
                tmp1 = tokenizer.nextToken();
                tmp2 = tokenizer.nextToken();
                table[i].setText(tmp2 + ": " + tmp1);
                i++;
            }
        }
        catch (NoSuchElementException exception)
        {
            System.out.println("NoSuchElementException: " + exception.toString());
        }
        while (HighScore.CAPACITY > i)
        {
            table[i].setText("Anonym 999");
            i++;
        }
    }

    public HighScoreDialog(ImageFrame frame, final HighScore scores)
    {
        assert (null != frame && null != scores);
        score = scores;
        dialog = new JDialog(frame.getFrame(), "High Scores", true);
        dialog.setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        dialog.setLocation(screenWidth / 2, screenHeight / 2);
        GridBagLayout gbl = new GridBagLayout();
        dialog.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 10, 0);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        String[] variants = {"Beginner", "Amateur", "Professional"};
        final JComboBox list = new JComboBox(variants);
        list.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setLabel((String) list.getSelectedItem());
            }
        });
        gbl.setConstraints(list, gbc);
        dialog.add(list);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 1;
        JLabel name = new JLabel("Name");
        gbl.setConstraints(name, gbc);
        dialog.add(name);

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        JLabel time = new JLabel("Time");
        gbl.setConstraints(time, gbc);
        dialog.add(time);

        gbc.anchor = GridBagConstraints.CENTER;
        table = new JLabel[HighScore.CAPACITY];
        for (int i = 0; i < HighScore.CAPACITY; i++)
        {
            table[i] = new JLabel("0");
            gbc.gridy += 1;
            gbl.setConstraints(table[i], gbc);
            dialog.add(table[i]);
        }
        setLabel(variants[0]);

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 7;
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                dialog.setVisible(false);
            }
        });
        gbl.setConstraints(ok, gbc);
        dialog.add(ok);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 2;
        JButton reset = new JButton("reset");
        reset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                score.remove();
                setLabel((String) list.getSelectedItem());
            }
        });
        gbl.setConstraints(reset, gbc);
        dialog.add(reset);

        dialog.pack();
        dialog.setVisible(true);
    }
}