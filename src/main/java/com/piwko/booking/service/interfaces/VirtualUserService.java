package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.VirtualUser;
import com.piwko.booking.util.exception.ResourceNotFoundException;

public interface VirtualUserService {

    VirtualUser getVirtualUserByUsername(String username) throws ResourceNotFoundException;

    boolean existsByUsername(String username);

    void deleteVirtualUser(VirtualUser virtualUser);
}
