package CovidCases;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileDownload {

	// State level Data ArrayList.
	List<DataHub> stateList = new ArrayList<DataHub>();
	List<DataHub> populationList = new ArrayList<DataHub>();

	// Download covid state file from source and store in working directory.
	public void refreshCovidStateFile() throws IOException {
		URL website = new URL("https://api.covidtracking.com/v1/states/current.csv");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("current_state.csv");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}

	// Download population file from source and store in working directory.
	public void refreshPopulationStateFile() throws IOException {
		URL website = new URL(
				"https://www2.census.gov/programs-surveys/popest/datasets/2010-2019/national/totals/nst-est2019-alldata.csv");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("population.csv");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}

	// Put state level case data and population data into array lists.
	public void createCaseData() throws FileNotFoundException {
		File currentCase = new File("current_state.csv");
		Scanner scanner1 = new Scanner(currentCase);
		int lineCounter1 = 0;

		// Data from Covid state case file.
		while (scanner1.hasNextLine()) {
			String line1 = scanner1.nextLine();

			if (lineCounter1 != 0) {
				String[] caseArr = line1.trim().split("\\,");
				String state = caseArr[1];
				String totalCases = caseArr[2];
				String hospitalized = caseArr[8];

				if (!state.equals("")) {
					DataHub newState = new State(state, totalCases, hospitalized);
					stateList.add(newState);
				}
			}

			lineCounter1++;
		}
	}

	public void createPopData() throws FileNotFoundException {
		File currentPop = new File("population.csv");
		Scanner scanner2 = new Scanner(currentPop);
		int lineCounter2 = 0;

		// Data from population file.
		while (scanner2.hasNextLine()) {

			String line2 = scanner2.nextLine();

			if (lineCounter2 != 0) {
				String[] popArr = line2.trim().split("\\,");
				String fullState = popArr[4];
				String population = popArr[16];

				if (!fullState.equals("")) {
					DataHub newPop = new Population(fullState, population);
					populationList.add(newPop);
				}
			}

			lineCounter2++;

		}

	}

	public void caseData(String userInput) throws IOException {
		for (DataHub state : stateList) {
			if (state.getState().equals(userInput)) {
				System.out.println("Total Positive Cases: "
						+ String.format("%,.0f", Double.parseDouble(state.getStateCases())) + "\n" + "Hospitalized: "
						+ String.format("%,.0f", Double.parseDouble(state.getStateHospitalized())));

			} else if (userInput.length() > 2) {
				userInput = getStateCode(userInput);
				if (state.getState().equals(userInput)) {
					System.out.println(
							"Total Positive Cases: " + String.format("%,.0f", Double.parseDouble(state.getStateCases()))
									+ "\n" + "Hospitalized: "
									+ String.format("%,.0f", Double.parseDouble(state.getStateHospitalized())));

				}

			}

		}

	}

	public void population(String userInput) throws IOException {
		String stateCases = null;

		for (DataHub pop : populationList) {
			if (pop.getFullState().equals(userInput)) {

				String stateCode = getStateCode(pop.getFullState());

				for (DataHub state : stateList) {
					if (state.getState().equals(stateCode)) {
						stateCases = state.getStateCases();
					}
				}

				System.out.println(divideTwoStrings(stateCases, pop.getPopulation()) + "% of " + pop.getFullState()
						+ "'s population has tested postive for Covid-19");

			} else if (userInput.length() < 3) {
				userInput = getFullState(userInput);
				if (pop.getFullState().equals(userInput)) {
					System.out.println(divideTwoStrings(pop.getStateCases(), pop.getPopulation()) + "% of "
							+ pop.getFullState() + "'s population has tested postive for Covid-19");

				}
			}
		}
	}

	// Map to convert fullState to stateCode.
	public String getStateCode(String fullState) {

		Map<String, String> states = new HashMap<String, String>();
		states.put("Alabama", "AL");
		states.put("Alaska", "AK");
		states.put("Alberta", "AB");
		states.put("American Samoa", "AS");
		states.put("Arizona", "AZ");
		states.put("Arkansas", "AR");
		states.put("Armed Forces (AE)", "AE");
		states.put("Armed Forces Americas", "AA");
		states.put("Armed Forces Pacific", "AP");
		states.put("British Columbia", "BC");
		states.put("California", "CA");
		states.put("Colorado", "CO");
		states.put("Connecticut", "CT");
		states.put("Delaware", "DE");
		states.put("District Of Columbia", "DC");
		states.put("Florida", "FL");
		states.put("Georgia", "GA");
		states.put("Guam", "GU");
		states.put("Hawaii", "HI");
		states.put("Idaho", "ID");
		states.put("Illinois", "IL");
		states.put("Indiana", "IN");
		states.put("Iowa", "IA");
		states.put("Kansas", "KS");
		states.put("Kentucky", "KY");
		states.put("Louisiana", "LA");
		states.put("Maine", "ME");
		states.put("Manitoba", "MB");
		states.put("Maryland", "MD");
		states.put("Massachusetts", "MA");
		states.put("Michigan", "MI");
		states.put("Minnesota", "MN");
		states.put("Mississippi", "MS");
		states.put("Missouri", "MO");
		states.put("Montana", "MT");
		states.put("Nebraska", "NE");
		states.put("Nevada", "NV");
		states.put("New Brunswick", "NB");
		states.put("New Hampshire", "NH");
		states.put("New Jersey", "NJ");
		states.put("New Mexico", "NM");
		states.put("New York", "NY");
		states.put("Newfoundland", "NF");
		states.put("North Carolina", "NC");
		states.put("North Dakota", "ND");
		states.put("Northwest Territories", "NT");
		states.put("Nova Scotia", "NS");
		states.put("Nunavut", "NU");
		states.put("Ohio", "OH");
		states.put("Oklahoma", "OK");
		states.put("Ontario", "ON");
		states.put("Oregon", "OR");
		states.put("Pennsylvania", "PA");
		states.put("Prince Edward Island", "PE");
		states.put("Puerto Rico", "PR");
		states.put("Quebec", "QC");
		states.put("Rhode Island", "RI");
		states.put("Saskatchewan", "SK");
		states.put("South Carolina", "SC");
		states.put("South Dakota", "SD");
		states.put("Tennessee", "TN");
		states.put("Texas", "TX");
		states.put("Utah", "UT");
		states.put("Vermont", "VT");
		states.put("Virgin Islands", "VI");
		states.put("Virginia", "VA");
		states.put("Washington", "WA");
		states.put("West Virginia", "WV");
		states.put("Wisconsin", "WI");
		states.put("Wyoming", "WY");
		states.put("Yukon Territory", "YT");

		return states.get(fullState);
	}

	// Map to convert stateCode to fullState.
	public String getFullState(String stateCode) {

		Map<String, String> STATE_MAP = new HashMap<String, String>();
		STATE_MAP.put("AL", "Alabama");
		STATE_MAP.put("AK", "Alaska");
		STATE_MAP.put("AB", "Alberta");
		STATE_MAP.put("AZ", "Arizona");
		STATE_MAP.put("AR", "Arkansas");
		STATE_MAP.put("BC", "British Columbia");
		STATE_MAP.put("CA", "California");
		STATE_MAP.put("CO", "Colorado");
		STATE_MAP.put("CT", "Connecticut");
		STATE_MAP.put("DE", "Delaware");
		STATE_MAP.put("DC", "District Of Columbia");
		STATE_MAP.put("FL", "Florida");
		STATE_MAP.put("GA", "Georgia");
		STATE_MAP.put("GU", "Guam");
		STATE_MAP.put("HI", "Hawaii");
		STATE_MAP.put("ID", "Idaho");
		STATE_MAP.put("IL", "Illinois");
		STATE_MAP.put("IN", "Indiana");
		STATE_MAP.put("IA", "Iowa");
		STATE_MAP.put("KS", "Kansas");
		STATE_MAP.put("KY", "Kentucky");
		STATE_MAP.put("LA", "Louisiana");
		STATE_MAP.put("ME", "Maine");
		STATE_MAP.put("MB", "Manitoba");
		STATE_MAP.put("MD", "Maryland");
		STATE_MAP.put("MA", "Massachusetts");
		STATE_MAP.put("MI", "Michigan");
		STATE_MAP.put("MN", "Minnesota");
		STATE_MAP.put("MS", "Mississippi");
		STATE_MAP.put("MO", "Missouri");
		STATE_MAP.put("MT", "Montana");
		STATE_MAP.put("NE", "Nebraska");
		STATE_MAP.put("NV", "Nevada");
		STATE_MAP.put("NB", "New Brunswick");
		STATE_MAP.put("NH", "New Hampshire");
		STATE_MAP.put("NJ", "New Jersey");
		STATE_MAP.put("NM", "New Mexico");
		STATE_MAP.put("NY", "New York");
		STATE_MAP.put("NF", "Newfoundland");
		STATE_MAP.put("NC", "North Carolina");
		STATE_MAP.put("ND", "North Dakota");
		STATE_MAP.put("NT", "Northwest Territories");
		STATE_MAP.put("NS", "Nova Scotia");
		STATE_MAP.put("NU", "Nunavut");
		STATE_MAP.put("OH", "Ohio");
		STATE_MAP.put("OK", "Oklahoma");
		STATE_MAP.put("ON", "Ontario");
		STATE_MAP.put("OR", "Oregon");
		STATE_MAP.put("PA", "Pennsylvania");
		STATE_MAP.put("PE", "Prince Edward Island");
		STATE_MAP.put("PR", "Puerto Rico");
		STATE_MAP.put("QC", "Quebec");
		STATE_MAP.put("RI", "Rhode Island");
		STATE_MAP.put("SK", "Saskatchewan");
		STATE_MAP.put("SC", "South Carolina");
		STATE_MAP.put("SD", "South Dakota");
		STATE_MAP.put("TN", "Tennessee");
		STATE_MAP.put("TX", "Texas");
		STATE_MAP.put("UT", "Utah");
		STATE_MAP.put("VT", "Vermont");
		STATE_MAP.put("VI", "Virgin Islands");
		STATE_MAP.put("VA", "Virginia");
		STATE_MAP.put("WA", "Washington");
		STATE_MAP.put("WV", "West Virginia");
		STATE_MAP.put("WI", "Wisconsin");
		STATE_MAP.put("WY", "Wyoming");
		STATE_MAP.put("YT", "Yukon Territory");

		return STATE_MAP.get(stateCode);
	}

	public static double divideTwoStrings(String string1, String string2) {
		double a = Double.parseDouble(string1);
		double b = Double.parseDouble(string2);
		Long c = (long) 100;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double percent = a / b;
		return Double.valueOf(twoDForm.format(percent * c));
	}
}
