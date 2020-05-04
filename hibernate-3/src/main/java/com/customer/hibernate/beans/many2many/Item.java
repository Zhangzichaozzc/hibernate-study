package com.customer.hibernate.beans.many2many;

import java.util.HashSet;
import java.util.Set;

/**
 * Item
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Item {
    private Integer id;
    private String name;
    private Set<Catalog> catalogs = new HashSet<>();

    public Set<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Set<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Item(String name) {
        this.name = name;
    }

    public Item() {
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

}
