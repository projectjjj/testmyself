package com.sparta.testmyself.service;

import com.sparta.testmyself.dto.UserDto;
import com.sparta.testmyself.model.User;
import com.sparta.testmyself.model.UserRole;
import com.sparta.testmyself.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;

    public User registerUser(UserDto userDto) {
        String username = userDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 ID가 있습니다");
        }
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        UserRole role = UserRole.USER;
        if(userDto.isAdmin()) {
            if(!userDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("암호가 틀려 관리자 등록에 실패하였습니다");
            }
            role = UserRole.ADMIN;
        }
        User user = new User(username,password,email,role);
        userRepository.save(user);

        return user;
    }

}
