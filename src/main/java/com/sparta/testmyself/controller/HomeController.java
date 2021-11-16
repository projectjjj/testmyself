package com.sparta.testmyself.controller;

import com.sparta.testmyself.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//이 홈 컨트롤러는 메인 서버사이드 렌더링의 username때문에 만들어주는거임.
@Controller
public class HomeController {
    @GetMapping("/")
    //@AuthenticationPrincipal은 뭘까? 어쨋거나 정보를 가져오는건가....ㅠㅠㅠ..
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username",userDetails.getUsername());
        return "index";
    }
}
