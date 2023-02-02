package dataBase;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;

import person.*;

public class DataBaseConnection {

    private Connection connection;
    private PreparedStatement prepareStatement;
    private Statement statement;

    public DataBaseConnection() {
        initializeConnection();
    }

    public boolean checkUser(User user) {
        try {
            String request = "SELECT * FROM users336385 WHERE username='" + user.getName() + "' AND password='" + user.getPassword() + "'";
            ResultSet result = statement.executeQuery(request); // Объект, хранящий ответ от БД

            // Проверка на найденных пользователей
            int k = 0;
            while (result.next()) {
                k++;
            }
            if (k == 1) return true;
        } catch (SQLException e) {
            System.out.println("Ошибка соединения к базе данных");
        }
        return false;
    }

    public boolean isContainsUserByUsername(User user) throws SQLException {
        String request = "SELECT * FROM users336385 WHERE username='" + user.getName() + "'";
        ResultSet result = statement.executeQuery(request); // Объект, хранящий ответ от БД

        // Проверка на найденных пользователей
        int k = 0;
        while (result.next()) {
            k++;
        }
        return k == 1;
    }

    public void addUser(User user) throws SQLException {
        prepareStatement = connection.prepareStatement("INSERT INTO users336385 (username, password)" +
                "VALUES (?, ?)");

        prepareStatement.setString(1, user.getName());
        prepareStatement.setString(2, user.getPassword());

        prepareStatement.execute();
    }


    public Collection loadCollection(DataBaseConnection dataBaseConnection) throws SQLException {
        Collection collection = new Collection(dataBaseConnection);
        LinkedList<Person> people = new LinkedList<>();

        String request = "SELECT * FROM people336385;";
        ResultSet result = statement.executeQuery(request);

        while (result.next()) {
            Integer id = result.getInt("id");
            String name = result.getString("name");

            int cor_x = result.getInt("cor_x");
            Long cor_y = result.getLong("cor_y");
            Coordinates coordinates = new Coordinates();
            coordinates.setX(cor_x);
            coordinates.setY(cor_y);

            LocalDate creationDate = LocalDate.parse(result.getString("creationDate"));
            Float height = result.getFloat("height");

            LocalDate badBirthday = LocalDate.parse(result.getString("birthday"));
            ZonedDateTime birthday = badBirthday.atStartOfDay(ZoneId.systemDefault());

            float weight = result.getFloat("weight");
            Country country = Country.valueOf(result.getString("country"));

            long loc_x = result.getLong("loc_x");
            Float loc_y = result.getFloat("loc_y");
            Double loc_z = result.getDouble("loc_z");
            String loc_name = result.getString("loc_name");
            Location location = new Location();
            location.setX(loc_x);
            location.setY(loc_y);
            location.setZ(loc_z);
            location.setName(loc_name);

            String owner = result.getString("owner");

            Person person = new Person();
            person.setThisId(id);
            person.setThisName(name);
            person.setThisCoordinates(coordinates);
            person.setThisCreationDate(creationDate);
            person.setThisHeight(height);
            person.setThisWeight(weight);
            person.setThisBirthday(birthday);
            person.setThisCountry(country);
            person.setThisLocation(location);

            person.setOwner(owner);
            people.add(person);
        }
        collection.setCollection(people);
        System.out.println("Коллекция успешно заполнена.");
        return collection;
    }

    public boolean addPerson(Person person, int id) {
        try {
            prepareStatement = connection.prepareStatement("INSERT INTO people336385 (id, name, cor_x, cor_y, creationDate, height, birthday, weight, country, loc_x, loc_y, loc_z, loc_name, owner) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, person.getName());
            prepareStatement.setInt(3, person.getCoordinates().getX());
            prepareStatement.setLong(4, person.getCoordinates().getY());
            prepareStatement.setDate(5, Date.valueOf(person.getCreationDate()));
            prepareStatement.setFloat(6, person.getHeight());
            prepareStatement.setDate(7, Date.valueOf(person.getBirthday().toLocalDate()));
            prepareStatement.setFloat(8, person.getWeight());
            prepareStatement.setString(9, person.getCountry().name());
            prepareStatement.setLong(10,person.getLocation().getX());
            prepareStatement.setFloat(11,person.getLocation().getY());
            prepareStatement.setDouble(12, person.getLocation().getZ());
            prepareStatement.setString(13,person.getLocation().getName());
            prepareStatement.setString(14,person.getOwner());

            prepareStatement.execute();
            return true;
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            System.out.println("Error with adding to DB");
            return false;
        }
    }

    public boolean removeById(int id, String owner) {
        try {
            prepareStatement = connection.prepareStatement("DELETE FROM people336385 WHERE (id = ?) AND (owner = ?);");
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, owner);

            int isUpdated = prepareStatement.executeUpdate();
            return isUpdated != 0;
        } catch (SQLException e) {
            System.out.println("Error with removing from DB");
            return false;
        }
    }


    public int getIdSeq() throws SQLException {
        String request = "SELECT nextval('id_sequence');";
        ResultSet result = statement.executeQuery(request);
        result.next();

        return result.getInt(1);
    }

    public boolean clear(User user) throws SQLException {
        prepareStatement = connection.prepareStatement("DELETE FROM people336385 WHERE owner = ?;");
        prepareStatement.setString(1, user.getName());
        int k = prepareStatement.executeUpdate();
        return k != 0;
    }

    private void initializeConnection() {
        try {
            //connection = DriverManager.getConnection();
            connection = DriverManager.getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Database connection error, check file with properties, exit...");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
