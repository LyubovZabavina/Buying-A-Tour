package ru.netology.data;


import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.sql.CreditRequest;
import ru.netology.sql.PaymentRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static Connection connect;

    public static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app", "user", "12345");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connect;
    }

    public static String getPaymentStatus() {
        val runner = new QueryRunner();
        val payStatus = "SELECT status FROM payment_entity";
        try (val connect = getConnection()) {
            val paymentStatus = runner.query(connect, payStatus, new BeanHandler<>(PaymentRequest.class));
            return paymentStatus.getStatus();
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

    public static String getPaymentAmount() {
        val runner = new QueryRunner();
        val payAmount = "SELECT amount FROM payment_entity";
        try (val connect = getConnection()) {
            val paymentAmount = runner.query(connect, payAmount, new BeanHandler<>(PaymentRequest.class));
            return paymentAmount.getAmount();
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

    public static String getCreditStatus() {
        val runner = new QueryRunner();
        val cStatus = "SELECT status FROM credit_request_entity";
        try (val connect = getConnection()) {
            val creditStatus = runner.query(connect, cStatus, new BeanHandler<>(CreditRequest.class));
            return creditStatus.getStatus();
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }
}
