package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.HighScore;
import java.util.ArrayList;

final class CommandHighScore extends Command
{
    private final HighScore score;

    public CommandHighScore(HighScore score)
    {
        this.score = score;
    }

    public boolean execute(String[] args)
    {
        ArrayList<String> table = score.getTable(args[LEVEL]);
        for (String string : table)
        {
            System.out.println(string);
        }
        System.out.println();
        return false;
    }
}