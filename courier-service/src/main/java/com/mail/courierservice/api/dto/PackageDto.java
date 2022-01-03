package com.mail.courierservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackageDto {
    private long id;

    private long senderId;
    private long recipientId;

    private String fromAddress;
    private String destinationAddress;
    private double costDelivery;
}
