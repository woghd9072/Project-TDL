package com.jaehong.service;

import com.jaehong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    public Boolean confirmId(String id) {
        if (userRepository.findById(id) == null)
            return false;
        return id.equals(userRepository.findById(id).getId());
    }
}
