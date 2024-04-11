package Commands;

import Modules.*;
public class UpdateCommand implements Command{
    private CommandKeeper commandKeeper;

    public UpdateCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("update", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.update(arguments);
    }
}