package ru.nsu.ccfit.berdov.minesweeper.fabric;

final class CommandEndGame extends Command
{
    public boolean execute(String[] args)
    {
        System.exit(0);
        return false;
    }
}
