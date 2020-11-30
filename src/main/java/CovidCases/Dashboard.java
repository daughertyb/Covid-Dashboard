package CovidCases;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Dashboard {

	public static void main(String[] args) throws IOException {

		// Call a new file and populate states list.
		FileDownload data = new FileDownload();
		data.refreshCovidStateFile();
		data.createCaseData();

		// Take user input and print case data.
		System.out.println("Welcome To The Covid Information Center!");
		System.out.println("Please Enter A State To View Current Statistics >>");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();

		// Print data from Covid state level file.
		data.caseData(userInput);

		// Print data from population file.
		data.population(userInput);

	}

}
