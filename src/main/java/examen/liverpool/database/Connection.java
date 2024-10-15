package examen.liverpool.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Connection {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@//host:puerto/nombre_servicio");//Colocar las credenciales
        dataSource.setUsername("user");//Colocar las credenciales
        dataSource.setPassword("password");//Colocar las credenciales
        return dataSource;
    }
}