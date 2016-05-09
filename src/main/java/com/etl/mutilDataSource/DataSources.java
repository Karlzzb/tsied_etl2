package com.etl.mutilDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  
  
/**
 * 
 * @author rock
 *
 */
public class DataSources extends AbstractRoutingDataSource{  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return DataSourceSwitch.getDataSourceType();  
    }  
  
}  
  