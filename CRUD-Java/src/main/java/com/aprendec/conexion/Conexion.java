package com.aprendec.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Clase de conexión a la base de datos utilizando Apache Commons DBCP.
 * Esta clase maneja la creación y configuración de un DataSource que
 * permite obtener conexiones a la base de datos de manera eficiente.
 */
public class Conexion {
    /** Instancia del DataSource. */
    private static BasicDataSource dataSource = null;

    /**
     * Obtiene una instancia de DataSource.
     * 
     * Si la instancia de dataSource es nula, se inicializa y configura
     * con los parámetros de conexión a la base de datos.
     * 
     * @return DataSource - La instancia configurada de DataSource.
     */
    private static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");
            dataSource.setUrl("jdbc:mariadb://localhost:3306/crud?useTimezone=true&serverTimezone=UTC");
            dataSource.setInitialSize(20);
            dataSource.setMaxIdle(15);
            dataSource.setMaxTotal(20);
            dataSource.setMaxWaitMillis(5000);
        }
        return dataSource;
    }

    /**
     * Obtiene una conexión a la base de datos.
     * 
     * Este método utiliza el DataSource para proporcionar una conexión
     * a la base de datos. Puede lanzar una SQLException si hay un problema
     * al obtener la conexión.
     * 
     * @return Connection - La conexión a la base de datos.
     * @throws SQLException - Si hay un error al obtener la conexión.
     */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}

