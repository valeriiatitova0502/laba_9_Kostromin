package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AnnotationProcessor {

    public static void createTable(Class<?> cl) throws Exception {
        if (!cl.isAnnotationPresent(Table.class)) {
            throw new Exception("Класс не содержит аннотации @Table");
        }
        Table table = cl.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("CREATE TABLE " + table.title() + " (");
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                sql.append(field.getName()).append(" ");

                if (field.getType().getSimpleName().equals("int")) {
                    sql.append("INTEGER");
                } else if (field.getType().getSimpleName().equals("double")) {
                    sql.append("DOUBLE");
                } else if (field.getType().getSimpleName().equals("String")) {
                    sql.append("TEXT");
                } else if (field.getType().isEnum()) {
                    sql.append("TEXT");
                }
            }
        }
        if (sql.charAt(sql.length() - 1) == ',') {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(");");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lr9.db");
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + table.title() + ";");
            statement.execute(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertIntoTable(Object cl) {
        Class<?> clClass = cl.getClass();
        if (!clClass.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Класс не содержит аннотации @Table");
        }
        String tableName = clClass.getAnnotation(Table.class).title();
        Field[] fields = clClass.getDeclaredFields();
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                query.append(field.getName()).append(",");
            }
        }
        if (query.charAt(query.length() - 1) == ',') {
            query.deleteCharAt(query.length() - 1);
        }
        query.append(") VALUES (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    query.append("'");
                    if (field.getType() == int.class) {
                        query.append(field.getInt(cl));
                    } else if (field.getType() == String.class || field.getType().isEnum()) {
                        query.append(field.get(cl));
                    }
                    query.append("',");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (query.charAt(query.length() - 1) == ',') {
            query.deleteCharAt(query.length() - 1);
        }
        query.append(")");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:lr9.db");
            Statement statement = connection.createStatement();
            statement.execute(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}