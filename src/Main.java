import Commands.*;
import Modules.CommandKeeper;
import Modules.ConsoleApp;
import Modules.UserInputScan;
import Modules.XMLProvider;

import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleApp consoleApp = createConsoleApp();
        try {
            var pathToCollection = System.getenv("PATH_TO_XML");//"collection.xml"
            if (pathToCollection.isBlank()){
                throw new NoSuchElementException();
            }
            XMLProvider xmlProvider = new XMLProvider(pathToCollection);
            xmlProvider.load();
            System.out.println("File read successfully!");
            System.out.println("Path to file: " + pathToCollection);
        } catch (NullPointerException e){
            System.out.println("No system environment variable named \"PATH_TO_XML\" found. Bye!");
            System.exit(1);
        } catch (NoSuchElementException e){
            System.out.println("Environment variable is blank. Bye!");
            System.exit(1);
        }


        UserInputScan.setUserScanner(new Scanner(System.in));
        var scanner = UserInputScan.getUserScanner();

        System.out.println("===================================");
        consoleApp.help("");
        System.out.print("> ");

        while (true) {
            try {
                while (scanner.hasNext()) {
                    var command = "";
                    var arguments = "";
                    String[] input = (scanner.nextLine() + " ").trim().split(" ", 2);

                    if (input.length == 2) {
                        arguments = input[1].trim();
                    }
                    command = input[0].trim();

                    if (ConsoleApp.commandList.containsKey(command)) {
                        ConsoleApp.commandList.get(command).execute(arguments);
                        CommandKeeper.addCommand(command);
                    }
                    else if (command == ""){
                        continue;
                    }
                    else {
                        System.out.println("Unknown command. Keep trying...");
                    }
                    System.out.print("> ");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Exit...");
                System.out.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                System.exit(1);
            }

        }
    }

    private static ConsoleApp createConsoleApp() {
        CommandKeeper commandKeeper = new CommandKeeper();
        return new ConsoleApp(
                new HelpCommand(commandKeeper),
                new InfoCommand(commandKeeper),
                new ShowCommand(commandKeeper),
                new AddCommand(commandKeeper),
                new UpdateCommand(commandKeeper),
                new RemoveByIdCommand(commandKeeper),
                new ClearCommand(commandKeeper),
                new SaveCommand(commandKeeper),
                new ExecuteScriptCommand(commandKeeper),
                new ExitCommand(commandKeeper),
                new AddIfMinCommand(commandKeeper),
                new RemoveLowerCommand(commandKeeper),
                new HistoryCommand(commandKeeper),
                new MinByCreationDateCommand(commandKeeper),
                new GroupCountingByAreaCommand(commandKeeper),
                new FilterBySOLCommand(commandKeeper)
        );
    }
}