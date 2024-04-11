package Commands;

import Modules.CommandKeeper;
import Modules.ConsoleApp;

public class SaveCommand implements Command{
    private CommandKeeper commandKeeper;

    public SaveCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("save", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.save(arguments);
    }
}