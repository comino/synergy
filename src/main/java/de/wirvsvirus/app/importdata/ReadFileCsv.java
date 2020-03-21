package de.wirvsvirus.app.importdata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ReadFileCsv {

	public static void main(String[] args) throws Exception {
		// TODO copy paste to
		// wirvsvirus/src/main/resources/config/liquibase/fake-data/challenge.csv
		printChallenges();
		// wirvsvirus/src/main/resources/config/liquibase/fake-data/category.csv
//		printCategories();
	}

	private static void printChallenges() throws FileNotFoundException, IOException {
		Reader in = new FileReader("Challenges-Alle Kategorien (Anischten).csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		int i = 1;
		System.out.println(
				"id;name;problems;description;solution;target_audience;stake_holder;slack_channel;ministry_project");
		for (CSVRecord record : records) {
			String challenge = toString(record, 0);
			String titel = toString(record, 1);
			String probleme = toString(record, 2);
			String formulierungHerausforderung = toString(record, 3);
			String loesungsansatz = toString(record, 4);
			String kategorie = toString(record, 5);
			String betroffenengruppe = toString(record, 6);
			String stakeholder = toString(record, 7);
			String challengeSlackChannel = toString(record, 8);
			String ministeriumsprojekt = toString(record, 9);
			if (ministeriumsprojekt != null && ministeriumsprojekt.equals("checked"))
				ministeriumsprojekt = "true";
			else
				ministeriumsprojekt = "false";
//			String challenges = toString(record, 10);
			// FIXME challenge, title different columns
			// FIXME challenge should only have ONE name
			// FIXME ideas should have all the other columns
			System.out.println(i + ";" + // 0, id
					shorten(challenge + ": " + titel) + ";" + // 1, name
					probleme + ";" + // 2, problems
					formulierungHerausforderung + ";" + // 3, description
					loesungsansatz + ";" + // 4, solution
//					kategorie + ";"+  //5, 
					betroffenengruppe + ";" + // 5
					stakeholder + ";" + // 6
					challengeSlackChannel + ";" + // 7
					ministeriumsprojekt); // 8
//			System.out.println(challenge);
//			System.out.println(titel);
//			System.out.println(probleme);
//			System.out.println(formulierungHerausforderung);
//			System.out.println(loesungsansatz);
//			System.out.println(kategorie);
//			System.out.println(betroffenengruppe);
//			System.out.println(stakeholder);
//			System.out.println(challengeSlackChannel);
//			System.out.println(ministeriumsprojekt);
//			System.out.println(challenges);
			i++;
		}
	}

	private static void printCategories() throws FileNotFoundException, IOException {
		Reader in = new FileReader("Challenges-Alle Kategorien (Anischten).csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		System.out.println(
				"id;name;problems;description;solution;target_audience;stake_holder;slack_channel;ministry_project");
		HashSet<String> kategorien = new HashSet<>();
		for (CSVRecord record : records) {
			String kategorie = record.get(5);
			String[] split = kategorie.split(",");
			for (String kat : split)
				if(kat != null && !kat.equals(""))
					kategorien.add(kat);
//			System.out.println(i + ";" + kategorie); 
//			i++;
		}
		int i = 1;
		for (String kategorie : kategorien) {
			System.out.println(i + ";" + kategorie); 
			i++;
		}
	}

	private static String toString(CSVRecord record, int nr) {
		String string = record.get(nr);// .replace(";", " ").replace("\n", "");
//		"/[^0-9a-zA-Z -]/"
		string = string.replaceAll("[^0-9a-zA-Z -]", "").replace("\n", "");
		return shorten(string);
	}

	private static String shorten(String string) {
		if (string.length() > 255)
			return string.substring(0, 255);
		return string;
	}

}
