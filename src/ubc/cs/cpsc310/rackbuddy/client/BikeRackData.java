package ubc.cs.cpsc310.rackbuddy.client;

import java.io.Serializable;

import com.google.appengine.api.users.User;

public class BikeRackData implements Serializable {
	
	private Long id;
	
	private User user;
	
	//can't be null
	private String streetNumber;
	
	//cam't be null
	private String streetName;
	
	//can't be null
	private String streetSide;
	
	//can be null
	private String skytrainStation;
	
	// can be null, if null, set it equal to 0
	private int numRacks; //might change to string
	
	//can be null
	private String yearInstalled;
	
	//default constructor
	public BikeRackData() {
		this.streetNumber = "";
		this.streetName = "";
		this.streetSide = "";
	}
	
	public BikeRackData(String streetNumber,
						String streetName,
						String streetSide,
						String skytrainStation,
						int numRacks,
						String yearInstalled,
						User user){
		
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.streetSide = streetSide;
		this.skytrainStation = skytrainStation;
		this.numRacks = numRacks;
		this.yearInstalled = yearInstalled;
		this.user = user;
		
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetSide() {
		return streetSide;
	}

	public void setStreetSide(String streetSide) {
		this.streetSide = streetSide;
	}

	public String getSkytrainStation() {
		return skytrainStation;
	}

	public void setSkytrainStation(String skytrainStation) {
		this.skytrainStation = skytrainStation;
	}

	public int getNumRacks() {
		return numRacks;
	}

	public void setNumRacks(int numRacks) {
		this.numRacks = numRacks;
	}

	public String getYearInstalled() {
		return yearInstalled;
	}

	public void setYearInstalled(String yearInstalled) {
		this.yearInstalled = yearInstalled;
	}

	//Don't know if I'm doing this right....
	
	@Override
	public int hashCode() {
		int hash = 1;
		
		hash = hash * 3 + id.hashCode();
		hash = hash * 11 + streetNumber.hashCode();
		hash = hash * 17 + streetName.hashCode();
		hash = hash * 13 + streetSide.hashCode();
		hash = hash * 31 + (skytrainStation == null ? 0 : skytrainStation.hashCode());
		hash = hash * 37 + numRacks;
		hash = hash * 31 + (yearInstalled == null ? 0 : yearInstalled.hashCode());
		
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null){
			return false;
		}
		
		if(obj.getClass() != this.getClass()){
			return false;
		}
		
		BikeRackData other = (BikeRackData) obj;
		
		boolean sameID = this.id.equals(other.id);
		boolean sameStreetNum = this.streetNumber.equals(other.streetNumber);
		boolean sameStreetName = this.streetName.equals(other.streetName);
		boolean sameStreetSide = this.streetSide.equals(other.streetSide);

		boolean sameNumRacks = this.numRacks == other.numRacks;
		
		boolean sameYearInstalled = this.yearInstalled.equals(other.yearInstalled);
		
		return sameID && sameStreetNum && sameStreetName && sameStreetSide && sameNumRacks && sameYearInstalled;
	}
	
	
}
