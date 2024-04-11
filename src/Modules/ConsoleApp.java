package Modules;

import Commands.Command;

import java.util.HashMap;

public class ConsoleApp {
    // command hashmap. K - command name; V - command class
    public static HashMap<String, Command> commandList = new HashMap<>();
    private Command help;
    private Command info;
    private Command show;
    private Command add;
    private Command update;
    private Command removeById;
    private Command clear;
    private Command save;
    private Command executeScript;
    private Command exit;
    private Command addIfMin;
    private Command removeLower;
    private Command history;
    private Command minByCreationDate;
    private Command groupCountingByArea;
    private Command filterByStandardOfLiving;

    public ConsoleApp(Command... commands) {
        this.help = commands[0];
        this.info = commands[1];
        this.show = commands[2];
        this.add = commands[3];
        this.update = commands[4];
        this.removeById = commands[5];
        this.clear = commands[6];
        this.save = commands[7];
        this.executeScript = commands[8];
        this.exit = commands[9];
        this.addIfMin = commands[10];
        this.removeLower = commands[11];
        this.history = commands[12];
        this.minByCreationDate = commands[13];
        this.groupCountingByArea = commands[14];
        this.filterByStandardOfLiving = commands[15];
    }

    public void help(String arguments){
        help.execute(arguments);
    }

    public void info(String arguments){
        info.execute(arguments);
    }

    public void show(String arguments){
        show.execute(arguments);
    }

    public void add(String arguments){
        add.execute(arguments);
    }

    public void update(String arguments){
        update.execute(arguments);
    }

    public void removeById(String arguments){
        removeById.execute(arguments);
    }

    public void clear(String arguments){
        clear.execute(arguments);
    }

    public void save(String arguments){
        save.execute(arguments);
    }

    public void executeScript(String arguments){
        executeScript.execute(arguments);
    }

    public void addIfMin(String arguments){
        addIfMin.execute(arguments);
    }

    public void removeLower(String arguments){
        removeLower.execute(arguments);
    }

    public void history(String arguments){
        history.execute(arguments);
    }

    public void minByCreationDate(String arguments){
        minByCreationDate.execute(arguments);
    }

    public void groupCountingByArea(String arguments){
        groupCountingByArea.execute(arguments);
    }

    public void filterByStandardOfLiving(String arguments){
        filterByStandardOfLiving.execute(arguments);
    }

    public void exit(String arguments){
        exit.execute(arguments);
    }
}
