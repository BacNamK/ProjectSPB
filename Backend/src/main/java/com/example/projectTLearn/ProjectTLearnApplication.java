package com.example.projectTLearn;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ProjectTLearnApplication {

	public static void main(String[] args) {
		String envDirectory = resolveEnvDirectory();
		Dotenv dotenv = Dotenv.configure()
				.directory(envDirectory)
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(ProjectTLearnApplication.class, args);
	}

	private static String resolveEnvDirectory() {
		Path currentDir = Path.of("").toAbsolutePath();
		Path parentDir = currentDir.getParent();

		if (parentDir != null && currentDir.getFileName() != null && currentDir.getFileName().toString().equals("Backend")) {
			return parentDir.toString();
		}

		return currentDir.toString();
	}

}
