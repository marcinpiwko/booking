package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Role;
import com.piwko.booking.persistence.repository.RoleRepository;
import com.piwko.booking.service.interfaces.RoleService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {

    private final RoleRepository roleRepository;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Role.class);

    @Override
    public Role getRole(Role.RoleType type) throws ResourceNotFoundException {
        return roleRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "type", type.name()));
    }
}
