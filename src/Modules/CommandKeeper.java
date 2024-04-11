package Modules;


import Exceptions.ScriptRecursionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CommandKeeper {
    private CollectionService collectionService;
    private static XMLProvider xmlProvider;
    private static LinkedList<String> commandHistory = new LinkedList<>();
    private static Set<Path> scriptsNames = new TreeSet<>();

    public CommandKeeper() {
        this.collectionService = new CollectionService();
        xmlProvider = new XMLProvider(XMLProvider.FILE_PATH);
    }

    public void help(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments");// illegal args exception
        } else {
            System.out.println(
                    """
                    Список доступных команд:
                    ================================================================================================
                    help : вывести справку по доступным командам
                    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                    add {element} : добавить новый элемент в коллекцию
                    update id {element} : обновить значение элемента коллекции, id которого равен заданному
                    remove_by_id id : удалить элемент из коллекции по его id
                    clear : очистить коллекцию
                    save : сохранить коллекцию в файл
                    execute_script file_name : считать и исполнить скрипт из указанного файла. В Wrong command arguments в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                    exit : завершить программу (без сохранения в файл)
                    add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                    remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                    history : вывести последние 13 команд (без их аргументов)
                    min_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является минимальным
                    group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе
                    filter_by_standard_of_living standardOfLiving : вывести элементы, значение поля standardOfLiving которых равно заданному
                    ================================================================================================
                    """
            );
        }
    }

    public void info(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments");
        } else {
            collectionService.info();
        }
    }

    public void show(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            collectionService.show();
        }
    }

    public void add(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            collectionService.addElement();
        }
    }

    public void update(String arguments){  //args required
        if (arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            try {
                long current_id = Long.parseLong(arguments);
                if (current_id > 0){
                    collectionService.update(current_id);
                } else {
                    System.out.println("id");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong args format");
            }
        }
    }

    public void removeById(String arguments){ //args required
        if (arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            try {
                long id = Long.parseLong(arguments);
                if (id > 0){
                    collectionService.removeById(id);
                } else {
                    System.out.println("id не может быть отрицательным");
                }
            } catch (NumberFormatException e){
                System.out.println("Wrong args format");
            }
        }
    }

    public void clear(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            collectionService.clear();
        }
    }

    public void save(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            xmlProvider.save(CollectionService.collection);
        }
    }

    public void executeScript(String path){  //args required
        if (path.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            try {
                Path pathToScript = Paths.get(path);

                UserInputScan.setUserScanner(new Scanner(pathToScript));
                Scanner scriptScanner = UserInputScan.getUserScanner();

                Path scriptFile = pathToScript.getFileName();

                if (!scriptScanner.hasNext()) throw new NoSuchElementException();
                scriptsNames.add(scriptFile);
                do {
                    var command = "";
                    var arguments = "";
                    String[] input = (scriptScanner.nextLine() + " ").trim().split(" ", 2);
                    if (input.length == 2){
                        arguments = input[1].trim();
                    }
                    command = input[0].trim();
                    while (scriptScanner.hasNextLine() && command.isEmpty()){
                        input = (scriptScanner.nextLine() + " ").trim().split(" ", 2);
                        if (input.length == 2){
                            arguments = input[1].trim();
                        }
                        command = input[0].trim();
                    }

                    if (ConsoleApp.commandList.containsKey(command)) {

                        if (command.equalsIgnoreCase("execute_script")) {

                            Path scriptNameFromArgument = Paths.get("").resolve(arguments).getFileName();

                            if (scriptsNames.contains(scriptNameFromArgument)) {
                                throw new ScriptRecursionException("The same script can't be run in recursion way.");
                            }
                            ConsoleApp.commandList.get("execute_script").execute(arguments);

                        }
                        else {
                            ConsoleApp.commandList.get(command).execute(arguments);
                            System.out.println("Command" + command + " executed successfully");
                        }

                    } else {
                        System.out.println("Unknown command.");
                    }

                } while (scriptScanner.hasNextLine());
                scriptsNames.remove(scriptFile);
                UserInputScan.setUserScanner(new Scanner(System.in));
                System.out.println("Script " + scriptFile + " executed successfully");

            } catch (FileNotFoundException e){
                System.out.println("File " + path + " not found");
            } catch (NoSuchElementException e){
                System.out.println("File " + path + " is empty");
            } catch (IllegalStateException e){
                System.out.println("Unexpected error");
            } catch (SecurityException e){
                System.out.println("You are not admin to read this file: " + path);
            } catch (ScriptRecursionException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("I/O error");
            } catch (InvalidPathException e){
                System.out.println("Check the file path!");
            }
        }

    }

    public void exit(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            System.out.println(
                    """
                    Changes won't save if you exit now. Are you sure?
                    y = "Yes"      any key = "No"
                    """);
            Scanner scanner = new Scanner(System.in);
            var answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")){
                System.exit(0);
            }
        }
    }

    public void removeLower(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments");
        } else {
            collectionService.removeLower();
        }
    }

    public void history(String arguments){
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            System.out.println("Last" + commandHistory.size() + " (of 13 kept) commands entered: ");
            for (String command : commandHistory) {
                System.out.println(command);
            }
        }
    }

    public void filterByStandardOfLiving(String arguments){ //args required
        if (arguments.isBlank()){
            System.out.println("Wrong command arguments"); // illegal args exception
        } else {
            collectionService.filterByStandardOfLiving(arguments.toUpperCase());
        }

    }

    public void groupCountingByArea(String arguments) {
        if (!arguments.isBlank()) {
            System.out.println("Wrong command arguments");
        }
        else {
            collectionService.groupCountingByArea();
        }
    }

    public void minByCreationDate(String arguments) {
        if (!arguments.isBlank()){
            System.out.println("Wrong command arguments");
        }
        else {
            collectionService.minByCreationDate();
        }
    }

    public void addIfMin(String arguments) {
        if (!arguments.isBlank()) {
            System.out.println("Wrong command arguments");
        }
        else{
            collectionService.addIfMin();
        }
    }

    public static void addCommand(String command){
        if (commandHistory.size() == 13){
            commandHistory.removeFirst();
        }
        commandHistory.addLast(command);
    }
}