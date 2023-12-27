package org.example;

@Table(title = "Losh")
public class Losh {

    public enum woolLength {
        LOW, MEDIUM, HIGH;
        public String getWoolLength() {
            return this.toString();
        }
    }

    @Column
    private String name;

    @Column
    private String age;

    @Column
    private int maxRunDistance;

    @Column
    private woolLength woolLength;

    public Losh(String name, String age, int maxRunDistance, woolLength woolLength) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.woolLength = woolLength;
    }
}