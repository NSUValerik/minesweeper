package ru.nsu.ccfit.berdov.minesweeper.fabric;

final class CommandAbout extends Command
{
    public boolean execute(String[] args)
    {
        System.out.println("Minesweeper is developed by Berdov Valera");
        return false;
    }
}