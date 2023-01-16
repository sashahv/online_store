package pl.kul.onlinestore.entity.user;

public enum Gender {
    MALE("Mężczyzna"),
    FEMALE("Kobieta"),
    NON_DEFINED("Nieokreślona");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
