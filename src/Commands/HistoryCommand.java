package Commands;

import Modules.*;

public class HistoryCommand implements Command{
    private CommandKeeper commandKeeper;

    public HistoryCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("history", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.history(arguments);
    }
}