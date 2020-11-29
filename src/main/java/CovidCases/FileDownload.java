package CovidCases;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
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
}
