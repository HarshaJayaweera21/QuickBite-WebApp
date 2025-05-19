package service;

import model.User;
import model.Customer;
import model.Admin;
import jakarta.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final String FILE_PATH = "WEB-INF/resources/data/users.txt";
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final ServletContext servletContext;

    public UserService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        String realPath = servletContext.getRealPath(FILE_PATH);
        if (realPath == null) {
            LOGGER.severe("Unable to resolve real path for " + FILE_PATH);
            throw new IOException("Unable to resolve real path for " + FILE_PATH);
        }
        File file = new File(realPath);

        if (!file.exists()) {
            LOGGER.warning("users.txt does not exist at " + realPath + ". Creating new file.");
            file.getParentFile().mkdirs();
            file.createNewFile();
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCSV(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading users.txt", e);
            throw e;
        }
        return users;
    }

