package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Role;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.model.VirtualUser;
import com.piwko.booking.persistence.repository.UserRepository;
import com.piwko.booking.persistence.search.UserSearchCriteria;
import com.piwko.booking.service.interfaces.*;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final VirtualUserService virtualUserService;

    private final ReservationService reservationService;

    private final CompanyService companyService;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(User.class);

    @Override
    public User getUser(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        Hibernate.initialize(user.getCompany());
        return user;
    }

    @Override
    public User getUser(String username) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "username", username));
        Hibernate.initialize(user.getCompany());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public Page<User> getUsers(UserSearchCriteria searchCriteria, Pageable pageable) {
        Page<User> users;
        if (!StringUtil.isEmpty(searchCriteria.getCompanyCode())) {
            if (searchCriteria.getEnabled() != null) {
                users = userRepository.findAllByEnabledAndCompanyCode(searchCriteria.getEnabled(), searchCriteria.getCompanyCode(), pageable);
            } else {
                users = userRepository.findAllByCompanyCode(searchCriteria.getCompanyCode(), pageable);
            }
        } else {
            if (searchCriteria.getEnabled() != null) {
                users = userRepository.findAllByEnabled(searchCriteria.getEnabled(), pageable);
            } else {
                users = userRepository.findAll(pageable);
            }
        }
        users.forEach(u -> Hibernate.initialize(u.getCompany()));
        return users;
    }

    @Override
    public Long createUser(User user) throws ResourceNotUniqueException, ResourceNotFoundException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "email", user.getEmail());
        }
        if (user.isCompanyUser()) {
            user.setRole(roleService.getRole(Role.RoleType.COMPANY_USER));
            user.setEnabled(false);
            companyService.createCompany(user.getCompany());
        } else {
            user.setRole(roleService.getRole(Role.RoleType.USER));
            user.setEnabled(true);
        }
        Long id = userRepository.save(user).getId();
        if (virtualUserService.existsByUsername(user.getEmail())) {
            VirtualUser virtualUser = virtualUserService.getVirtualUserByUsername(user.getEmail());
            reservationService.mergeVirtualReservations(virtualUser, user);
            virtualUserService.deleteVirtualUser(virtualUser);
        }
        return id;
    }

    @Override
    public void updateUser(User user, String username) throws ResourceNotFoundException, ResourceNotUniqueException {
        User existingUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "username", username));
        if (!StringUtil.isEmpty(user.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new ResourceNotUniqueException(ENTITY_NAME, "email", user.getEmail());
            }
            existingUser.setEmail(user.getEmail());
        }
        if (!StringUtil.isEmpty(user.getPassword())) {
            existingUser.setPassword(user.getPassword());
        }
        if (!StringUtil.isEmpty(user.getPhoneNumber())) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        userRepository.softDeleteById(id);
    }

    @Override
    public void enableUser(Long id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        userRepository.enableUser(id);
    }
}
