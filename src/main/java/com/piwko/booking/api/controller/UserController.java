package com.piwko.booking.api.controller;


import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.patch.PatchUserForm;
import com.piwko.booking.api.form.post.PostUserForm;
import com.piwko.booking.api.form.get.GetUserForm;
import com.piwko.booking.api.form.get.GetUsersForm;
import com.piwko.booking.api.security.JwtUtil;
import com.piwko.booking.api.swagger.UserApi;
import com.piwko.booking.api.translator.UserTranslator;
import com.piwko.booking.persistence.search.UserSearchCriteria;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    private final UserTranslator userTranslator;

    private final JwtUtil jwtUtil;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetUsersForm> getUsers(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<Boolean> enabled, Optional<String> companyCode) {
        log.info("GET /api/users");
        return new ResponseEntity<>(new GetUsersForm(userService.getUsers(new UserSearchCriteria(enabled.orElse(null), companyCode.orElse(null)),
                                PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                                        .map(userTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetUserForm> getUser(Long id) throws ResourceNotFoundException {
        log.info("GET /api/users/" + id);
        return new ResponseEntity<>(userTranslator.translate(userService.getUser(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetUserForm> getMe() throws ResourceNotFoundException {
        log.info("GET /api/users/me");
        return new ResponseEntity<>(userTranslator.translate(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createUser(@Valid PostUserForm postUserForm) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("POST /api/users");
        userService.createUser(userTranslator.translate(postUserForm));
        if (postUserForm.getCompany() != null) {
            return new ResponseEntity<>("Your company account has been registered. Please wait for the activation!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Your account has been registered. You can login now!", HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Void> updateMe(@Valid PatchUserForm patchUserForm, String token) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("PATCH /api/users/me");
        userService.updateUser(userTranslator.translate(patchUserForm), SecurityContextHolder.getContext().getAuthentication().getName());
        if (!StringUtil.isEmpty(patchUserForm.getEmail()) || !StringUtil.isEmpty(patchUserForm.getPassword())) {
            jwtUtil.invalidateToken(token);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtUtil.getTokenPrefix() + jwtUtil.generateToken(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()), false));
            return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<GetUsersForm> enableUser(Long id) throws ResourceNotFoundException {
        log.info("POST /api/users/" + id + "/enable");
        userService.enableUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
