package CollectionObject;

public class Human {
    private String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        if (this.name != null) {
            return name;
        }else{
            return "null";
        }
    }
}
