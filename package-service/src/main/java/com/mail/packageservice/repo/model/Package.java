package com.mail.packageservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "packages")
public final class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long senderId;
    private long recipientId;
    private String fromAddress;
    private String destinationAddress;

    private String status;    //відправлена, у дорозі, доставлена, отримана ітп
    private double costDelivery;

    public Package() {
    }

    public Package(long senderId, long recipientId, String fromAddress,
                   String destinationAddress, String status, double costDelivery) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.fromAddress = fromAddress;
        this.destinationAddress = destinationAddress;
        this.status = status;
        this.costDelivery = costDelivery;
    }

    public long getId() {
        return id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCostDelivery() {
        return costDelivery;
    }

    public void setCostDelivery(double costDelivery) {
        this.costDelivery = costDelivery;
    }
}
