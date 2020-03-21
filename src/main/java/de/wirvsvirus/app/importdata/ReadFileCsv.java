package de.wirvsvirus.app.importdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ReadFileCsv {

	public static void main(String[] args) throws Exception {
		writeChallenges();
		writeIdeas();
		writeCategories();
	}

	private static void writeChallenges() throws IOException, FileNotFoundException {
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader("Challenges-Alle Kategorien (Anischten).csv"));
		writeFile("src/main/resources/config/liquibase/fake-data/challenge.csv", printChallenges(records));
	}

	private static void writeIdeas() throws IOException, FileNotFoundException {
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader("Challenges-Alle Kategorien (Anischten).csv"));
		writeFile("src/main/resources/config/liquibase/fake-data/idea.csv", printIdeas(records));
	}

	private static void writeCategories() throws IOException, FileNotFoundException {
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new FileReader("Challenges-Alle Kategorien (Anischten).csv"));
		writeFile("src/main/resources/config/liquibase/fake-data/category.csv", printCategories(records));
	}

	private static void writeFile(String filename, StringBuilder printChallenges) throws IOException {
		Writer append = new BufferedWriter(new FileWriter(new File(filename)));
		append.append(printChallenges);
		append.close();
	}

	private static StringBuilder printChallenges(Iterable<CSVRecord> records) {
		StringBuilder sb = new StringBuilder();
		sb.append("id;name\n");
		HashSet<String> challenges = new HashSet<>();
		for (CSVRecord record : records) {
			String challenge = record.get(0);
			if(challenge != null && !challenge.equals(""))
				challenges.add(challenge);
		}
		int i = 1;
		for (String challenge : challenges) {
			sb.append(i).append(";").append(challenge).append("\n");
			i++;
		}
		return sb;
	}

	private static StringBuilder printIdeas(Iterable<CSVRecord> records) {
		int i = 1;
		StringBuilder sb = new StringBuilder();
		sb.append("id;title;problems;description;solution;target_audience;stake_holder;slack_channel;ministry_project\n");
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
			sb.append(i).append(";").
				append(titel).append(";").
				append(probleme).append(";").
				append(formulierungHerausforderung).append(";").
				append(loesungsansatz).append(";").
				append(betroffenengruppe).append(";").
				append(stakeholder).append(";").
				append(challengeSlackChannel).append(";").
				append(ministeriumsprojekt).append("\n");
			i++;
		}
		return sb;
	}

	private static StringBuilder printCategories(Iterable<CSVRecord> records) {
		StringBuilder sb = new StringBuilder();
		sb.append("id;name\n");
		HashSet<String> kategorien = new HashSet<>();
		for (CSVRecord record : records) {
			String kategorie = record.get(5);
			String[] split = kategorie.split(",");
			for (String kat : split)
				if(kat != null && !kat.equals(""))
					kategorien.add(kat);
		}
		int i = 1;
		for (String kategorie : kategorien) {
			sb.append(i).append(";").append(kategorie).append("\n");
			i++;
		}
		return sb;
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
