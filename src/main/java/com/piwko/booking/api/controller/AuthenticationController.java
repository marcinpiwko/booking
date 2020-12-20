package com.piwko.booking.api.controller;

import com.piwko.booking.api.form.common.authentication.AuthenticationRequest;
import com.piwko.booking.api.form.common.authentication.AuthenticationResponse;
import com.piwko.booking.api.security.JwtUtil;
import com.piwko.booking.api.swagger.AuthenticationApi;
import com.piwko.booking.api.translator.UserTranslator;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserTranslator userTranslator;

    private final UserService userService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(@Valid AuthenticationRequest loginRequest, Optional<String> token) throws UnauthorizedException, ResourceNotFoundException {
        log.info("POST /api/login");
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        User userDetails = userService.getUser(loginRequest.getEmail());
        token.ifPresent(jwtUtil::invalidateToken);
        return new ResponseEntity<>(new AuthenticationResponse(jwtUtil.generateToken(userDetails, loginRequest.isRememberMe()), userTranslator.translate(userDetails)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> logout(String token) {
        log.info("DELETE /api/logout");
        jwtUtil.invalidateToken(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void authenticate(String username, String password) throws UnauthorizedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(username);
        }
    }
}
