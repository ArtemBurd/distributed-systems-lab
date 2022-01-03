package com.mail.packageservice.service;

import com.mail.packageservice.api.dto.PackageDto;
import com.mail.packageservice.api.dto.UserDto;
import com.mail.packageservice.repo.PackageRepo;
import com.mail.packageservice.repo.model.Package;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PackageService {
    private final PackageRepo packageRepo;

    public List<Package> getAll(){
        return packageRepo.findAll();
    }

    public Package getById(long id) throws IllegalArgumentException {
        Optional<Package> maybePackage = packageRepo.findById(id);
        if(maybePackage.isEmpty()) throw new IllegalArgumentException("Package not found");
        else return maybePackage.get();
    }

    public long create(PackageDto newPackage){
        String recipientPhone = newPackage.getRecipientPhone();
        String senderPhone = newPackage.getSenderPhone();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String request1 = "http://user-service:8081/users/phone/" + senderPhone;
            long senderId = restTemplate.getForEntity(request1, UserDto.class).getBody().getId();
            String request2 = "http://user-service:8081/users/phone/" + recipientPhone;
            long recipientId = restTemplate.getForEntity(request2, UserDto.class).getBody().getId();
            String status = newPackage.getStatus();
            if (status == null || status.isBlank()) status = "sent";

            Package pack = new Package(senderId, recipientId, newPackage.getFromAddress(),
                    newPackage.getDestinationAddress(), status, newPackage.getCostDelivery());
            final Package savedPackage = packageRepo.save(pack);
            return savedPackage.getId();
        } catch (Exception e) { throw new IllegalArgumentException(); }
    }


    public void update(long id, PackageDto newPackage) throws IllegalArgumentException {
        String senderPhone = newPackage.getSenderPhone();
        String recipientPhone = newPackage.getRecipientPhone();
        long senderId = 0;
        long recipientId = 0;
        RestTemplate restTemplate = new RestTemplate();
        try{
            if (senderPhone != null && !senderPhone.isBlank()) {
                String request1 = "http://user-service:8081/users/phone/" + senderPhone;
                senderId = restTemplate.getForEntity(request1, UserDto.class).getBody().getId();
            }
            if (recipientPhone != null && !recipientPhone.isBlank()) {
                String request2 = "http://user-service:8081/users/phone/" + recipientPhone;
                recipientId = restTemplate.getForEntity(request2, UserDto.class).getBody().getId();
            }
        } catch (Exception e) { throw new IllegalArgumentException("New parameters are invalid"); }

        String fromAddress = newPackage.getFromAddress();
        String destinationAddress = newPackage.getDestinationAddress();
        String status = newPackage.getStatus();
        double costDelivery = newPackage.getCostDelivery();

        Optional<Package> maybePackage = packageRepo.findById(id);
        if (maybePackage.isEmpty()) throw new IllegalArgumentException("Package not found");

        final Package pack = maybePackage.get();

        if(senderId != 0) pack.setSenderId(senderId);
        if(recipientId != 0) pack.setRecipientId(recipientId);
        if(fromAddress != null && !fromAddress.isBlank()) pack.setFromAddress(fromAddress);
        if(destinationAddress != null && !destinationAddress.isBlank()) pack.setDestinationAddress(destinationAddress);
        if(status != null && !status.isBlank()) pack.setStatus(status);
        if(costDelivery != 0.0) pack.setCostDelivery(costDelivery);
        packageRepo.save(pack);
    }
    public void delete(long id){
        packageRepo.deleteById(id);
    }
}
