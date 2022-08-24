package org.example.factorymethod;

import org.example.factorymethod.framework.Product;

public class IDCard extends Product {
    private String owner;

    // IDCard的构造函数访问控制不是public，这样可以强迫包外必须通过IDCardFactory来生成IDCard的实例
    IDCard(String owner) {
        System.out.println("create " + owner + "'s IDCard");
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public void use() {
        System.out.println("use " + owner + "'s IDCard");
    }
}
