package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

public interface UtilisateurService {
	public UtilisateurDto postUtilisateur(UtilisateurDto utilisateurDto);
	public UtilisateurDto putUtilisateur(Long id,UtilisateurDto utilDto);
	public Page<Utilisateur> getAllUtilisateurs(int pageNo, int pageSize);
	public List<UtilisateurDto> getUtilisateurs();
	public Optional<UtilisateurDto> getUtilisateur(String username);
	public Optional<UtilisateurDto> getUtilisateurByEmail(String email);
	public UtilisateurDto setUpdate(Long id, UtilisateurDto utilDto);
	//public Optional<UtilisateurDto> getUtilisateurByUsernameExceptUserId();
	public void login(String username);
	public long findIdTypeByNomType(String nomType);
	public Map<String, Object> generateToken(String username, Authentication authentication);
	public String generateDefaultPassword();
	public void setPassword(String password, String email);
	public boolean verifyEmail(String email);
	public void updateProfilePicture(String username, String path);
	public boolean firstLoginAfterForgetPassword(String email, String oldPassword, String newPassword);
	public boolean postAttachUsers(String usernamePro, String usernameCour);
	public List<UtilisateurDto> getUserAttachment(String username);
	public boolean getdetachUsers(String usernamePro, String usernameCour);
	public String forgotPassword(String email);
	public void deleteProfilePicture(String username);
}
