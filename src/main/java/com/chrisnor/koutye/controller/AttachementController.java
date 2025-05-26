package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chrisnor.koutye.service.UtilisateurService;

@Controller
@RequestMapping("/api")
public class AttachementController {
	@Autowired
	private UtilisateurService utilService;
    
	@GetMapping("/attach-user")
	public String attachUsers(@RequestParam String usernamePro, @RequestParam String usernameCour,RedirectAttributes redirectAttributes)
	{
		System.out.println("Test de submit via email");
		boolean result = utilService.postAttachUsers(usernamePro, usernameCour);
		if (result) {
	        redirectAttributes.addFlashAttribute("message", "Vous avez accepté la demande avec succès.");
	        return "redirect:/api/confirmation-success";
	    } else {
	        redirectAttributes.addFlashAttribute("message", "Une erreur s'est produite.");
	        return "redirect:/api/confirmation-error";
	    }
	}
	
	@GetMapping("/confirmation-success")
    public String confirmationSuccess() {
        return "confirmation-success"; // nom du fichier HTML sans extension
    }
	
	@GetMapping("/confirmation-error")
    public String confirmationError() {
        return "confirmation-error"; // nom du fichier HTML sans extension
    }
}
