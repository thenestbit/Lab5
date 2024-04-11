package Commands;

import Modules.*;

public class InfoCommand implements Command{
    private CommandKeeper commandKeeper;

    public InfoCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("info", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.info(arguments);
    }
}