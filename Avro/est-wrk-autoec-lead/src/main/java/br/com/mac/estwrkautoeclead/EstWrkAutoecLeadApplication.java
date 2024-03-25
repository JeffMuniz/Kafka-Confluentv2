package br.com.machina.estwrkautoeclead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.machina")
public class EstWrkAutoecLeadApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstWrkAutoecLeadApplication.class, args);
	}

}
