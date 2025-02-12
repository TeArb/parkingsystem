package com.parkit.parkingsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseTestConfig extends DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");
    /**
	* 
	* @param create database connection
	*/
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        return DriverManager.getConnection(
        		"jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "rootroot");
    }
    /**
	* 
	* @param close database connection
	*/
    public void closeConnection(Connection con) {
        if(con != null) {
            try {
                con.close();
                logger.info("Closing DB connection");
            }
            catch (SQLException e) {
                logger.error("Error while closing connection", e);
            }
        }
    }
    /**
	* 
	* @param close precompiled SQL statement stored
	*/
    public void closePreparedStatement(PreparedStatement ps) {
        if(ps != null){
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            }
            catch (SQLException e) {
                logger.error("Error while closing prepared statement", e);
            }
        }
    }
    /**
	* 
	* @param close the table of data generated
	*/
    public void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
                logger.info("Closing Result Set");
            }
            catch (SQLException e) {
                logger.error("Error while closing result set", e);
            }
        }
    }
}
