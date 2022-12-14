package com.minde.authorizationserver.repositories.auth;

import com.minde.authorizationserver.models.auth.TbdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbdUserRepository extends JpaRepository<TbdUser, Long> {
    Optional<TbdUser> findByUsername(String userName);
}
