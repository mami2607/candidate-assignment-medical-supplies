package ch.aaap.ca.be.medicalsupplies.model;

import ch.aaap.ca.be.medicalsupplies.data.MSProductIdentity;

public class Product implements MSProductIdentity {

    private String id;
    private String name;
    private GenericName genericName;
    private Category primaryCategory;
    private Producer producer;
    private LicenceHolder licenceHolder;

    public Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.genericName = builder.genericName;
        this.primaryCategory = builder.primaryCategory;
        this.producer = builder.producer;
        this.licenceHolder = builder.licenceHolder;
    }

    public static class Builder {
        private String id;
        private String name;
        private GenericName genericName;
        private Category primaryCategory;
        private Producer producer;
        private LicenceHolder licenceHolder;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder genericName(GenericName genericName) {
            this.genericName = genericName;
            return this;
        }

        public Builder primaryCategory(Category primaryCategory) {
            this.primaryCategory = primaryCategory;
            return this;
        }

        public Builder producer(Producer producer) {
            this.producer = producer;
            return this;
        }

        public Builder licenceHolder(LicenceHolder licenceHolder) {
            this.licenceHolder = licenceHolder;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    @Override
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenericName getGenericName() {
        return genericName;
    }

    public void setGenericName(GenericName genericName) {
        this.genericName = genericName;
    }

    public Category getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(Category primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public LicenceHolder getLicenceHolder() {
        return licenceHolder;
    }

    public void setLicenceHolder(LicenceHolder licenceHolder) {
        this.licenceHolder = licenceHolder;
    }
}
