package Commands;

import Modules.*;

public class ExitCommand implements Command {
    private CommandKeeper commandKeeper;

    public ExitCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("exit", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.exit(arguments);
    }
}