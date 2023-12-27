package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Losh[] loshArray = {
                new Losh("Лосяш", "7", 230, Losh.woolLength.LOW),
                new Losh("Бука", "8", 238, Losh.woolLength.MEDIUM),
                new Losh("Бака", "3", 222, Losh.woolLength.HIGH)
        };
        Losh1 losh1 = new Losh1("Лосяш", 3, 230.5, Losh1.woolLength.LOW);

        AnnotationProcessor.createTable(Losh.class);

        for (Losh losh : loshArray) {
            AnnotationProcessor.insertIntoTable(losh);
        }

        AnnotationProcessor.createTable(Losh1.class);
        AnnotationProcessor.insertIntoTable(losh1);
        }
    }
