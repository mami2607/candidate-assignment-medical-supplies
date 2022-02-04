package ch.aaap.ca.be.medicalsupplies.model;

import java.util.Set;


public class GenericName {
    private String name;
    private Set<Category> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public GenericName() {
    }

    public GenericName(String name, Set<Category> categories) {
        this.name = name;
        this.categories = categories;
    }
}
