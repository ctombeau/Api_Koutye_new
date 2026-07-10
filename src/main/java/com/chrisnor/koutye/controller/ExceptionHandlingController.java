package com.chrisnor.koutye.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chrisnor.koutye.exception.ApiError;
import com.chrisnor.koutye.exception.FileExcededSizeException;
import com.chrisnor.koutye.exception.FileNotFoundException;
import com.chrisnor.koutye.exception.InvalidInputException;
import com.chrisnor.koutye.exception.SqlInsertException;
import com.chrisnor.koutye.exception.UserNotFoundException;

//@RestController
@RestControllerAdvice
public class ExceptionHandlingController {
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiError> handleInvalidInputException(){
		
		ApiError apiError = new ApiError(400,"informations saisies incorrectes", LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SqlInsertException.class)
	public ResponseEntity<ApiError> handleSqlInputException()
	{
		ApiError apiError = new ApiError(500,"Erreur au moment de l'insertion", LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiError> handleFileNotFoundException()
	{
		ApiError apiError = new ApiError(404,"Image non modifiee, Fichier non trouve",LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileExcededSizeException.class)
	public ResponseEntity<ApiError> handleFileExcededSizeException()
	{
		ApiError apiError = new ApiError(500,"Le fichier depasse la taille maximale",LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFoundException()
	{
		ApiError apiError = new ApiError(404,"L'utilisateur n'existe pas.",LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}
}
