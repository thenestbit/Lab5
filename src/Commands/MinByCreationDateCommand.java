package Commands;

import Modules.*;

public class MinByCreationDateCommand implements Command{
    private CommandKeeper commandKeeper;

    public MinByCreationDateCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("min_by_creation_date", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.minByCreationDate(arguments);
    }
}