package giuk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller 로는 request mapping이 잘 안되는데...
@RequiredArgsConstructor
public class ViewConfig{
    @RequestMapping("/p1")
    public String admin(){
        System.out.println("p1 called");
        return "admin";
    }
    @RequestMapping("/p2")
    public String denied(){
        return "/denied";
    }
    @RequestMapping("/p3")
    public String login(){
        return "/login";
    }
    @RequestMapping("/p4")
    public String member(){
        return "/member";
    }
    @RequestMapping("/p5")
    public String signup(){
        return "/signup";
    }
}
