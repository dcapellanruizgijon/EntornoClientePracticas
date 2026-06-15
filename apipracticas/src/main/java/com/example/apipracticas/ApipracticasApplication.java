package com.example.apipracticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApipracticasApplication {

	public static void main(String[] args) {
		// If a datasource URL is provided in the environment, assume we're running in production
		// and default to the 'prod' profile unless a profile is already set.
		String envDs = System.getenv("SPRING_DATASOURCE_URL");
		String sysProfile = System.getProperty("spring.profiles.active");
		String envProfile = System.getenv("SPRING_PROFILES_ACTIVE");
		SpringApplication app = new SpringApplication(ApipracticasApplication.class);
		if ((sysProfile == null || sysProfile.isBlank()) && (envProfile == null || envProfile.isBlank())
				&& envDs != null && !envDs.isBlank()) {
			java.util.Map<String, Object> defaults = new java.util.HashMap<>();
			defaults.put("spring.profiles.active", "prod");
			app.setDefaultProperties(defaults);
		}
		app.run(args);
	}

}