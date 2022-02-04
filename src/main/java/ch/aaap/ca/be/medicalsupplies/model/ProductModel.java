package ch.aaap.ca.be.medicalsupplies.model;

/**
 * Assignment 4: "...exstract new information and model it."
 */
public class ProductModel {

    private String productId;
    private String productName;
    private String[] productModel;


    private ProductModel(Builder builder) {
        this.productId = builder.productId;
        this.productName = builder.productName;
        this.productModel = builder.productModel;
    }

    @Override
    public String toString() {
        return " Id: " + this.productId + " Product: " + this.productName + " Model:" + String.join(",", this.productModel);
    }

    public static class Builder {
        private String productId;
        private String productName;
        private String[] productModel;

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productModel(String[] productModel) {
            this.productModel = productModel;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(this);
        }
    }
}
