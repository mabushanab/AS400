/*
 * Copyright 2002-2018 Jalal Kiswani. 
 * E-mail: Kiswani.Jalal@Gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.controllers;

import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.app.models.User;
import com.app.models.UserRole;
import com.app.security.SecurityService;
import com.jk.web.controllers.JKManagedBeanWithOrmSupport;
import com.jk.web.faces.mb.JKManagedBean;

/**
 * The Class MB_Login.
 */
@ManagedBean(name = "mbSecurity")
@RequestScoped
public class MB_Security extends JKManagedBean {

	private User user;

	String currentPassword;
	String newPassword;

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */

	public String getMessage() {
		Object attribute = session().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (attribute == null) {
			return null;
		}
//		session().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (attribute.toString().contains("BadCredentialsException")) {
			return "Invalid username or password";
		}

		return attribute.toString();
	}

	/**
	 * Return account object for current logged in user
	 * 
	 * @return
	 */
	public User getUser() {
		if (isUserLoggedIn()) {
			if (this.user == null) {
				this.user = (User) getService().loadUserByUsername(getUserName());
			}
			return user;
		}
		return null;
	}

	protected SecurityService getService() {
		return new SecurityService();
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String changePassword() {
		try {
			getService().changePassword(getUserName(), currentPassword, newPassword);
			return "dashboard";
		} catch (AssertionError e) {
			error("Unable to change password, please check your current password");
			return null;
		}
	}
	
	public boolean isAdmin() {
		UserRole role = getRole();
		if(role!=null) {
			return role.getName().equals(UserRole.ADMIN);
		}
		return false;
	}

	private UserRole getRole() {
		User user = getUser();
		if(user!=null) {
			return user.getRole();
		}
		return null;
	}
}
