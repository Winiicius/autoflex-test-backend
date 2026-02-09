package com.projedata.autoflex.inventory.entities.enums;

public enum Unit {

    KG("Kilogram"),
    G("Gram"),
    L("Liter"),
    ML("Milliliter"),
    UNIT("Unit");

    private final String description;

    Unit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}