package org.chreso.edots;

public class MedDrug {

    private String uuid;
    private String generic_name;
    private String brand_name;
    private String formulation;
    private String generic_ingredients;
    private String generic_strength;


    public MedDrug(String uuid, String genericName, String brandName, String formulation, String genericIngredients, String genericStrength) {
        this.uuid = uuid;
        this.generic_name = genericName;
        this.brand_name = brandName;
        this.formulation = formulation;
        this.generic_ingredients = genericIngredients;
        this.generic_strength = genericStrength;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getGeneric_ingredients() {
        return generic_ingredients;
    }

    public void setGeneric_ingredients(String generic_ingredients) {
        this.generic_ingredients = generic_ingredients;
    }

    public String getGeneric_strength() {
        return generic_strength;
    }

    public void setGeneric_strength(String generic_strength) {
        this.generic_strength = generic_strength;
    }

}
