package ubc.cs.cpsc310.rackbuddy.client;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class BikeRackData implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
//	@Persistent
//	private User user;
	
	//can't be null
	@Persistent
	private String streetNumber;
	
	//cam't be null
	@Persistent
	private String streetName;
	
	//can't be null
	@Persistent
	private String streetSide;
	
	//can be null
	@Persistent
	private String skytrainStation;
	
	// can be null, if null, set it equal to 0
	@Persistent
	private int numRacks; //might change to string
	
	//can be null
	@Persistent
	private String yearInstalled;
	
	//default constructor
	public BikeRackData() {
		this.streetNumber = "";
		this.streetName = "";
		this.streetSide = "";
	}
	//Don't need user variable yet...
	public BikeRackData(String streetNumber,
						String streetName,
						String streetSide,
						String skytrainStation,
						int numRacks,
						String yearInstalled
						){
		
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.streetSide = streetSide;
		this.skytrainStation = skytrainStation;
		this.numRacks = numRacks;
		this.yearInstalled = yearInstalled;
//		this.user = user;
		
	}
	
	

//	public User getUser() {
//		return user;
//	}

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
		//TODO hashcode ID?
		hash = hash * 11 + (streetNumber == null ? 0 : streetNumber.hashCode());
		hash = hash * 17 + (streetName == null ? 0 : streetName.hashCode());
		hash = hash * 13 + (streetSide == null ? 0 : streetSide.hashCode());
		hash = hash * 31 + (skytrainStation == null ? 0 : skytrainStation.hashCode());
		hash = hash * 37 + numRacks;
		hash = hash * 41 + (yearInstalled == null ? 0 : yearInstalled.hashCode());
		
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
		//TODO compare ID?
		boolean sameStreetNum = this.streetNumber.equals(other.streetNumber);
		boolean sameStreetName = this.streetName.equals(other.streetName);
		boolean sameStreetSide = this.streetSide.equals(other.streetSide);

		boolean sameNumRacks = this.numRacks == other.numRacks;
		
		boolean sameYearInstalled = this.yearInstalled.equals(other.yearInstalled);
		
		return  sameStreetNum && sameStreetName && sameStreetSide && sameNumRacks && sameYearInstalled;
	}
	
	@Override
	public String toString() {
		
		String id = (this.id == null? "null" : Long.toString(this.id));
		String streetNum = (this.streetNumber==null? "null" : this.streetNumber);
		String streetName = (this.streetName==null? "null" : this.streetName);
		String streetSide = (this.streetSide==null? "null" : this.streetSide);
		String yearInstalled = (this.yearInstalled==null? "null" : this.yearInstalled);
		
		return "id: " + id + ", "+
				" steetNum: " + streetNum+ ", "+
				" streetName: " + streetName+ ", "+
				" streetSide: " + streetSide+ ", "+
				" numRacks: " + this.numRacks+ ", "+
				" yearInstalled: " + yearInstalled;
	}
	
	
}
