package Commands;

import Modules.*;

public class ClearCommand implements Command{
    private CommandKeeper commandKeeper;

    public ClearCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("clear", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.clear(arguments);
    }
}