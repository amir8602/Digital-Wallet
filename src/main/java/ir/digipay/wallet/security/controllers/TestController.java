package ir.digipay.wallet.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    AuthenticationManager authenticationManager;
    
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(@Autowired Authentication authentication , @Autowired Principal principal) {


        return principal.getName().toString() + authentication.getAuthorities();

//        Authentication authenticate = authenticationManager.authenticate();
//
//        System.out.println(authentication.getDetails());
//        System.out.println(authentication.getCredentials());
//        System.out.println(authentication.getPrincipal());
//
//        return "User Content.\n" + authentication.getPrincipal() + "\n" + authentication.getCredentials() +"\n" + authentication.getAuthorities() + "\n" + authentication.getCredentials();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(@Autowired Authentication authentication) {
        return "Admin Board.\n" + authentication.getPrincipal() + "\n" + authentication.getCredentials();
    }
}
