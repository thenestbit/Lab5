package Commands;

import Modules.*;

public class RemoveLowerCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveLowerCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_lower", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.removeLower(arguments);
    }
}