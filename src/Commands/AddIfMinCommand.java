package Commands;

import Modules.*;

public class AddIfMinCommand implements Command{
    private CommandKeeper commandKeeper;

    public AddIfMinCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("add_if_min", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.addIfMin(arguments);
    }
}