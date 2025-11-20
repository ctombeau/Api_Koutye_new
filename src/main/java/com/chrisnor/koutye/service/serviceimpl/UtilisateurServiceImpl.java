package com.chrisnor.koutye.service.serviceimpl;


import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.exception.InvalidInputException;
import com.chrisnor.koutye.exception.SqlInsertException;
import com.chrisnor.koutye.model.ActiveToken;
import com.chrisnor.koutye.model.Attachement;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.ActiveTokenRepository;
import com.chrisnor.koutye.repository.AttachementRepository;
import com.chrisnor.koutye.repository.TypeUtilisateurRepository;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.service.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@PersistenceContext
@Transactional 
public class UtilisateurServiceImpl implements UtilisateurService{
	@Value("${expiration_Date}")
	private int expirationDate;
	
    @Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	@Autowired
	private TypeUtilisateurRepository typeUtilRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EntityManager em;
	
	@Autowired
	private JwtEncoder jwtEncoder;
	
	@Autowired
	private AttachementRepository attachRepo;
	
	@Autowired
	private ActiveTokenRepository activeTokenRepo;
	
	public UtilisateurDto setUpdate(Long id,UtilisateurDto utilDto)
	{
		try {
			TypeUtilisateur typeUtil = typeUtilRepo.findByNomType(utilDto.getNomType());
			Query q = em.createNativeQuery("update utilisateur set nom=:userNom,prenom=:userPrenom,email=:userEmail,"
					+ "username=:userName,phone=:userPhone,"
					+ "modification_date=:userDate where utilisateur_id=:userId",UtilisateurDto.class);
			q.setParameter("userNom", utilDto.getNom().toUpperCase());
			q.setParameter("userPrenom", utilDto.getPrenom());
			q.setParameter("userEmail", utilDto.getEmail());
			q.setParameter("userName", utilDto.getUsername());
			q.setParameter("userPhone", utilDto.getPhone());
			q.setParameter("userDate", LocalDateTime.now());
			q.setParameter("userId", id);
			
			int isUpdated = q.executeUpdate();
			
			if(isUpdated>0)
				return utilDto;
			else
				return null;
		}
		catch(Exception e)
		{
			throw new SqlInsertException();
		}
	}

	@Override
	public UtilisateurDto putUtilisateur(Long id, UtilisateurDto utilDto) {
		//Utilisateur util = utilisateurRepo.findById(id).get();
		//Utilisateur utilBase = new Utilisateur();
		//utilBase = utilisateurRepo.getById(id);
		/*
		if(utilBase.getEmail().equals(utilDto.getEmail()) && utilBase.getUsername().equalsIgnoreCase(utilDto.getUsername()))
		{
			return this.setUpdate(id, utilDto);
		}
		else {
			if(!utilBase.getEmail().equals(utilDto.getEmail())){
				if(this.getUtilisateurByEmail(utilDto.getEmail()) != null) {
					return null;
				}
				else {
					return this.setUpdate(id, utilDto);
					
				}
				
			}
			else if(!utilBase.getUsername().equalsIgnoreCase(utilDto.getUsername())){
				if(this.getUtilisateur(utilDto.getUsername()) != null) {
					return null;
				}
				else {
					return this.setUpdate(id, utilDto);
					
				}
			}
			else {
				if(this.getUtilisateur(utilDto.getUsername()) == null && this.getUtilisateurByEmail(utilDto.getEmail()) != null ) {
					return this.setUpdate(id, utilDto);
					
				}
				else {
					
					return null;
				}
		
		  }
		}
		*/
		return this.setUpdate(id, utilDto);
	}

	@Override
	public Optional<UtilisateurDto> getUtilisateur(String username) {
        
	    Utilisateur util = utilisateurRepo.findUtilisateurByUsername(username);
	    
	    return Optional.ofNullable(util)
	               .map(u -> modelMapper.map(u, UtilisateurDto.class));
	}

	@Override
	@Transactional
	public void login(String username) {
		Utilisateur utilisateur = utilisateurRepo.findUtilisateurByUsername(username);
	    
	    if (utilisateur == null) {
	        throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
	    }

	    utilisateur.setLoginDate(LocalDateTime.now());
	    utilisateurRepo.save(utilisateur);
		
	}

	
	@Override
	public Page<Utilisateur> getAllUtilisateurs(int pageNo, int pageSize) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Utilisateur> utilisateurs = utilisateurRepo.findAll(paging);
        	                         
         return utilisateurs;
       
	}
	
	
	@Override
	public List<UtilisateurDto> getUtilisateurs() {
		List<Utilisateur> utilisateurs = utilisateurRepo.findAll();
		
		return  utilisateurs.stream()
				            .map(utilisateur-> modelMapper.map(utilisateur, UtilisateurDto.class))
							.collect(Collectors.toList());
	}
	

	@Override
	public UtilisateurDto postUtilisateur( UtilisateurDto utilDto) {
		Utilisateur util = new Utilisateur();
		TypeUtilisateur typeUtil = new TypeUtilisateur();
		
        try
        {
		    
        	typeUtil = typeUtilRepo.findByNomType(utilDto.getNomType());
        	Utilisateur utilisateur =util.builder()
        								 .nom(utilDto.getNom())
        								 .prenom(utilDto.getPrenom())
        								 .username(utilDto.getUsername())
        								 .email(utilDto.getEmail())
        								 .password(passwordEncoder.encode(utilDto.getPassword()))
        								 .photo("")
        								 .phone(utilDto.getPhone())
        								 .actif(true)
        								 .typeUtilisateur(typeUtil)
        								 .creationDate(LocalDateTime.now())
        								 .build();
        	
        	if(typeUtil != null)
        	{
        		utilisateurRepo.save(utilisateur);
        		return modelMapper.map(utilisateur, UtilisateurDto.class);
        	}
        	
        	return null;
			
        }
        catch(Exception e)
        {
        	throw new SqlInsertException();
        }
			
	}

	@Override
	public long findIdTypeByNomType(String nomType) {
		
		Query id = em.createNativeQuery("select id_type from type_utilisateur where nom_type= ?");
		return 0;
	}

	
	@Override
	public Map<String, Object> generateToken(String username, Authentication authentication) {
		Instant instant = Instant.now();
		String id = UUID.randomUUID().toString();
		
		String scope = authentication.getAuthorities()
				                     .stream()
				                     .map(a->a.getAuthority())
				                     .collect(Collectors.joining(" "));
		
		JwtClaimsSet claims = JwtClaimsSet
									.builder()
									.id(id)
									.issuedAt(instant)
									.expiresAt(instant.plus(expirationDate,ChronoUnit.MINUTES))
									.subject(username)
									.claim("scope", scope)
									.build();
		String token = jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(), claims)).getTokenValue();
		ActiveToken at = new ActiveToken();
		
		at.setJti(id);
		at.setUsername(username);
		at.setLastActivity(LocalDateTime.now());
		activeTokenRepo.save(at);
		return Map.of("access-token",token,"user-info",this.getUtilisateur(username).get());
	}

	@Override
	public Optional<UtilisateurDto> getUtilisateurByEmail(String email) {
		Utilisateur util = utilisateurRepo.findUtilisateurByEmail(email);
		return Optional.ofNullable(util)
	               .map(u -> modelMapper.map(u, UtilisateurDto.class));
		
	}

	@Override
	public String generateDefaultPassword() 
	{
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
        		+ "0123456789"
                +"@#$"; 

        StringBuilder s = new StringBuilder(10); 

        for (int i = 0; i < 10; i++) { 
        	int index = (int)(str.length() * Math.random()); 
        	s.append(str.charAt(index)); 
        } 
        return s.toString(); 
	}

	@Override
	public void setPassword(String password, String email) {
		String passwordEncode = passwordEncoder.encode(password);
		Query q = em.createNativeQuery("update utilisateur set password=:pass, modification_date=:mdate,actif=:actif where email=:mail");
		q.setParameter("pass", passwordEncode);
		q.setParameter("mail", email);
		q.setParameter("mdate", LocalDateTime.now());
		q.setParameter("actif", false);
		q.executeUpdate();
	}

	@Override
	public boolean verifyEmail(String email) {
		Optional<UtilisateurDto> util = this.getUtilisateurByEmail(email);
		return util != null; 	
	}
	
    /*
	@Override
	public List<UtilisateurDto> getUtilisateurs() {
		// TODO Auto-generated method stub
		return null;
	}
    */
	@Override
	public void updateProfilePicture(String username, String path) {
		Query q = em.createNativeQuery("update utilisateur set photo=:photo,modification_date=:mdate where username=:username");
		q.setParameter("photo", path)
		 .setParameter("mdate", LocalDateTime.now())
		 .setParameter("username", username)
		 .executeUpdate();
		
	}

	@Override
	public boolean firstLoginAfterForgetPassword(String email, String oldPassword, String newPassword) {
		
		Optional<UtilisateurDto> utilDto = getUtilisateurByEmail(email);
		if(utilDto.isPresent())
		{
			if(passwordEncoder.matches(oldPassword,utilDto.get().getPassword()))
			{
				String newPasswordEncrypt = passwordEncoder.encode(newPassword);
			    int result = utilisateurRepo.firstLoginAfterForgetPassword(email, newPasswordEncrypt, LocalDateTime.now(), true);
			    if(result>0)
			    	return true;
			    else
			    	return false;
			}
			else 
				return false;
		}
		else
			return false;
  }

	@Override
	public boolean postAttachUsers(String usernamePro, String usernameCour) {

		UtilisateurDto utilPro = this.getUtilisateur(usernamePro).get();
		UtilisateurDto utilCour = this.getUtilisateur(usernameCour).get();
		Attachement attach = new Attachement();
		
		if(utilPro != null && utilCour != null)
		{
			attach.setProprietaireId(utilPro.getUtilisateurId());
			attach.setCourtierId(utilCour.getUtilisateurId());
			attachRepo.save(attach);
			return true;
		}
		return false;
	}

	@Override
	public List<UtilisateurDto> getUserAttachment(String username) {
		Optional<UtilisateurDto> utilDto = this.getUtilisateur(username);
		List<UtilisateurDto> maListeDto = new ArrayList<UtilisateurDto>();
		if(utilDto.get().getNomType().equals("Proprietaire"))
		{
			List<Long> ids = attachRepo.findIdCourtiersByIdProprietaire(utilDto.get().getUtilisateurId());
			
			List<Utilisateur> utils = utilisateurRepo.findAllById(ids);
			
			 utils.stream().forEach(util->{
				 UtilisateurDto utilisateurDto = modelMapper.map(util, UtilisateurDto.class);
				 maListeDto.add(utilisateurDto);
			 });
			 return maListeDto;
		}
		else if(utilDto.get().getNomType().equals("Courtier"))
		{
			return Arrays.asList(utilDto.get()) ;
		}
		return null;
	}

	@Override
	public boolean getdetachUsers(String usernamePro, String usernameCour) {
		Optional<UtilisateurDto> utilDtoPro = this.getUtilisateur(usernamePro);
		Optional<UtilisateurDto> utilDtoCour = this.getUtilisateur(usernameCour);
		
		if(utilDtoCour.get().getNomType().equals("Courtier") && utilDtoPro.get().getNomType().equals("Proprietaire"))
		{
			int result = attachRepo.deleteAttachUser(utilDtoPro.get().getUtilisateurId(), utilDtoCour.get().getUtilisateurId());
			return result > 0 ?  true : false;
			
		}
		else
		 return false;
	}

	@Override
	public String forgotPassword(String email) {
		boolean verifyEmail = verifyEmail(email);
		if(verifyEmail==true) {
			String defaultPassword = generateDefaultPassword();
			setPassword(defaultPassword, email);
		}
		return null;
		
	}

	@Override
	public void deleteProfilePicture(String username) {
		Query q = em.createNativeQuery("update utilisateur set photo=:photo,modification_date=:mdate where username=:username");
		q.setParameter("photo", "")
		 .setParameter("mdate", LocalDateTime.now())
		 .setParameter("username", username)
		 .executeUpdate();
	}
}  
