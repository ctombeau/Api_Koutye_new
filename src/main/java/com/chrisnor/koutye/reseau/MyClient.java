package com.chrisnor.koutye.reseau;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
	public void sendMessage(String usernameSender,String usernameReceiver, String message ) throws IOException{
		System.out.println("Je me connecte au serveur...");
		Socket s = new Socket("localhost",1234); 
		/*
		InputStream is = s.getInputStream();
		OutputStream os = s.getOutputStream();
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Donner un nombre: ");
		int nb = sc.nextInt();
		System.out.println("J'envoie le nombre "+ nb + " au serveur");
		os.write(nb);
		System.out.println("J'attends la reponse du serveur");
		int res = is.read();
		System.out.println("Reponse du serveur: "+res);
		*/
	}
	
	public void receiveMessage() throws IOException{
		
	}
}
