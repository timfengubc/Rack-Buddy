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
	

	@Persistent
	private String streetNumber;
	
	@Persistent
	private String bia;
	
	@Persistent
	private String streetName;

	@Persistent
	private String streetSide;

	@Persistent
	private String skytrainStation;
	
	@Persistent
	private int numRacks; 

	@Persistent
	private String yearInstalled;
	
	
	public BikeRackData() {
		this.streetNumber = "";
		this.streetName = "";
		this.streetSide = "";
	}
	//Don't need user variable yet...
	public BikeRackData(String streetNumber,
						String bia,
						String streetName,
						String streetSide,
						String skytrainStation,
						int numRacks,
						String yearInstalled
						){
		
		this.streetNumber = streetNumber;
		this.bia = bia;
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

	public String getBia() {
		return bia;
	}
	public void setBia(String bia) {
		this.bia = bia;
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


	
	@Override
	public int hashCode() {
		int hash = 1;

		hash = hash * 5 + (id == null? 0 : id.hashCode());
		hash = hash * 11 + (streetNumber == null ? 0 : streetNumber.hashCode());
		hash = hash * 17 + (bia == null ? 0 : bia.hashCode());
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

		boolean sameID = true;
		if(this.id != null){
			if(this.id.equals(other.id)){
				sameID = true;
			}
			else{
				sameID = false;
			}
		}

		boolean sameStreetNum = (this.streetNumber == null? this.streetNumber == other.streetNumber : this.streetNumber.equals(other.streetNumber));
		boolean sameBia = (this.bia == null? this.bia == other.bia : this.bia.equals(other.bia));
		boolean sameStreetName = (this.streetName == null? this.streetName == other.streetName : this.streetName.equals(other.streetName));
		boolean sameStreetSide = (this.streetSide == null? this.streetSide == other.streetSide : this.streetSide.equals(other.streetSide));

		boolean sameNumRacks = this.numRacks == other.numRacks;
		
		boolean sameYearInstalled = (this.yearInstalled == null? this.yearInstalled == other.yearInstalled : this.yearInstalled.equals(other.yearInstalled));
		
		boolean sameSkytrainStn = (this.skytrainStation == null? this.skytrainStation == other.skytrainStation : this.skytrainStation.equals(other.skytrainStation));
		
		return  sameStreetNum && sameStreetName && sameStreetSide && sameNumRacks && sameYearInstalled && sameSkytrainStn && sameID && sameBia;
	}
	
	@Override
	public String toString() {
		
		String id = (this.id == null? "null" : Long.toString(this.id));
		String streetNum = (this.streetNumber==null? "null" : this.streetNumber);
		String bia = (this.bia==null? "null" : this.bia);
		String streetName = (this.streetName==null? "null" : this.streetName);
		String streetSide = (this.streetSide==null? "null" : this.streetSide);
		String yearInstalled = (this.yearInstalled==null? "null" : this.yearInstalled);
		
		return "id: " + id + ", "+
				" steetNum: " + streetNum+ ", "+
				" bia: " + bia + ", " +
				" streetName: " + streetName+ ", "+
				" streetSide: " + streetSide+ ", "+
				" numRacks: " + this.numRacks+ ", "+
				" yearInstalled: " + yearInstalled;
	}
	
	
}
