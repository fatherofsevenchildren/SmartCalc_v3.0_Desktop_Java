package edu.school21.smartcalc.serializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;

public class Serializer {

    private static final String LOG_FILE_NAME = "log.ser";

    private final Expressions expressions;

    public Serializer() {
        try {
            this.expressions = loadLogs();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashSet<String> getExpressions() {
        return expressions.getExpressions();
    }

    public void addExpression(String expr) {
        expressions.addExpression(expr);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearExpressions() {
        expressions.clearExpressions();
    }


    public void save() throws IOException {
        Path logFilePath = getLogFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(logFilePath))) {
            oos.writeObject(expressions);
        }
    }

    public Expressions loadLogs() throws IOException, ClassNotFoundException {
        Path logFilePath = getLogFilePath();
        if (Files.exists(logFilePath)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(logFilePath))) {
                return (Expressions) ois.readObject();
            }
        }
        return new Expressions();
    }

    private Path getLogFilePath() {
        Path resourceDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "edu", "school21", "smartcalc", "serializer");
        Path logFilePath = resourceDir.resolve(LOG_FILE_NAME);
        if (!Files.exists(logFilePath.getParent())) {
            try {
                Files.createDirectories(logFilePath.getParent());
            } catch (IOException e) {
                throw new RuntimeException("Could not create log directory", e);
            }
        }
        return logFilePath;
    }
}