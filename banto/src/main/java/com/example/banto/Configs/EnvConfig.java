package com.example.banto.Configs;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class EnvConfig {
	private final Dotenv dotenv;
	
	public EnvConfig() {
		this.dotenv = Dotenv.configure().ignoreIfMissing().load();
	}
	
	public String get(String key) {
		return dotenv.get(key);
	}
}
