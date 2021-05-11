package com.yuuy.designpattern.creation.builder;


/**
 * 静态内部类的方式使用建造者模式
 */
public class Product {
    private int id;
    private String name;
    private String fullName;
    private String address;
    private String phoneNumber;

    public String toString() {
        return String.format(
                "id = %d\nname=%s\nfullName=%s\naddress=%s\nphoneNumber=%s",
                id, name, fullName, address, phoneNumber
        );
    }

    public static class Builder {
        private Product product = new Product();

        public Builder addFullName(String fullName) {
            product.fullName = fullName;
            return this;
        }

        public Builder addAddress(String  address) {
            product.address = address;
            return this;
        }

        public Builder addPhoneNumber(String  phoneNumber) {
            product.phoneNumber = phoneNumber;
            return this;
        }

        public Builder addId(int id) {
            product.id = id;
            return this;
        }

        public Builder addName(String name) {
            product.name = name;
            return this;
        }

        public Product build() {
            return product;
        }
    }
}
