package ch.aaap.ca.be.medicalsupplies;

import ch.aaap.ca.be.medicalsupplies.data.CSVUtil;
import ch.aaap.ca.be.medicalsupplies.data.MSGenericNameRow;
import ch.aaap.ca.be.medicalsupplies.data.MSProductRow;
import ch.aaap.ca.be.medicalsupplies.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class MSApplication {

    private final Set<MSGenericNameRow> genericNames;
    private final Set<MSProductRow> registry;
    private static final String MODEL_STRING = "Model:";

    public MSApplication() {
        genericNames = CSVUtil.getGenericNames();
        registry = CSVUtil.getRegistry();
        // Bellow is Assignment 4: "...extract new information and model it."
        // this two methods for extracting new information are declared at the bottom of this file
        //first one is using new model class ProductModel.java to represent new information
        //second method uses simple presentation of extracted data.
        /*  Uncomment this two below lines in order to console output the new information required for Assignment 4*/
        //extractAndModelNewInformationFromProduct();
        //extractNewInformationFromProduct();
    }

    public static void main(String[] args) {
        MSApplication main = new MSApplication();

        System.err.println("generic names count: " + main.genericNames.size());
        System.err.println("registry count: " + main.registry.size());

        System.err.println("1st of generic name list: " + main.genericNames.iterator().next());
        System.err.println("1st of registry list: " + main.registry.iterator().next());
    }

    /**
     * Create a model / data structure that combines the input sets.
     *
     * @param genericNameRows
     * @param productRows
     * @return
     */
    public Set<Product> createModel(Set<MSGenericNameRow> genericNameRows, Set<MSProductRow> productRows) {
        Map<String, GenericName> genericNameMap = new HashMap<>();
        Set<Product> products = new HashSet<>();

        this.genericNames.stream().forEach(msGenericNameRow -> {
            GenericName genericName = new GenericName();
            genericName.setName(msGenericNameRow.getName());
            genericName.setCategories(generateCategories(msGenericNameRow));
            genericNameMap.put(genericName.getName(), genericName);
        });
        this.registry.stream().forEach(msProductRow -> {
            Producer producer = new Producer(msProductRow.getProducerId()
                    , msProductRow.getProducerName()
                    , msProductRow.getProducerAddress());
            LicenceHolder licenceHolder = new LicenceHolder(msProductRow.getLicenseHolderId()
                    , msProductRow.getLicenseHolderName()
                    , msProductRow.getLicenseHolderAddress());
            Product product = Product.Builder.newInstance()
                    .id(msProductRow.getId())
                    .name(msProductRow.getName())
                    .primaryCategory(new Category(msProductRow.getPrimaryCategory()))
                    .producer(producer)
                    .licenceHolder(licenceHolder)
                    .genericName(genericNameMap.get(msProductRow.getGenericName()))
                    .build();
            products.add(product);
        });
        return products;
    }

    private Set<Category> generateCategories(MSGenericNameRow genericNameRow) {
        Set<Category> categories = new HashSet<>();
        addCategory(genericNameRow.getCategory1(), categories);
        addCategory(genericNameRow.getCategory2(), categories);
        addCategory(genericNameRow.getCategory3(), categories);
        addCategory(genericNameRow.getCategory4(), categories);
        return categories;
    }

    private void addCategory(String categoryName, Set<Category> categories) {
        if (null != categoryName && !categoryName.equals("")) {
            categories.add(new Category(categoryName));
        }
    }

    /**
     * Method find the number of unique generic names.
     *
     * @return
     */
    public int numberOfUniqueGenericNames() {
        return genericNames.stream().map(MSGenericNameRow::getName).collect(Collectors.toSet()).size();
    }

    /**
     * Method finds the number of generic names which are duplicated.
     *
     * @return
     */
    public int numberOfDuplicateGenericNames() {
        return genericNames.size() - numberOfUniqueGenericNames();
    }

    /* MS Products */

    /**
     * Method finds the number of products which have a generic name which can be
     * determined.
     *
     * @return
     */
    public long numberOfMSProductsWithGenericName() {
        return createModel(genericNames, registry).stream()
                .filter(product -> product.getGenericName() != null).count();
    }

    /**
     * Method finds the number of products which have a generic name which can NOT
     * be determined.
     *
     * @return
     */
    public long numberOfMSProductsWithoutGenericName() {
        return createModel(genericNames, registry).stream()
                .filter(product -> product.getGenericName() == null).count();
    }

    /**
     * Method finds the name of the company which is both the producer and license holder for the
     * most number of products.
     *
     * @return
     */
    public String nameOfCompanyWhichIsProducerAndLicenseHolderForMostNumberOfMSProducts() {
        Map<String, Long> groupedProducers = createModel(genericNames, registry).stream()
                .filter(product -> product.getLicenceHolder().getName().equals(product.getProducer().getName()))
                .collect(Collectors
                        .groupingBy(product -> product.getProducer().getName(), Collectors.counting()));
        long producersMaxNumberOfProducts = (!groupedProducers.values().isEmpty()) ? Collections.max(groupedProducers.values()) : -1;
        return groupedProducers.entrySet().stream().filter(entry -> producersMaxNumberOfProducts == entry.getValue())
                .map(Map.Entry::getKey).findFirst().orElse("");
    }

    /**
     * Method finds the number of products whose producer name starts with
     * <i>companyName</i>.
     *
     * @param companyName
     * @return
     */
    public int numberOfMSProductsByProducerName(String companyName) {
        return createModel(genericNames, registry).stream()
                .filter(product -> (product.getProducer().getName()).toUpperCase().startsWith(companyName.toUpperCase()))
                .collect(Collectors.toSet()).size();
    }

    /**
     * Method finds the products whose generic name has the category of interest.
     *
     * @param category
     * @return
     */
    public Set<Product> findMSProductsWithGenericNameCategory(String category) {
        return createModel(genericNames, registry).stream()
                .filter(product -> null != product.getGenericName() && null != product.getGenericName().getCategories())
                .filter(product -> product.getGenericName().getCategories().stream().anyMatch(ctgry -> ctgry.getName().equals(category)))
                .collect(Collectors.toSet());
    }

    public Set<Product> getProductsThatContainsModelStringInsideName() {
        return createModel(genericNames, registry).stream()
                .filter(product -> product.getName().contains(MODEL_STRING)).collect(Collectors.toSet());
    }

    // Assignment 4: "...exstract new information and model it."
    // new class is created to represent extracted informations - src/main/java/ch/aaap/ca/be/medicalsupplies/model/ProductModel.java
    // by running this methods bellow, console outputs all extracted Model values from product names
    //1. First  method uses model class that represents Model of product
    //2. Second method roughly extracts data from csv.
    public void extractAndModelNewInformationFromProduct() {
        Set<Product> products = getProductsThatContainsModelStringInsideName();
        Map<String, String> modelElements = products.stream()
                .filter(p -> p.getName().split(MODEL_STRING).length > 1)
                .collect(
                        Collectors.toMap(Product::getID, this::getProductModelBasicData));
        modelElements.forEach((k, v) -> System.out.println("Product_Id: " + k + " | " + v));
    }

    public void extractNewInformationFromProduct() {
        Set<Product> products = getProductsThatContainsModelStringInsideName();
        Map<String, String> modelElements = products.stream()
                .filter(p -> p.getName().split(MODEL_STRING).length > 1)
                .collect(Collectors.toMap(Product::getID, p -> p.getName().split(MODEL_STRING)[1]));
        modelElements.forEach((k, v) -> System.out.println("Product_Id: " + k + " NewInfo: " + v));
    }

    //helper method for extracting basic model data
    private String getProductModelBasicData(Product product) {
        return new ProductModel.Builder().productId(product.getID())
                .productName(product.getName().split(MODEL_STRING)[0])
                .productModel(product.getName().split(MODEL_STRING)[1].split(";")).build().toString();
    }
}
