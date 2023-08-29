package com.ergasia.spring.login.controllers;

import com.ergasia.spring.login.models.Role;
import com.ergasia.spring.login.models.User;
import com.ergasia.spring.login.repository.RoleRepository;
import com.ergasia.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ergasia.spring.login.models.ERole.ROLE_ADMIN;
import static com.ergasia.spring.login.models.ERole.ROLE_AGORASTIS;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = "/accounts_list", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAccounts() {
        return userRepository.findAllUsers();
    }

    @RequestMapping(value = "/accounts_list/account/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getAccount(@PathVariable("id") long id) {
        return userRepository.getAccount(id);
    }

    @RequestMapping(value = "/accounts_list/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody User account) {
        Long roleId = mapRoleToLong(account.getRoles().iterator().next());
        String encodedPassword = passwordEncoder().encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setConfirmPassword(encodedPassword);
        userRepository.insert(account);
        User newuser = userRepository.findByUsername(account.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", account.getUsername())));
        roleRepository.insertUserRole(newuser.getId(), roleId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/accounts_list/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateAccount(@RequestBody User account) {
        Long roleId = mapRoleToLong(account.getRoles().iterator().next());
        String encodedPassword = passwordEncoder().encode(account.getPassword());
        account.setPassword(encodedPassword);
        account.setConfirmPassword(encodedPassword);
        userRepository.updateAccount(account);
        roleRepository.updateRole(roleId, account.getId());
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/accounts_list/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") long id) {
        User toDel = userRepository.getAccount(id);
        userRepository.delete(toDel);
        roleRepository.deleteUserRole(id);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/accounts_list/find_user/{username}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getAccountBasedOnUsername(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
        return user;
    }

    @RequestMapping(value = "/accounts_list/buyers", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('ROLE_POLITIS')")
    public List<User> getBuyers() {
        return userRepository.findAllBuyers();
    }


    private Long mapRoleToLong(Role role) {
        if (role.equals(ROLE_ADMIN)) {
            return 1L;
        } else if (role.equals(ROLE_AGORASTIS)) {
            return 2L;
        } else {
            return 3L;
        }
    }

}
