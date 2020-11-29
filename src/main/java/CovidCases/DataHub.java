package CovidCases;

public abstract class DataHub {

	private String state;
	private String stateCases;
	private String stateHospitalized;
	private String fullState;
	private String population;

	public DataHub(String state, String stateCases, String stateHospitalized) {
		this.state = state;
		this.stateCases = stateCases;
		this.stateHospitalized = stateHospitalized;
	}
	
	public DataHub(String fullState, String population) {
		this.fullState = fullState;
		this.population = population;
	}

	public String getState() {
		return state;
	}

	public String getStateCases() {
		return stateCases;
	}

	public String getStateHospitalized() {
		return stateHospitalized;
	}
	
	public String getFullState() {
		return fullState;
	}

	public String getPopulation() {
		return population;
	}

	
	
	

}
