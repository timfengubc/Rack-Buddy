package ubc.cs.cpsc310.rackbuddy.client;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class BikeRackData implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
//	@Persistent
//	private User user;
	
	@Persistent
	protected String streetNumber; 
	
	@Persistent
	protected String bia;
	
	@Persistent
	protected String streetName;

	@Persistent
	protected String streetSide;

	@Persistent
	protected String skytrainStation;
	
	@Persistent
	protected int numRacks; 

	@Persistent
	protected String yearInstalled;
	
	@Persistent
	protected double lat;
	
	@Persistent
	protected double lng;
	
	
	public BikeRackData() {
		this.streetNumber = "";
		this.bia = "";
		this.streetName = "";
		this.streetSide = "";
		this.skytrainStation = "";
		this.numRacks = 0;
		this.yearInstalled = "";
		this.lat = 0;
		this.lng = 0;
	}
	/**
	 * This constructor is only for the JUNIT tests
	 * since it is only testing parsing of the data,
	 * we don't need to worry about lat lng.
	 * So just set lat lng to default value
	 */
	public BikeRackData(String streetNumber, String bia, String streetName,
			String streetSide, String skytrainStation, int numRacks,
			String yearInstalled) {

		this.streetNumber = streetNumber;
		this.bia = bia;
		this.streetName = streetName;
		this.streetSide = streetSide;
		this.skytrainStation = skytrainStation;
		this.numRacks = numRacks;
		this.yearInstalled = yearInstalled;
		this.lat = 0;
		this.lng = 0;
		// this.user = user;

	}

	//Don't need user variable yet...
	public BikeRackData(String streetNumber,
						String bia,
						String streetName,
						String streetSide,
						String skytrainStation,
						int numRacks,
						String yearInstalled,
						double lat,
						double lng
						){
		
		this.streetNumber = streetNumber;
		this.bia = bia;
		this.streetName = streetName;
		this.streetSide = streetSide;
		this.skytrainStation = skytrainStation;
		this.numRacks = numRacks;
		this.yearInstalled = yearInstalled;
		this.lat = lat;
		this.lng = lng;
//		this.user = user;
		
	}
	
	/**
	 * Copy constructor for cloning
	 * so that the object can be updated
	 * @param other
	 */
	public BikeRackData(BikeRackData other){
		this.streetNumber = other.streetNumber;
		this.bia = other.bia;
		this.streetName = other.streetName;
		this.streetSide = other.streetSide;
		this.skytrainStation = other.skytrainStation;
		this.numRacks = other.numRacks;
		this.yearInstalled = other.yearInstalled;
		this.lat = other.lat;
		this.lng = other.lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

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
		hash = hash * 51 + Double.valueOf(lat).hashCode();
		hash = hash * 53 + Double.valueOf(lng).hashCode();
		
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
		
		double epilson = 0.00001;

		boolean sameStreetNum = (this.streetNumber == null? this.streetNumber == other.streetNumber : this.streetNumber.equals(other.streetNumber));
		boolean sameBia = (this.bia == null? this.bia == other.bia : this.bia.equals(other.bia));
		boolean sameStreetName = (this.streetName == null? this.streetName == other.streetName : this.streetName.equals(other.streetName));
		boolean sameStreetSide = (this.streetSide == null? this.streetSide == other.streetSide : this.streetSide.equals(other.streetSide));
		boolean sameNumRacks = this.numRacks == other.numRacks;
		boolean sameYearInstalled = (this.yearInstalled == null? this.yearInstalled == other.yearInstalled : this.yearInstalled.equals(other.yearInstalled));
		boolean sameSkytrainStn = (this.skytrainStation == null? this.skytrainStation == other.skytrainStation : this.skytrainStation.equals(other.skytrainStation));
		boolean sameLat = this.compareDouble(this.lat, other.lat, epilson);
		boolean sameLng = this.compareDouble(this.lng, other.lng, epilson);
		
		return  sameStreetNum && sameStreetName && sameStreetSide && sameNumRacks && sameYearInstalled && sameSkytrainStn && sameID && sameBia && sameLat && sameLng;
	}
	
	@Override
	public String toString() {
		
		String id = (this.id == null? "null" : Long.toString(this.id));
		String streetNum = (this.streetNumber==null? "null" : this.streetNumber);
		String bia = (this.bia==null? "null" : this.bia);
		String streetName = (this.streetName==null? "null" : this.streetName);
		String streetSide = (this.streetSide==null? "null" : this.streetSide);
		String yearInstalled = (this.yearInstalled==null? "null" : this.yearInstalled);
		String lat = String.valueOf(this.lat);
		String lng = String.valueOf(this.lng);
		
		return "id: " + id + ", "+
				" steetNum: " + streetNum+ ", "+
				" bia: " + bia + ", " +
				" streetName: " + streetName+ ", "+
				" streetSide: " + streetSide+ ", "+
				" numRacks: " + this.numRacks+ ", "+
				" yearInstalled: " + yearInstalled+
				" Lat: " + lat + 
				" Lng: " + lng;
	}
	
	private boolean compareDouble(double a, double b, double epilson){
		
		if(Math.abs(a - b) <= epilson){
			return true;
		}
		
		return false;
	}
	
}
