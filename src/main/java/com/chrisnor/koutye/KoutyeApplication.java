package com.chrisnor.koutye;

import java.util.Arrays;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chrisnor.koutye.aspects.LoginAspect;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.chrisnor.koutye.modelmysql.Client;
import com.chrisnor.koutye.repository.TypeUtilisateurRepository;
import com.chrisnor.koutye.repository2.ClientRepository;
import com.chrisnor.koutye.reseau.MyServer;
import com.chrisnor.koutye.service.EmailService;
import com.chrisnor.koutye.service.UtilisateurService;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
public class KoutyeApplication {
	@Autowired
	private EmailService emailService;
	
	protected static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		SpringApplication.run(KoutyeApplication.class, args);
		//new MyServer().start();
		logger.info("Hello World test log4j");
	}
    
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	/*
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail(){
		emailService.sendEmail("jeanedmasherley@gmail.com", "Test Email", "Je teste l'email de mon application cherie");
	}
	*/
	
	//@Bean
	CommandLineRunner commandLineRunner(TypeUtilisateurRepository typeRepo) {
		return args->{
			typeRepo.save(TypeUtilisateur.builder().nomType("BacKAdmin").build());
			typeRepo.save(TypeUtilisateur.builder().nomType("Courtier").build());
			typeRepo.save(TypeUtilisateur.builder().nomType("Locataire").build());
			typeRepo.save(TypeUtilisateur.builder().nomType("Proprietaire").build());
		};
	}
	//@Bean
	CommandLineRunner commandLineRunner2(ClientRepository clientRepo) {
		return args->{
			clientRepo.save(Client.builder().username("ctombeau").password("1234").build());
			clientRepo.save(Client.builder().username("jedma").password("1234").build());
 
		};
	}
	
	
}
