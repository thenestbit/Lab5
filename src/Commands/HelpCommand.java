package Commands;

import Modules.*;

public class HelpCommand implements Command {
    CommandKeeper commandKeeper;

    public HelpCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("help", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.help(arguments);
    }
}