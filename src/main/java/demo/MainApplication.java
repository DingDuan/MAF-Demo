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
//
//        double TP = 719.0;
//        double FP = 33.0;
//        double FN = 59.0;
//        double P = TP/(TP+FP);
//        double R = TP/(TP+FN);
//        System.out.println(2*P*R/(P+R));
    }

}
