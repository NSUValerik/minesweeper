package ru.nsu.ccfit.berdov.minesweeper.fabric;

import ru.nsu.ccfit.berdov.minesweeper.Controller;
import ru.nsu.ccfit.berdov.minesweeper.HighScore;

import java.util.HashMap;
import java.util.Map;

public class Chooser
{
    private final Map<String, Command> choose;

    public Chooser(Controller ctrl, HighScore scores)
    {
        choose = new HashMap<String, Command>();
        choose.put("open", new CommandOpen(ctrl));
        choose.put("open_all", new CommandOpenAll(ctrl));
        choose.put("flag", new CommandFlag(ctrl));
        choose.put("new_game", new CommandNewGame(ctrl));
        choose.put("new_level", new CommandNewLevel(ctrl));
        choose.put("end_game", new CommandEndGame());
        choose.put("high_score", new CommandHighScore(scores));
        choose.put("about", new CommandAbout());
    }

    public Command getCommand(String name)
    {
        return choose.get(name);
    }
}
