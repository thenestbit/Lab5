package Commands;

import Modules.*;

public class AddCommand implements Command {
    private CommandKeeper commandKeeper;

    public AddCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("add", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.add(arguments);
    }
}