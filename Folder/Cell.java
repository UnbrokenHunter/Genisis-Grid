package Folder;

public class Cell {
    public static enum Type {
        MASS, ENERGY, EMPTY
    }

    private Type type;
    private int age; // Only relevant for MASS cells

    public Cell(Type type) {
        this.type = type;
        this.age = 0;
    }

    public Cell(Type type, int age) {
        this.type = type;
        this.age = age;
    }

    // Getters and setters
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        setAge(0);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void incrementAge() {
        this.age++;
    }

}
