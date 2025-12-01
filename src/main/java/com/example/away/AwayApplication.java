package com.example.away;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwayApplication {

	public static void main(String[] args) {
		// Carrega o arquivo .env (opcional - se não existir, usa variáveis de ambiente do sistema)
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();

			// Exporta as variáveis carregadas para o System Properties
			dotenv.entries().forEach(entry ->
					System.setProperty(entry.getKey(), entry.getValue())
			);
		} catch (Exception e) {
			// Se não conseguir carregar .env, continua com variáveis de ambiente do sistema
			System.out.println("Arquivo .env não encontrado, usando variáveis de ambiente do sistema");
		}

		SpringApplication.run(AwayApplication.class, args);
	}

}
