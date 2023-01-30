package com.zpop.web.controller.admin;

import com.zpop.web.dao.AdminDao;
import com.zpop.web.dto.admin.auth.AdminLoginDto;
import com.zpop.web.dto.admin.auth.AdminResponse;
import com.zpop.web.entity.Admin;
import com.zpop.web.security.UserRole;
import com.zpop.web.security.UserSecurityService;
import com.zpop.web.security.ZpopUserDetails;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/admin/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserSecurityService userSecurityService;

    @PostMapping("/login")
    public ResponseEntity<AdminResponse> login(@Valid @RequestBody AdminLoginDto dto, HttpSession session){
        UserDetails user = userSecurityService.loadWithAdmin(dto.getName(), dto.getPwd());

        System.out.println(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        ZpopUserDetails admin = (ZpopUserDetails) user;

        return ResponseEntity.ok(new AdminResponse(admin.getId(), admin.getUsername()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal ZpopUserDetails userDetails){
        Map<String, Object> result = new HashMap<>();

        if(userDetails == null){
            result.put("code", "NOT_AUTHENTICATED");
            return ResponseEntity.ok(result);
        }

        if(!userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(UserRole.ADMIN))){
            result.put("code", "NOT_AUTHENTICATED");
            return ResponseEntity.ok(result);
        }

        result.put("code", "AUTHENTICATED");
        result.put("id", userDetails.getId());
        result.put("nickname", userDetails.getUsername());
        result.put("profileImagePath", userDetails.getProfileImagePath());

        return ResponseEntity.ok(result);
    }

//    @PostMapping("/register")
//    public void register() {
        //     개발시에만 사용할것!!
//        userSecurityService.createAdmin("1", "1);
//        return;
//    }
}
