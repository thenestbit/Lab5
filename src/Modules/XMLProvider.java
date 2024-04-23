package Modules;

import CollectionObject.City;
import CollectionObject.Coordinates;
import CollectionObject.Human;
import CollectionObject.StandardOfLiving;


import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class XMLProvider implements DataProvider {

    protected static String FILE_PATH;
    private ArrayDeque<City> arrayDeque = CollectionService.collection;

    public XMLProvider(String filePath) {
        this.FILE_PATH = filePath;
    }

    @Override
    public void save(ArrayDeque<City> collection) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE_PATH))) {
            writer.write("<cities>\n");

            for (City city : collection) {
                writer.write("\t<city>\n");
                writer.write("\t\t<id>" + city.getId() + "</id>\n");
                writer.write("\t\t<name>" + city.getName() + "</name>\n");
                writer.write("\t\t<coordinates>" + city.getCoordinates() + "</coordinates>\n");
                writer.write("\t\t<creationDate>" + city.getCreationDate() + "</creationDate>\n");
                writer.write("\t\t<area>" + city.getArea() + "</area>\n");
                writer.write("\t\t<population>" + city.getPopulation() + "</population>\n");
                writer.write("\t\t<metersAboveSeaLevel>" + city.getMetersAboveSeaLevel() + "</metersAboveSeaLevel>\n");
                writer.write("\t\t<telephoneCode>" + city.getTelephoneCode() + "</telephoneCode>\n");
                writer.write("\t\t<agglomeration>" + city.getAgglomeration() + "</agglomeration>\n");
                writer.write("\t\t<standardOfLiving>" + city.getStandardOfLiving() + "</standardOfLiving>\n");
                if (city.getGovernor() == null) {
                    writer.write("\t\t<governor>" + "null" + "</governor>\n");
                } else{
                    writer.write("\t\t<governor>" + city.getGovernor().getName() + "</governor>\n");
                }
                writer.write("\t</city>\n");
            }

            writer.write("</cities>");
            System.out.println("Cities were saved successfuly");

        } catch (FileNotFoundException e){
            System.out.println("Error occured while saving collection: " + e.getMessage());
            System.exit(1);
        }
    }


    @Override
    public void load() {
        try (Scanner scanner = new Scanner(Path.of(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("<city>")) {
                    City city = new City((long) 0L,"",new Coordinates(0.0F,0.0),new Date(),0,0, (float) 0.0F, 0L, 0,StandardOfLiving.LOW, new Human("Unknown")); // Create a new City object
                    while (!(line = scanner.nextLine()).contains("</city>")) {
                        if (line.contains("<id>")) {
                            try {
                                city.setId(Long.parseLong(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong ID found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<name>")) {
                            try {
                                city.setName(line.replaceAll("<name>(.*)</name>", "$1").trim());
                            } catch (IllegalArgumentException e){
                                System.out.println("Wrong name found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<coordinates>")) {
                            // Assuming coordinates are stored in a specific format, parse them accordingly
                            city.setCoordinates(parseCoordinates(line));
                        } else if (line.contains("<creationDate>")) {
                            // Parse creation date (assuming it's stored in a specific format)
                            city.setCreationDate(parseCreationDate(line));
                        } else if (line.contains("<area>")) {
                            try {
                                city.setArea(Integer.parseInt(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong area found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<population>")) {
                            try {
                                city.setPopulation(Integer.parseInt(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong population found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<metersAboveSeaLevel>")) {
                            try {
                                city.setMetersAboveSeaLevel(Float.parseFloat(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong metersAboveSeaLevel found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<telephoneCode>")) {
                            try {
                                city.setTelephoneCode(Long.parseLong(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong telephoneCode found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<agglomeration>")) {
                            try {
                                city.setAgglomeration(Long.parseLong(line.replaceAll("\\D+", "")));
                            } catch (NumberFormatException e){
                                System.out.println("Wrong agglomeration found while parsing data. Default value set.");
                            }
                        } else if (line.contains("<standardOfLiving>")) {
                            // Parse standard of living (assuming it's stored in a specific format)
                            city.setStandardOfLiving(parseStandardOfLiving(line));
                        } else if (line.contains("<governor>")) {
                            city.setGovernor(parseGovernor(line));
                        }
                    }
                    arrayDeque.add(city); // Add the parsed City object to the collection
                }
            }
        } catch (SecurityException e){
            System.out.println("You have no access to " + FILE_PATH + ". Ask your admin for rights.");
        } catch (NoSuchFileException e){
            System.out.println("Error occured while loading collection" + e.getMessage() +". Bye!");
            System.exit(1);
        } catch (IOException e){
            System.out.println("No such file or directory. Bye!");
            System.exit(1);
        }
    }

    private Human parseGovernor(String line) {
        String governorName = line.replaceAll("<governor>(.*)</governor>", "$1").trim();

        try {
            if (governorName != "null") {
                return new Human(governorName);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Wrong governor found while parsing data. Default value set.");;
            return new Human("Unknown"); // Return a default Human object or handle the error as needed
        }
    }


    private StandardOfLiving parseStandardOfLiving(String line) {
        String standardOfLivingString = line.replaceAll("<standardOfLiving>(.*)</standardOfLiving>", "$1").trim();

        try {
            return StandardOfLiving.valueOf(standardOfLivingString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong standard. Default value set.");
            return StandardOfLiving.LOW; // Return a default value or handle the error as needed
        }
    }


    private Date parseCreationDate(String line) {
        String dateString = line.replaceAll("<creationDate>(.*)</creationDate>", "$1").trim(); // Убираем лишние пробелы в начале и конце строки

        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US); // Учитывая формат даты

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Wrong date found while parsing data. Default value set.");
            return null;
        }
    }




    private Coordinates parseCoordinates(String line) {
        String coordinatesString = line.replaceAll(
                "<coordinates>Coordinates: \\(x=(.*), y=(.*)\\)</coordinates>", "$1, $2");
        String[] coordinatesArray = coordinatesString.split(", ");

        try {
            Float x = Float.parseFloat(coordinatesArray[0]);
            double y = Double.parseDouble(coordinatesArray[1]);

            return new Coordinates(x, y);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Some coordinates are invalid. Default value set.");;
            return null;
        }
    }





}