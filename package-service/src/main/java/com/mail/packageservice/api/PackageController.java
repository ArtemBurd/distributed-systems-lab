package com.mail.packageservice.api;

import com.mail.packageservice.api.dto.PackageDto;
import com.mail.packageservice.repo.model.Package;
import com.mail.packageservice.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/packages")
public final class PackageController {

    private final PackageService packageService;

    @GetMapping
    public ResponseEntity<List<Package>> index(){
        final List<Package> packages = packageService.getAll();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> show(@PathVariable long id){
        try {
            final Package pack = packageService.getById(id);
            return ResponseEntity.ok(pack);
        } catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PackageDto newPackage){
        try {
            final long id = packageService.create(newPackage);
            String location = String.format("/packages/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody PackageDto newPackage){
        try{
            packageService.update(id, newPackage);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        packageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
