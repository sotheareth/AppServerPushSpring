package com.mcnc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles CRUD requests for users
 * 
 */
@Controller
@RequestMapping("/main")
public class MediatorController {

	protected static Logger logger = LoggerFactory.getLogger(MediatorController.class);

	/**
	 * Retrieves the JSP page that contains our JqGrid
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsersPage() {
		logger.debug("Received request to show users page");

		// This will resolve to /WEB-INF/jsp/users.jsp page
		return "users";
	}
}
