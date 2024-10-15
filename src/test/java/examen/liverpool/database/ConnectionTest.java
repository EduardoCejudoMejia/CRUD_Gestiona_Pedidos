package examen.liverpool.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootTest
public class ConnectionTest {

    @Autowired
    private ApplicationContext applicationContext;

    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = new Connection();
    }

    @Test
    public void testDataSourceBean() {
        DataSource dataSource = connection.dataSource();

        // Verificamos que no sea nulo
        assertThat(dataSource).isNotNull();

        // Verificamos que sea una instancia de DriverManagerDataSource
        assertThat(dataSource).isInstanceOf(DriverManagerDataSource.class);

        DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) dataSource;

        // Verificamos la configuración
        assertThat(driverManagerDataSource.getDriverClassName()).isEqualTo("oracle.jdbc.OracleDriver");
        assertThat(driverManagerDataSource.getUrl()).isEqualTo("jdbc:oracle:thin:@//host:puerto/nombre_servicio");
        assertThat(driverManagerDataSource.getUsername()).isEqualTo("user");
        assertThat(driverManagerDataSource.getPassword()).isEqualTo("password");
    }
}