/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.Model;

public class GuestModel {

    private int guestId;
    private String fullName;
    private String contact;
    private String email;
    private String address;
    private String registrationDate;

    // DEFAULT CONSTRUCTOR
    public GuestModel() {
    }

    // CONSTRUCTOR WITH GUEST ID
    public GuestModel(int guestId, String fullName,
            String contact, String email,
            String address, String registrationDate) {

        this.guestId = guestId;
        this.fullName = fullName;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.registrationDate = registrationDate;
    }

    // CONSTRUCTOR WITHOUT GUEST ID
    public GuestModel(String fullName, String contact,
            String email, String address,
            String registrationDate) {

        this.fullName = fullName;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.registrationDate = registrationDate;
    }

    // GETTERS AND SETTERS

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
