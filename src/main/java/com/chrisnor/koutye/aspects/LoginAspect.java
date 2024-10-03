package com.chrisnor.koutye.aspects;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.service.serviceimpl.UtilisateurServiceImpl;

@Aspect
public class LoginAspect {
	Logger logger = Logger.getLogger(LoginAspect.class.getName());
	long t1, t2; 
	
	public LoginAspect() throws IOException{
		logger.addHandler(new FileHandler("log4j2.xml"));
		logger.setUseParentHandlers(false);
	}
	
	@Pointcut("execution(* com.chrisnor.koutye.service.serviceimpl.UtilisateurServiceImpl.*(..))")
	public void pc1() {}
	
	@Before("pc1()")
    public void avant(JoinPoint joinPoint) {
    	t1=System.currentTimeMillis();
    	logger.info("---------------------------------------------------");
    	logger.info("Avant execution de la methode: "+joinPoint.getSignature());
    }
	
	@After("pc1()")
    public void apres(JoinPoint joinPoint) {
		logger.info("Apres execution de la methode: "+joinPoint.getSignature());
		t2=System.currentTimeMillis();
		logger.info("Duree d'execution: "+(t2-t1));
		logger.info("---------------------------------------------------");
    }
	
	@Around("pc1() && args(username, password)")
	public void around(String username,String password,ProceedingJoinPoint proceedindJoinPoint, JoinPoint joinPoint) throws Throwable{
		/*
		t1=System.currentTimeMillis();
		logger.info("---------------------------------------------------");
		logger.info("Avant execution de la methode: "+proceedindJoinPoint.getSignature());
    	
    	proceedindJoinPoint.proceed();
    	
    	logger.info("Apres execution de la methode: "+proceedindJoinPoint.getSignature());
		t2=System.currentTimeMillis();
		logger.info("Duree d'execution: "+(t2-t1));
		logger.info("---------------------------------------------------");
		*/
		UtilisateurServiceImpl utilServiceImpl = (UtilisateurServiceImpl) joinPoint.getTarget();
		utilServiceImpl.Login(username, password);
	}
}
