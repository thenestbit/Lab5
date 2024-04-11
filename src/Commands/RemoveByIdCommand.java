package Commands;

import Modules.*;

public class RemoveByIdCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveByIdCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_by_id", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.removeById(arguments);
    }
}