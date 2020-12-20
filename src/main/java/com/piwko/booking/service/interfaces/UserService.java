package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.UserSearchCriteria;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUser(Long id) throws ResourceNotFoundException;

    User getUser(String username) throws ResourceNotFoundException;

    Page<User> getUsers(UserSearchCriteria searchCriteria, Pageable pageable);

    Long createUser(User user) throws ResourceNotUniqueException, ResourceNotFoundException;

    void updateUser(User user, String username) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteUser(Long id) throws ResourceNotFoundException;

    void enableUser(Long id) throws ResourceNotFoundException;
}
