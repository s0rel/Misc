package org.example.factorymethod;

import org.example.factorymethod.framework.Factory;
import org.example.factorymethod.framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {
    private final List<String> owners = new ArrayList<>();

    public List<String> getOwners() {
        return owners;
    }

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        owners.add(((IDCard) product).getOwner());
    }
}
