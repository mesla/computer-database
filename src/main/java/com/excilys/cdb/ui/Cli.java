package com.excilys.cdb.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;

public class Cli {
	private final static Scanner sc = new Scanner(System.in);
	
	static Logger logger = LoggerFactory.getLogger(Cli.class);
	
	public static void displayChoicesList() throws BadEntryException{
		boolean run = true;
		String i = "";
		while(run) {
			System.out.println(
					"Bonjour, veuillez sélectionner votre choix dans la liste suivante:\n"
					+ " 1 pour obtenir la liste des ordinateurs\n"
					+ " 2 pour selectionner un ordi avec son id\n"
					+ " 3 pour créer un ordi\n"
					+ " 4 pour supprimer un ordi\n"
					+ " 5 pour màj un ordi\n"
					+ " 6 pour lister les companies\n"
					+ " 7 pour quitter");
			i = sc.nextLine();
			
			if(i.matches("^[1-6]$")) {
				try {
					Controller.getInstance().callFonction(i);
					
				} catch (SQLException | ConnectionDBFailedException | RequestFailedException e) {
				    logger.error(e.getMessage());
					Cli.displayChoicesList();
				}
			}
			else if (i.equals("7")){
				sc.close();
				run = false;
			}
			else {
				sc.close();
				throw new BadEntryException("Mauvaise entrée. Veuillez saisir un nombre compris entre 1 et 7");
			}
		}
		sc.close();
	}
	
	public <T> void read (ArrayList<T> dtoList) {
		System.out.println("\nRésultat(s) :\n");
		for (T dto : dtoList) {
			System.out.println(dto.toString());
		}
		System.out.println("\n");
	}

	public int askId() throws BadEntryException {
		System.out.println("Entrez l'id souhaité");
		String str = sc.nextLine();
		try {
			int id = Integer.valueOf(str);
			return id;
		}
		catch(Exception e) {
			throw new BadEntryException("Veuillez rentrer un id valide\n");
		}
	}
	
	
	public ArrayList<String> createOrUpdate() throws BadEntryException {
		
		ArrayList<String> args = new ArrayList<String>();
		
		System.out.println("Entrez le nom (Optionnel pour modifier un ordi \"Entrée\" pour passer)");		
		String str = sc.nextLine();
		args.add(str);
		
		System.out.println("Entrez la date de sortie au format YYYY-MM-DD (optionnel, \"Entrée\" pour passer)");
		str = sc.nextLine();
		if(!str.matches("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"))
			if(!str.isEmpty()) {
				throw new BadEntryException("Veuillez rentrer la date de sortie au format YYYY-MM-DD");
			}
		args.add(str);
		
		
		System.out.println("Entrez la date de retrait au format YYYY-MM-DD (optionnel, \"Entrée\" pour passer)");
		str = sc.nextLine();
		if(!str.matches("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"))
			if(!str.isEmpty()) {
				throw new BadEntryException("Veuillez rentrer la date de retrait au format YYYY-MM-DD");
			}
		args.add(str);
		
		System.out.println("Entrez l'id de l'entreprise (optionnel, \"Entrée\" pour passer)");
		str = sc.nextLine();
		if(!str.matches("^[0-9]*$"))
			if(!str.isEmpty()) {
				throw new BadEntryException("Veuillez rentrer un id valide");
			}

		args.add(str);
		
		return args;
	}

	public int[] askPage() throws BadEntryException {
		
		System.out.println("Combien d'éléments voulez-vous afficher ?");
		String str = sc.nextLine();
		if(!str.matches("^[0-9]*$"))
			if(!str.isEmpty()) {
				throw new BadEntryException("Veuillez rentrer un offset valide");
			}
		int limit = Integer.valueOf(str);
		
		System.out.println("à partir de combien de résultats voulez-vous commencer à afficher ?");
		str = sc.nextLine();
		if(!str.matches("^[0-9]*$"))
			if(!str.isEmpty()) {
				throw new BadEntryException("Veuillez rentrer un offset valide");
			}
				
		int offset = Integer.valueOf(str);
		
		int[] limits = {limit, offset};
		return limits;
	}
}
