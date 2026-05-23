
package tunixserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class App {
    public void getGreeting() {
        System.out.println("Hello, World!");
    }
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
