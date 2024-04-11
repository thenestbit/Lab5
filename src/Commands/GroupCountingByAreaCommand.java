package Commands;

import Modules.*;

public class GroupCountingByAreaCommand implements Command{
    private CommandKeeper commandKeeper;

    public GroupCountingByAreaCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("group_counting_by_area", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.groupCountingByArea(arguments);
    }
}