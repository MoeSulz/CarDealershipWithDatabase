package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.*;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        String query = "INSERT INTO inventory (vin, dealership_id) values (?,?);";
        int rows = 0;


        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vin);
            preparedStatement.setInt(2, dealershipId);

            rows = preparedStatement.executeUpdate();
            System.out.println("Row #" + rows + " has been affected");

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeVehicleFromInventory(String vin) {
        String query = "DELETE FROM inventory WHERE vin = ?;";
        int rows = 0;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vin);

            rows = preparedStatement.executeUpdate();
            System.out.println("Row #" + rows + " has been affected");

            try (ResultSet keys = preparedStatement.getGeneratedKeys()){
                while (keys.next()){
                    keys.getString(2);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
