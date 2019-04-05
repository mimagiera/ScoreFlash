package footballdata.model.player;

import java.io.Serializable;

public class Player implements Serializable {

	//ATTRIBUT
	private String id;
	private String name;
	private String firstName;
	private String lastName;
	private String position;
	private String dateOfBirth;
	private String countryOfBirth;
	private String nationality;
	private String role;
	private String lastUpdated;

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
