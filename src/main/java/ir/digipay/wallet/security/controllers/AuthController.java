package ir.digipay.wallet.security.controllers;


import ir.digipay.wallet.models.user.entity.UserEntity;
import ir.digipay.wallet.security.models.ERole;
import ir.digipay.wallet.security.models.Role;
import ir.digipay.wallet.security.models.Users;
import ir.digipay.wallet.security.payload.request.LoginRequest;
import ir.digipay.wallet.security.payload.request.SignupRequest;
import ir.digipay.wallet.security.payload.response.JwtResponse;
import ir.digipay.wallet.security.payload.response.MessageResponse;
import ir.digipay.wallet.security.repository.RoleRepository;
import ir.digipay.wallet.security.repository.UsersRepository;
import ir.digipay.wallet.security.security.jwt.JwtUtils;
import ir.digipay.wallet.security.security.services.UserDetailsImpl;
import ir.digipay.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpRequest.getUsername());
        userEntity.setFirstName(signUpRequest.getFirstName());
        userEntity.setLastName(signUpRequest.getLastName());



        if (usersRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (usersRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Users users = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        users.setRoles(roles);
        usersRepository.save(users);
        userService.saveUser(userEntity);

        return ResponseEntity.ok(new

                MessageResponse("User registered successfully!"));
    }
}
