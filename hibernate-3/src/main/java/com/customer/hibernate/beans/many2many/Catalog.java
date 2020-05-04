package com.customer.hibernate.beans.many2many;

import java.util.HashSet;
import java.util.Set;

/**
 * Catalog
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Catalog {
    private Integer id;
    private String name;
    private Set<Item> items = new HashSet<>();

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Catalog() {
    }

    public Catalog(String name, Set<Item> items) {
        this.name = name;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
