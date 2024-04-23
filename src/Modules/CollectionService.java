package Modules;

import CollectionObject.*;
import Exceptions.*;
import java.util.*;
import java.util.stream.Collectors;

import static CollectionObject.StandardOfLiving.*;

public class CollectionService {
//    protected static Long elementsCount = 0L;
    private Date initializationDate;
    protected static ArrayDeque<City> collection;

    protected static Scanner InputScanner;

    public CollectionService() {
        collection = new ArrayDeque<>();
        this.initializationDate = new Date();
    }

    protected record CityWithoutId (
            String name, Coordinates coordinates, Date creationDate,
            int area, Integer population, Float metersAboveSeaLevel,
            Long telephoneCode, long agglomeration,
            StandardOfLiving standardOfLiving, Human governor){}

    public void addElement(){
        CityWithoutId source = createElement();
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
        City newElement = new City(
                newId,
                source.name,
                source.coordinates,
                source.creationDate,
                source.area,
                source.population,
                source.metersAboveSeaLevel,
                source.telephoneCode,
                source.agglomeration,
                source.standardOfLiving,
                source.governor
        );

        collection.addLast(newElement);
        System.out.println("Element added successfully");
    }

    public void info(){
        System.out.println("Collection type: " + collection.getClass());
        System.out.println("Initialization date: " + initializationDate);
        System.out.println("Number of elements: " + collection.size());
    }

    public void show(){
        if (collection.isEmpty()){
            System.out.println("There is no elements in collection yet");
        } else{
            for (City city: collection) {
                System.out.println(city + "\n");
            }
        }
    }

    public void update(long current_id){
        if (!collection.contains(getElementById(collection, (int) current_id))){
            System.out.println("Element doesn't exist");
        }
        for (City city:collection) {
            if (current_id == city.getId()){
                collection.remove(city);

                CityWithoutId source = createElement();
                City newElement = new City(
                        current_id,
                        source.name,
                        source.coordinates,
                        source.creationDate,
                        source.area,
                        source.population,
                        source.metersAboveSeaLevel,
                        source.telephoneCode,
                        source.agglomeration,
                        source.standardOfLiving,
                        source.governor
                );

                collection.addLast(newElement);
                System.out.println("Element with id " + current_id + " updated successfully");
                break;

            }
        }
    }

    public void removeById(long id){
        if (!collection.contains(getElementById(collection, (int) id))){
            System.out.println("Element doesn't exist");
        }
        for (City city:collection) {
            if (id == city.getId()){
                collection.remove(city);
                System.out.println("Element with id " + id + " deleted successfully");
                break;
            }
        }
    }

    public void clear(){
        collection.clear();
        System.out.println("Collection is clear now");
    }

    public void removeLower(){
        if (!collection.isEmpty()) {
            var counter = 0;

            CityWithoutId source = createElement();

            UUID uuid = UUID.randomUUID();
            long mostSignificantBits = uuid.getMostSignificantBits();
            long leastSignificantBits = uuid.getLeastSignificantBits();
            long nnId = Math.abs(mostSignificantBits ^ leastSignificantBits);
            City newElement = new City(
                    nnId,
                    source.name,
                    source.coordinates,
                    source.creationDate,
                    source.area,
                    source.population,
                    source.metersAboveSeaLevel,
                    source.telephoneCode,
                    source.agglomeration,
                    source.standardOfLiving,
                    source.governor
            );

            for (City city : collection) {
                if (newElement.getPopulation() > city.getPopulation()) {
                    collection.remove(city);
                    System.out.println("City with id " + city.getId() + "removed successfully");
                    counter++;
                }
            }

            if (counter > 0) {
                System.out.println("Process finished, " + counter + " cities removed");
            } else {
                System.out.println("Sorry, there is nothing lower in collection.");
            }
        } else{
            System.out.println("Collection is empty, nothing to remove");
        }
    }

    public void filterByStandardOfLiving(String standard){
        boolean hasItem = false;
        try {
            StandardOfLiving s = StandardOfLiving.valueOf(standard);
            for (City el : collection) {
                if (el.getStandardOfLiving().equals(s)) {
                    hasItem = true;
                    break;
                }
            }
            if (hasItem) {
                for (City city : collection) {
                    if (city.getStandardOfLiving().equals(s)) {
                        System.out.println(city.toString() + "\n");
                    }
                }
            } else {
                System.out.println("Cities with such standard of living don't exist");
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid input for standard of living. Please enter a valid standard of living option (HIGH, LOW, NIGHTMARE).");
        }
    }

    public void groupCountingByArea() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty. No elements to group.");
            return;
        }

        Map<Integer, Long> areaCountMap = collection.stream()
                .collect(Collectors.groupingBy(City::getArea, Collectors.counting()));

        areaCountMap.forEach((area, count) -> System.out.println("Area: " + area + " | Count: " + count));
    }

    public void addIfMin() {
        CityWithoutId source = createElement();

        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
        City newCity = new City(
                newId,
                source.name,
                source.coordinates,
                source.creationDate,
                source.area,
                source.population,
                source.metersAboveSeaLevel,
                source.telephoneCode,
                source.agglomeration,
                source.standardOfLiving,
                source.governor
        );

        if (collection.isEmpty() || newCity.getPopulation() < Collections.min(collection, Comparator.comparing(City::getPopulation)).getPopulation()) {
            collection.add(newCity);
            System.out.println("City added successfully.");
        } else {
            System.out.println("City was not added as its population is not minimal.");
        }
    }

    public void minByCreationDate() {
        if (collection.isEmpty()) {
            System.out.println("The collection is empty. No objects to display.");
            return;
        }

        City minByCreationDateCity = Collections.min(collection, Comparator.comparing(City::getCreationDate));

        System.out.println("Object with the minimum creation date:" + "\n" + minByCreationDateCity.toString());
    }

    private String askString(Scanner InputScanner) {
        while(true) {
            try {
                var name = InputScanner.nextLine();
                if (name.isBlank()){
                    throw new EmptyFieldException("Field can't be empty. Re-enter it, please: ");
                }
                return name.trim();
            }catch(EmptyFieldException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private float askX(Scanner InputScanner) {
        while(true) {
            try {
                return Float.parseFloat(InputScanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            }
        }
    }

    private double askY(Scanner InputScanner) {
        while(true) {
            try {
                return Double.parseDouble(InputScanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            }
        }
    }

    private float askFloat(Scanner InputScanner) {
        while(true) {
            try {
                float num = Float.parseFloat(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private StandardOfLiving askStandardOfLiving(Scanner InputScanner) {
        while (true){
            try {
                String type = InputScanner.nextLine().toUpperCase();
                StandardOfLiving standard;
                switch (type){
                    case "HIGH":
                        standard = HIGH;
                        break;
                    case "LOW":
                        standard = LOW;
                        break;
                    case "NIGHTMARE":
                        standard = NIGHTMARE;
                        break;
                    default:
                        throw new EmptyFieldException("No such standard of living. " +
                                "Re-enter it correctly (HIGH, LOW, NIGHTMARE): ");
                }
                return standard;
            } catch(NullPointerException e){
                System.out.println("Null standard of living found.");
            } catch (EmptyFieldException e){
                System.out.println("Wrong format of standard of living.");
            }
        }
    }

    private int askInt(Scanner inputScanner) {
        while(true) {
            try {
                int num = Integer.parseInt(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private long askLong(Scanner InputScanner) {
        while(true) {
            try {
                long num = Long.parseLong(InputScanner.nextLine());
                if (num > 0){
                    return num;
                } else {
                    throw new NegativeFieldException("Wrong number format (it can't be less than 0). Re-enter it please: ");
                }

            } catch (NumberFormatException e){
                System.out.println("Wrong number format. Re-enter it please: ");
            } catch (NegativeFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public String askGovernorName(Scanner InputScanner){
        while(true) {
                var name = InputScanner.nextLine();
                if (!name.isBlank()){
                    return name.trim();
                }
                else {
                    return "null";
                }
        }
    }

    private CityWithoutId createElement(){
        InputScanner = UserInputScan.getUserScanner();

        System.out.println("Enter name: ");
        String name = askString(InputScanner);

        System.out.println("Enter x coordinate: ");
        float x = askX(InputScanner);

        System.out.println("Enter y coordinate: ");
        double y = askY(InputScanner);

        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Enter area: ");
        int area = askInt(InputScanner);

        System.out.println("Enter population: ");
        int population = askInt(InputScanner);

        System.out.println("Enter meters above the sea level: ");
        Float metersAboveSeaLevel = askFloat(InputScanner);

        System.out.println("Enter phone code: ");
        Long telephoneCode = askLong(InputScanner);

        System.out.println("Enter agglomeration: ");
        long agglomeration = askLong(InputScanner);

        System.out.print("""
                Enter ONE of available standards of living:
                HIGH
                LOW
                NIGHTMARE
                """);
        StandardOfLiving standard = askStandardOfLiving(InputScanner);

        Date creationDate = new Date();

        System.out.println("Enter the governor's name (if you want it to be null -- enter \"null\" in any case: ");
        Human governor = new Human(askGovernorName(InputScanner));
        if (governor.getName().equalsIgnoreCase("null")){
            governor = null;
        }

        return new CityWithoutId(name, coordinates, creationDate, area, population, metersAboveSeaLevel, telephoneCode, agglomeration, standard, governor);
    }

    public City getElementById(ArrayDeque collection, int id) {
        List<City> c = collection.stream().toList();
        for (City city : c) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

}