package com.example.away;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwayApplication {

	public static void main(String[] args) {
		// Carrega o arquivo .env
		Dotenv dotenv = Dotenv.load();

		// Exporta as variáveis carregadas para o System Properties
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(AwayApplication.class, args);
	}

}
