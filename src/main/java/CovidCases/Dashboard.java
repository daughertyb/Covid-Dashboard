package CovidCases;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Dashboard {

	public static void main(String[] args) throws IOException {

		// Call a new file and populate states list.
		FileDownload stateLevelData = new FileDownload();
		stateLevelData.refreshCovidStateFile();
		stateLevelData.refreshPopulationStateFile();
		stateLevelData.createCaseData();
		stateLevelData.createPopData();

		// Take user input and print case data.
		System.out.println("Welcome To The Covid Information Center!");
		System.out.println("Please Enter A State To View Current Statistics >>");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();

		for (DataHub state : stateLevelData.stateList) {
			if (state.getState().equals(userInput)) {
				System.out.println("Positive cases In " + state.getState() + " Are "
						+ String.format("%,.0f", Double.parseDouble(state.getStateCases())) + " " + "Hospitalized: "
						+ String.format("%,.0f", Double.parseDouble(state.getStateHospitalized())));
				
				population();
			}

		}
	}

	public static void population() {
		FileDownload stateLevelData = new FileDownload();
		System.out.println("Please Enter A State To View Current Population >>"); //program seems to be terminating in the console about here.
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();

		for (DataHub pop : stateLevelData.populationList) {
			if (pop.getFullState().equals(userInput)) {
				System.out.println("Population of " + pop.getFullState() + " is "
						+ String.format("%,.0f", Double.parseDouble(pop.getPopulation())));
			}
		}
	}
}