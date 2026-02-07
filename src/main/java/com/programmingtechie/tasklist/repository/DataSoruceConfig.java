package com.programmingtechie.tasklist.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class DataSoruceConfig {
    private final DataSource dataSource;
    public Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }

}
