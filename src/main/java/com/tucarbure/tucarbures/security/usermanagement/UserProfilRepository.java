package com.tucarbure.tucarbures.security.usermanagement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilRepository extends CrudRepository<UserProfil, String> {
}
