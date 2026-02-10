package com.projedata.autoflex.inventory.mappers;

import com.projedata.autoflex.inventory.dtos.auth.UserSummaryResponse;
import com.projedata.autoflex.inventory.entities.User;

public class UserMapper {

    public UserSummaryResponse toSummary(User user) {
        if (user == null) return null;

        return new UserSummaryResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
