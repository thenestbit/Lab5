package Commands;

import Modules.*;

public class FilterBySOLCommand implements Command{
    private CommandKeeper commandKeeper;

    public FilterBySOLCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("filter_by_standard_of_living", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.filterByStandardOfLiving(arguments);
    }
}
