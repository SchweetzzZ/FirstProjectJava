package com.firstContact.projetoUm.services;

import com.firstContact.projetoUm.entity.User;
import com.firstContact.projetoUm.repositories.UserRepository;
import com.firstContact.projetoUm.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException((id)));
    }

    public User inser(User obj){
        return repository.save(obj);
    }

    public void delet(Long id){
        try {
            repository.deleteById(id);
        } catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public User update(Long id, User obj) {
        User entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
    }
    public User insert(User obj){
        String encryptedPassword = passwordEncoder.encode(obj.getPassword());
        obj.setPassword(encryptedPassword);
        return repository.save(obj);
    }
}
