package com.mail.courierservice.api;

import com.mail.courierservice.api.dto.PackageInfoDto;
import com.mail.courierservice.repo.model.CourierOrder;
import com.mail.courierservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courier-orders")
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public ResponseEntity<List<CourierOrder>> index(){
        final List<CourierOrder> orders = courierService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierOrder> show(@PathVariable long id){
        try {
            final CourierOrder order = courierService.getById(id);
            return ResponseEntity.ok(order);
        } catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/package-info/{orderId}")
    public ResponseEntity<PackageInfoDto> showPackageInfo(@PathVariable long orderId){
        try{
            final PackageInfoDto packInfo = courierService.getInfoAboutPackage(orderId);
            return ResponseEntity.ok(packInfo);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CourierOrder newOrder){
        final long id = courierService.create(newOrder);
        String location = String.format("/courier-orders/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody CourierOrder newOrder){
        try{
            courierService.update(id, newOrder);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        courierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
