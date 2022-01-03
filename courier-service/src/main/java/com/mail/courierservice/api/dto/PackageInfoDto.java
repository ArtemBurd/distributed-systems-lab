package com.mail.courierservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PackageInfoDto {
    private long id;

    private String senderName;
    private String senderSurname;
    private String recipientName;
    private String recipientSurname;

    private String fromAddress;
    private String destinationAddress;
    private double costDelivery;
}
