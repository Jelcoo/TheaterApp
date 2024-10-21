package me.jelco.theaterapp.tools;

import javafx.stage.*;
import me.jelco.theaterapp.data.*;

import java.io.*;

public class IOTools {
    static File DBFILE = new File("database.dat");

    public static void saveDatabase(Database database) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DBFILE))) {
            oos.writeObject(database);
            System.out.println("Database saved successfully.");
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file cannot be found: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error during writing database: " + ioe.getMessage());
        }
    }

    public static boolean databaseExists() {
        return DBFILE.exists();
    }

    public static Database getDatabase() {
        Database database = new Database();
        if (!databaseExists()) {
            System.out.println("Database file does not exist. Returning new Database instance.");
            return database;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DBFILE))) {
            database = (Database) ois.readObject();
            System.out.println("Database loaded successfully.");
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file cannot be found: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error during reading database: " + ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found while reading database: " + cnfe.getMessage());
        }
        return database;
    }

    public static File pickFilepath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
        fileChooser.setInitialFileName("showings.csv");
        return fileChooser.showOpenDialog(new Stage());
    }
    public static void writeToFile(File file, String content) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("Showings export done successfully.");
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file cannot be found: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error during writing showings export: " + ioe.getMessage());
        }
    }
}
