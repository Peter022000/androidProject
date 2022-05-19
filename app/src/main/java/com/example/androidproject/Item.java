package com.example.androidproject;

public class Item {
    private int id;
    private String name;
    private String description;
    private int value;
    private int weight;
    private int amount;
    private int typeId;
    private String typeName;

    public Item(int id, String name, String description, int value, int weight, int typeId, String typeName, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.weight = weight;
        this.amount = amount;
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public int getAmount() {
        return amount;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
}
