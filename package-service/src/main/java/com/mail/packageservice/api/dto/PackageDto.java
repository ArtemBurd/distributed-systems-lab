package com.mail.packageservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class PackageDto {
    private String senderPhone;
    private String recipientPhone;
    private String fromAddress;
    private String destinationAddress;

    private String status;
    private double costDelivery;
}
