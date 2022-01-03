package com.mail.courierservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "courier_orders")
public class CourierOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long packageId;
    private String storage;
    private String orderStatus;

    public CourierOrder() {
    }

    public CourierOrder(long packageId, String storage, String orderStatus) {
        this.packageId = packageId;
        this.storage = storage;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}