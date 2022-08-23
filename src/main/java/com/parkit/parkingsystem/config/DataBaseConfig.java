package com.parkit.parkingsystem.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    
    public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
    	
        logger.info("Create DB connection");
        
        // Get user and password in properties file
        Path pathProperties = FileSystems.getDefault().getPath(".", "bdd.properties");
    	BufferedReader input = Files.newBufferedReader(pathProperties, StandardCharsets.UTF_8);
        Properties pros = new Properties();
        pros.load(input);      
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        input.close();
        
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prod",user, password);
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection",e);
            }
        }
    }

    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement",e);
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set",e);
            }
        }
    }
}
