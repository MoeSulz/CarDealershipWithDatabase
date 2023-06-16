package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.*;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        String query = "INSERT INTO sales_contract (vin, leasestart, leaseend, monthly_payment) values (?,?,?,?);";
        int rows = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setString(2, String.valueOf(leaseContract.getLeaseStart()));
            preparedStatement.setString(3, String.valueOf(leaseContract.getLeaseEnd()));
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());

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
