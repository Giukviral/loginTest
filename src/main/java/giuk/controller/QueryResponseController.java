package giuk.controller;


import giuk.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryResponseController {
    private final AppUserService userService;
}
