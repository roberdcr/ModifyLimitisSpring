/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client.types.data;

/**
 *
 * @author Gonzalo
 */
public class Address {
    
    private String street;
    private int blockNumber;
    private int floor;
    private char door;
    private String locality;
    private String province;
    private int zipCode;
    
    public Address(String street, int blockNumber, int floor, char door, String locality, String province, int zipCode) {
        this.street = street;
        this.blockNumber = blockNumber;
        this.floor = floor;
        this.door = door;
        this.locality = locality;
        this.province = province;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public char getDoor() {
        return door;
    }

    public void setDoor(char door) {
        this.door = door;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    
    
}
