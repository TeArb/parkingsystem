package com.parkit.parkingsystem.service;

import java.sql.Connection;

import com.parkit.parkingsystem.config.DataBaseTestConfig;

public class DataBasePrepareService {

    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    /**
	* 
	* @param clear the DB
	*/
    public void clearDataBaseEntries() {
        Connection connection = null;
        
        try{
            connection = dataBaseTestConfig.getConnection();
            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();
            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
}
