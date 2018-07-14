package ru.nsu.ccfit.berdov.minesweeper;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HighScore
{
    private static final String beginnerTable = "Beginner.txt";
    private static final String amateurTable = "Amateur.txt";
    private static final String professionalTable = "Professional.txt";
    private final ArrayList<String> beginner;
    private final ArrayList<String> amateur;
    private final ArrayList<String> professional;
    public final static int CAPACITY = 5;

    void createFiles()
    {
        PrintWriter outputBeginner = null;
        PrintWriter outputAmateur = null;
        PrintWriter outputProfessional = null;
        try
        {
            outputBeginner = new PrintWriter(beginnerTable);
            outputAmateur = new PrintWriter(amateurTable);
            outputProfessional = new PrintWriter(professionalTable);
            write(outputBeginner, beginner);
            write(outputAmateur, amateur);
            write(outputProfessional, professional);
        }
        catch (IOException exception)
        {
            System.out.println("IOException: " + exception.toString());
        }
        finally
        {
            if (null != outputBeginner)
            {
                outputBeginner.close();
            }
            if (null != outputAmateur)
            {
                outputAmateur.close();
            }
            if (null != outputProfessional)
            {
                outputProfessional.close();
            }
        }
    }

    private static void read(BufferedReader input, ArrayList<String> level)
    {
        assert (null != input && null != level);
        String line;
        try
        {
            for (int i = 0; i < CAPACITY; i++)
            {
                line = input.readLine();
                if (null == line || "\n".equals(line) || line.isEmpty())
                {
                    return;
                }
                level.add(line);
            }
        }
        catch (IOException exception)
        {
            System.out.println("Exception: " + exception.toString());
        }
    }

    private static void write(PrintWriter output, ArrayList<String> level)
    {
        assert (null != output && null != level);
        for (String string : level)
        {
            output.println(string);
        }
    }

    public HighScore()
    {
        beginner = new ArrayList<String>();
        amateur = new ArrayList<String>();
        professional = new ArrayList<String>();
        BufferedReader inputBeginner = null;
        BufferedReader inputAmateur = null;
        BufferedReader inputProfessional = null;
        try
        {
            inputBeginner = new BufferedReader(new FileReader(beginnerTable));
            inputAmateur = new BufferedReader(new FileReader(amateurTable));
            inputProfessional = new BufferedReader(new FileReader(professionalTable));
            read(inputBeginner, beginner);
            read(inputAmateur, amateur);
            read(inputProfessional, professional);
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("FileNotFoundException: " + exception.toString());
        }
        finally
        {
            try
            {
                if (null != inputBeginner)
                {
                    inputBeginner.close();
                }
                if (null != inputAmateur)
                {
                    inputAmateur.close();
                }
                if (null != inputProfessional)
                {
                    inputProfessional.close();
                }
            }
            catch (IOException exception)
            {
                System.out.println("IOException: " + exception.toString());
            }
        }
    }

    public void remove()
    {
        beginner.clear();
        amateur.clear();
        professional.clear();
        createFiles();
    }

    public int setRecord(int time, String name, String level)
    {
        assert (null != name && null != level && 0 <= time);
        if ("".equals(name))
        {
            return 0;
        }
        ArrayList<String> table = getTable(level);
        StringTokenizer tokenizer;
        int result;
        try
        {
            for (String string : table)
            {
                tokenizer = new StringTokenizer(string);
                result = Integer.parseInt(tokenizer.nextToken());
                if (result >= time)
                {
                    int position = table.lastIndexOf(string);
                    table.add(position, time + " " + name);
                    createFiles();
                    return ++position;
                }
            }
            if (table.size() < CAPACITY)
            {
                table.add(time + " " + name);
                createFiles();
                return table.size();
            }
        }
        catch (IndexOutOfBoundsException exception)
        {
            System.out.println("IndexOutOfBoundsException: " + exception.toString());
        }
        return 0;
    }

    public ArrayList<String> getTable(String level)
    {
        assert (null != level);
        if ("Beginner".equals(level))
        {
            return beginner;
        }
        if ("Amateur".equals(level))
        {
            return amateur;
        }
        if ("Professional".equals(level))
        {
            return professional;
        }
        return null;
    }
}