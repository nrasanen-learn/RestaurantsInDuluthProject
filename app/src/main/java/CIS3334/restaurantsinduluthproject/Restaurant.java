package CIS3334.restaurantsinduluthproject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * this class allows you to create restaurant objects and sets the data type for each of the attributes of a restaurant
 * and getters and setters for each one
 */
public class Restaurant {
    private String id;
    private String name;
    private String address;
    private ArrayList<String> menuItems;
    private String category;
    private Double rating;


    public Restaurant() {

    }

    public Restaurant(String id, String name, String address, ArrayList<String> menuItems, String category, Double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menuItems = menuItems;
        this.category = category;
        this.rating = rating;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public ArrayList<String> getMenuItems() {
        return menuItems;
    }
    public void setMenuItems(ArrayList<String> menuItems) {
        this.menuItems = menuItems;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }




}
