package Modules;

import CollectionObject.City;
import java.util.ArrayDeque;

public interface DataProvider {

    void save(ArrayDeque<City> collection);

    void load();
}
