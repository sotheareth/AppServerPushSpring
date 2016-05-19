package com.mcnc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.domain.User;

/**
 * Handles CRUD services for users
 * 
 */
@Service("userService")
@Transactional
public class UserService implements IUserService {
	private List<User> dummyUsersList = new ArrayList<User>();

	protected static Logger logger = LoggerFactory.getLogger("service");

	public UserService() {
		// Initialize our in-memory list of users
		init();
	}

	public List<User> getAll() {
		logger.debug("Retrieving all users");

		return dummyUsersList;
	}

	public User get(String id) {
		logger.debug("Retrieving an existing user");

		return dummyUsersList.get(Integer.valueOf(id));

	}

	public Boolean add(User user) {
		logger.debug("Adding a new user");

		try {
			// Assign a new id
			user.setId(Long.valueOf(dummyUsersList.size()));

			dummyUsersList.add(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean delete(User user) {
		logger.debug("Deleting an existing user");

		try {
			// Retrieve id to delete
			Long id = Long.valueOf(user.getId().toString());

			// Loop array
			for (User dummyUser : dummyUsersList) {
				if (dummyUser.getId().compareTo(id) == 0) {
					dummyUsersList.remove(dummyUser);
					break;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Boolean edit(User user) {
		logger.debug("Editing an existing user");

		try {
			// Retrieve id to edit
			Long id = Long.valueOf(user.getId().toString());

			// Loop array
			for (User dummyUser : dummyUsersList) {
				if (dummyUser.getId().compareTo(id) == 0) {
					dummyUser.setFirstName(user.getFirstName());
					dummyUser.setLastName(user.getLastName());
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private void init() {
		// Populate our in-memory, dummy list of users
		// Normally, the data should come from your DAOs or your persitence
		// layer

		logger.debug("Init in-memory users");

		User user = new User();
		user.setId(Long.valueOf("1"));
		user.setFirstName("John");
		user.setLastName("Smith");
		dummyUsersList.add(user);

		user = new User();
		user.setId(Long.valueOf("2"));
		user.setFirstName("Jane");
		user.setLastName("Adams");
		dummyUsersList.add(user);

		user = new User();
		user.setId(Long.valueOf("3"));
		user.setFirstName("Jeff");
		user.setLastName("Mayer");
		dummyUsersList.add(user);
	}
}