package com.tucarbure.tucarbures.releves.code;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeCarburantRepository extends CrudRepository<CodeCarburantsDB, String> {
}
