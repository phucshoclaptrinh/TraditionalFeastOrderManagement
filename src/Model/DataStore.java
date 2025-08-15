package Model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DataStore {
    private DataStore() {}

    public static final String FILE_CUSTOMERS = "data/customers.dat";
    public static final String FILE_ORDERS = "data/feast_order_service.dat";

    // CUSTOMERS
    public static boolean SaveCustomers(Collection<Customer> customers, String path) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(new ArrayList<>(customers));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Customer> LoadCustomers(String path) {
        File file = new File(path);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            Object object = objectInputStream.readObject();
            return (object instanceof List) ? (List<Customer>) object : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // ORDERS
    public static boolean SaveOrders(Collection<Order> orders, String path) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(new ArrayList<>(orders));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Order> LoadOrders(String path) {
        File file = new File(path);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            Object object = objectInputStream.readObject();
            return (object instanceof List) ? (List<Order>) object : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static final String FILE_NEXT_ORDER_ID = "data/nextid.txt";

    public static int LoadNextOrderId() {
        Path p = Paths.get(FILE_NEXT_ORDER_ID);
        if (!java.nio.file.Files.exists(p)) return 1;
        try {
            String s = new String(Files.readAllBytes(p), StandardCharsets.UTF_8).trim();
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 1;
        }
    }

    public static boolean SaveNextOrderId(int nextId) {
        try {
            Path p = Paths.get(FILE_NEXT_ORDER_ID);
            Files.createDirectories(p.getParent());
            Files.write(p, String.valueOf(nextId).getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}