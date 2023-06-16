package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.*;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        String query = "INSERT INTO sales_contract (vin, date, price) values (?,?,?);";
        int rows = 0;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, salesContract.getVin());
            preparedStatement.setString(2, String.valueOf(salesContract.getSaleDate()));
            preparedStatement.setDouble(3, salesContract.getPrice());

            rows = preparedStatement.executeUpdate();
            System.out.println("Row #" + rows + " has been affected");

            try (ResultSet keys = preparedStatement.getGeneratedKeys()){
                while (keys.next()){
                    keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
