package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.VirtualUser;
import com.piwko.booking.persistence.repository.VirtualUserRepository;
import com.piwko.booking.service.interfaces.VirtualUserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VirtualUserManager implements VirtualUserService {

    private final VirtualUserRepository virtualUserRepository;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(VirtualUser.class);

    @Override
    public VirtualUser getVirtualUserByUsername(String username) throws ResourceNotFoundException {
        return virtualUserRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "username", username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return virtualUserRepository.existsByEmail(username);
    }

    @Override
    public void deleteVirtualUser(VirtualUser virtualUser) {
        virtualUserRepository.delete(virtualUser);
    }
}
