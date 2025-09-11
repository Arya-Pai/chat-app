package com.project.repo;
import java.util.List;

import com.project.model.User;

public interface SearchUserRepo {
	List<User> findAllUser(String text);
}
