package Commands;

import Modules.*;

public class ShowCommand implements Command{
    private CommandKeeper commandKeeper;

    public ShowCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("show", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.show(arguments);
    }
}