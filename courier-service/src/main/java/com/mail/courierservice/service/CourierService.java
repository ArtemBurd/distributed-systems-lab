package com.mail.courierservice.service;

import com.mail.courierservice.api.dto.PackageDto;
import com.mail.courierservice.api.dto.PackageInfoDto;
import com.mail.courierservice.api.dto.UserDto;
import com.mail.courierservice.repo.CourierOrderRepo;
import com.mail.courierservice.repo.model.CourierOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourierService {
    private final CourierOrderRepo orderRepo;

    public List<CourierOrder> getAll(){
        return orderRepo.findAll();
    }

    public CourierOrder getById(long id) throws IllegalArgumentException {
        Optional<CourierOrder> maybeOrder = orderRepo.findById(id);
        if(maybeOrder.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeOrder.get();
    }

    public PackageInfoDto getInfoAboutPackage(long orderId) {
        try {
            long packageId = getById(orderId).getPackageId();
            RestTemplate restTemplate = new RestTemplate();
            String request = "http://package-service:8080/packages/" + packageId;
            PackageDto pack = restTemplate.getForEntity(request, PackageDto.class).getBody();

            long senderId = pack.getSenderId();
            long recipientId = pack.getRecipientId();

            String requestSender = "http://user-service:8081/users/" + senderId;
            String requestRecipient = "http://user-service:8081/users/" + recipientId;
            UserDto sender = restTemplate.getForEntity(requestSender, UserDto.class).getBody();
            UserDto recipient = restTemplate.getForEntity(requestRecipient, UserDto.class).getBody();

            return new PackageInfoDto(pack.getId(), sender.getName(), sender.getSurname(), recipient.getName(),
                    recipient.getSurname(), pack.getFromAddress(), pack.getDestinationAddress(), pack.getCostDelivery());
        } catch (Exception e) { throw new IllegalArgumentException(); }
    }

    public long create(CourierOrder newOrder){
        RestTemplate restTemplate = new RestTemplate();
        try{
            String request = "http://package-service:8080/packages/" + newOrder.getPackageId();
            String temp = restTemplate.getForEntity(request, String.class).getBody();
        } catch (Exception e) { throw new IllegalArgumentException(); }

        final CourierOrder savedOrder = orderRepo.save(newOrder);
        return savedOrder.getId();
    }

    public void update(long id, CourierOrder newOrder){
        long packageId = newOrder.getPackageId();
        String storage = newOrder.getStorage();
        String orderStatus = newOrder.getOrderStatus();

        Optional<CourierOrder> maybeOrder = orderRepo.findById(id);
        if(maybeOrder.isEmpty()) throw new IllegalArgumentException("User not found");
        final CourierOrder oldOrder = maybeOrder.get();

        if (packageId != 0) oldOrder.setPackageId(packageId);
        if (storage != null && !storage.isBlank()) oldOrder.setStorage(storage);
        if (orderStatus != null && !orderStatus.isBlank()) oldOrder.setOrderStatus(orderStatus);

        orderRepo.save(oldOrder);
    }

    public void delete(long id){
        orderRepo.deleteById(id);
    }
}
