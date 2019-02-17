package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
//        System.getProperties().put( "server.port", 130 );
        SpringApplication.run(MainApplication.class, args);
    }

}
