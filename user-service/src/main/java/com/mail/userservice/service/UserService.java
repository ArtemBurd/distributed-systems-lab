package com.mail.userservice.service;

import com.mail.userservice.repo.UserRepo;
import com.mail.userservice.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {
    private final UserRepo userRepo;

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User getById(long id) throws IllegalArgumentException {
        Optional<User> maybeUser = userRepo.findById(id);
        if(maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public User getByPhone(String phone) throws IllegalArgumentException {
        Optional<User> maybeUser = userRepo.findByPhone(phone);
        if(maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public long create(User newUser){
        final User savedUser = userRepo.save(newUser);
        return savedUser.getId();
    }

    public void update(long id, User newUser){
        String name = newUser.getName();
        String surname = newUser.getSurname();
        String phone = newUser.getPhone();
        String password = newUser.getPassword();

        Optional<User> maybeUser = userRepo.findById(id);
        if(maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        final User oldUser = maybeUser.get();

        if (name != null && !name.isBlank()) oldUser.setName(name);
        if (surname != null && !surname.isBlank()) oldUser.setSurname(surname);
        if (phone != null && !phone.isBlank()) oldUser.setPhone(phone);
        if (password != null && !password.isBlank()) oldUser.setPassword(password);

        userRepo.save(oldUser);
    }

    public void delete(long id){
        userRepo.deleteById(id);
    }
}
