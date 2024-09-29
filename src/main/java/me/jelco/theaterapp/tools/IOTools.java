package me.jelco.theaterapp.tools;

import me.jelco.theaterapp.data.Database;

import java.io.*;

public class IOTools {
    static File DBFILE = new File("database.dat");

    public static void saveDatabase(Database database) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DBFILE))) {
            oos.writeObject(database);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Het bestand kan niet worden gevonden.");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    public static boolean databaseExists() {
        return DBFILE.exists();
    }
    public static Database getDatabase() {
        Database database = new Database();
        if (!databaseExists()) return database;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DBFILE))) {
            while (true) {
                try {
                    database = (Database) ois.readObject();
                } catch (EOFException eofe) {
                    break; // break out of the loop
                } catch (ClassNotFoundException e) {
                    break;
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Het bestand kan niet worden gevonden.");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return database;
    }
}
