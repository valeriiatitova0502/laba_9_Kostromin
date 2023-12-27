package org.example;

@Table(title = "Losh1")
public class Losh1 {

    public enum woolLength {
        LOW, MEDIUM, HIGH;
        public String getWoolLength() {
            return this.toString();
        }
    }

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private double maxRunDistance;

    //@Column
    private woolLength woolLength;

    public Losh1(String name, Integer age, double maxRunDistance, woolLength woolLength) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.woolLength = woolLength;
    }
}