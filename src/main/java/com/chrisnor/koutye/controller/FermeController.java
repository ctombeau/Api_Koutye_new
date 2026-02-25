package com.chrisnor.koutye.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.dto.FermeDto;
import com.chrisnor.koutye.model.Ferme;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.FermeService;
import com.chrisnor.koutye.utils.ConvertImage;

@RestController
@RequestMapping("/api")
public class FermeController {
	
	@Autowired
	private FermeService fermeService;
	
	@Autowired
	private ResponseGenerator responseGenerator;
    
	@PostMapping("/ferme/add")
	public ResponseEntity<?> AddFerme(@RequestBody FermeDto fermeDto)
	{
		Ferme ferme = fermeService.EnFermerAppartement(fermeDto);
		if(ferme != null)
			return responseGenerator.SuccessResponse(HttpStatus.CREATED, ferme);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NOT_FOUND, "Cet appartement n'existe pas");
	}
	/*
	@GetMapping("/transform")
	public String transformImage() throws IOException {
		ConvertImage ci = new ConvertImage();
		return ci.extractBytes("C:/Koutye_Folder/ImageApp/1/back.webp");
	}
	*/
}
