package com.chrisnor.koutye.service.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisnor.koutye.KoutyeApplication;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.service.UtilisateurService;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes=KoutyeApplication.class)
public class UtilisateurServiceImplTest {
	//@Autowired
	@Mock
	private UtilisateurRepository utilRepo;
	
	@Autowired
	private UtilisateurService utilService;
	
	private AutoCloseable autoCloseable;
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void tearDown() throws Exception{
		autoCloseable.close();
	}
	
	@Test
	@Disabled
	public void Loging(String username, String password) {
		
	}
	
	@Test
	@Disabled
	public void addUser()
	{
		UtilisateurDto utilDtoToSave = UtilisateurDto.builder()
													  .nom("Gelin")
													  .prenom("Schtaraya")
													  .username("gschtaraya")
													  .password("Talalap")
													  .email("gschtaraya@gmail.com")
													  .phone("38051274")
													  .photo("")
													  .nomType("Locataire")
													  .build();
		UtilisateurDto savedUtilDto = utilService.PostUtilisateur(utilDtoToSave);
		//assertNotNull(savedUtilDto);
		//assertEquals(savedUtilDto.getNom(),utilDtoToSave.getNom());
		
		ArgumentCaptor<Utilisateur> utilisateurArgCaptor = ArgumentCaptor.forClass(Utilisateur.class);
		verify(utilRepo).save(utilisateurArgCaptor.capture());
		
		Utilisateur capturedUtilisateur = utilisateurArgCaptor.getValue();
		
		assertThat(capturedUtilisateur).isEqualTo(utilDtoToSave);
	}
	
	@Test
	@Disabled
	void getAllUsers()
	{
		utilService.getUtilisateurs();
		verify(utilRepo).findAll();
	}
}
