package KeywordExtraction.NamedEntityTurkish.enums;

public enum WordType {

	ABBREVIATION("Abbreviation"), CITY("City"), CONTINENT("Continent"), COUNTRY(
			"Counry"), DATE("Date"), LOCATION("Location"), MONETARY("Monetary"), NAME("Name"), ORGANIZATION(
			"Organization"), PERCENTAGE("Percantage"), PERSON("Person"), POSSIBLE(
			"Possible"), QUANTITY("Quantity"), TIME("Time");

	private String value;

	private WordType(String s) {
		value = s;
	}

	public String getStatusCode() {
		return value;
	}

}
