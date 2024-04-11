package Commands;

import Modules.*;

public class ExecuteScriptCommand implements Command{
    private CommandKeeper commandKeeper;

    public ExecuteScriptCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("execute_script", this);
    }

    @Override
    public void execute(String arguments) {
        commandKeeper.executeScript(arguments);
    }
}