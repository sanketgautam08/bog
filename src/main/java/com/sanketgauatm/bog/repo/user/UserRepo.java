package com.sanketgauatm.bog.repo.user;

import com.sanketgauatm.bog.model.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepo extends ListCrudRepository<User, Integer> {
}
