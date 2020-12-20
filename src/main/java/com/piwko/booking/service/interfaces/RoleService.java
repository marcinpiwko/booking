package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Role;
import com.piwko.booking.util.exception.ResourceNotFoundException;

public interface RoleService {

    Role getRole(Role.RoleType type) throws ResourceNotFoundException;
}
