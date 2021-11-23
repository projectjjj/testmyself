package com.sparta.testmyself.service;

import com.sparta.testmyself.dto.UserDto;
import com.sparta.testmyself.kakao.KakaoOAuth2;
import com.sparta.testmyself.kakao.KakaoUserInfo;
import com.sparta.testmyself.model.User;
import com.sparta.testmyself.model.UserRole;
import com.sparta.testmyself.repository.UserRepository;
import com.sparta.testmyself.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;

    public User registerUser(UserDto userDto) {
        String username = userDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 ID가 있습니다");
        }
        String password = passwordEncoder.encode(userDto.getPassword());
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

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
//        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
//        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            //카카오 이멜과 동일한 이멜이 있는지 확인
            User sameEmailUser = userRepository.findByEmail(email).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                //카카오 이멜과 동일한 이멜이 있네
                //카카오 Id를 회원정보에 저장
                kakaoUser.setKakaoId(kakaoId);
                userRepository.save(kakaoUser);
            } else {
                //카카오 이멜과 동일한 이멜이 없으면 카카오 회원가입 진행
                //유저네임은 kakao 닉네임이다
                String username = nickname;
                //비번은 어차피 모르고, 비번으로 들어오는 것이 아니기 때문에 유니크한 값을 넣어줌
                String password = kakaoId + ADMIN_TOKEN;
                // 패스워드 인코딩
                String encodedPassword = passwordEncoder.encode(password);
                // ROLE = 사용자
                UserRole role = UserRole.USER;
                kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
                userRepository.save(kakaoUser);
            }
        }

        // 로그인 처리
//        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        //강제 로그인 처리
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
