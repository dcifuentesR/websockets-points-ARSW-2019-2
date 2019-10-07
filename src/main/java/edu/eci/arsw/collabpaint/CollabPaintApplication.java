package edu.eci.arsw.collabpaint;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.collabpaint"})
public class CollabPaintApplication{

	public static void main(String[] args) {
		
		SpringApplication.run(CollabPaintApplication.class, args);
	}
}
