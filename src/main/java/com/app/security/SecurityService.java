package com.app.security;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.models.User;
import com.app.models.UserRole;
import com.jk.db.dataaccess.orm.JKObjectDataAccess;
import com.jk.db.dataaccess.orm.JKObjectDataAccessImpl;
import com.jk.db.exceptions.JKRecordNotFoundException;
import com.jk.util.JKValidationUtil;
import com.jk.util.factory.JKFactory;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.util.security.JKPasswordUtil;
import com.jk.web.util.JKWebUtil;

public class SecurityService implements UserDetailsService {
	static JKLogger logger = JKLoggerFactory.getLogger(SecurityService.class);

	JKObjectDataAccess dataAccessService = JKFactory.instance(JKObjectDataAccessImpl.class);

	//////////////////////////////////////////////////////////////////
	public SecurityService() {
		//validate if no user configured, then add default user
		if(dataAccessService.getList(User.class).size()==0) {
			UserRole role=new UserRole();
			role.setName(UserRole.ADMIN);
			dataAccessService.insert(role);
			createAccount("admin", "admin-first-name", "admin-last-name", "admin",role);
		}
	}

	//////////////////////////////////////////////////////////////////
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User account = findAccountUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return account;
	}

	//////////////////////////////////////////////////////////////////
	public User createAccount(String username, String firstname, String lastname, String password, UserRole role) {
		logger.debug("Create account with ({},{},{})", username, firstname, lastname);
		User account = findAccountUsername(username);
		if (account == null) {
			account = User.create().username(username).firstname(firstname).lastname(lastname);
			account.setTemp(password);
			account.setRole(role);
			processNewAccount(account);
		} else {
			account.setTemp(password);
			processCurrentAccount(account);
		}
		return account;
	}

	//////////////////////////////////////////////////////////////////
	protected void processNewAccount(User account) {
		logger.debug("Process new account ({})", account.getUsername());
		String password = account.getTemp();
		account.setPassword(JKWebUtil.encodePassword(password));
		dataAccessService.insert(account);
		logger.debug("Account created ({}) with password ({})", account.getUsername(), account.getPassword());
	}

	//////////////////////////////////////////////////////////////////
	protected void processCurrentAccount(User account) {
		resetAccount(account);
	}

	//////////////////////////////////////////////////////////////////
	public User resetAccount(String username) {
		User acconut = findAccountUsername(username);
		if (acconut != null) {
			resetAccount(acconut);
			return acconut;
		} else {
			throw new JKRecordNotFoundException();
		}
	}

	//////////////////////////////////////////////////////////////////
	protected void resetAccount(User account) {
		logger.debug("Reset account ({})", account.getUsername());
		String password = account.getTemp();
		if (JKValidationUtil.isEmpty(password)) {
			password = JKPasswordUtil.generateNumricPassword(10);
			account.setTemp(password);
		}
		account.setPassword(new BCryptPasswordEncoder().encode(password));
		dataAccessService.update(account);
	}

	//////////////////////////////////////////////////////////////////
	protected User findAccountUsername(String username) {
		return dataAccessService.findOneByFieldName(User.class, "username", username);
	}

	//////////////////////////////////////////////////////////////////
	public void changePassword(String userName, String currentPassword, String newPassword) {
		User account = findAccountUsername(userName);
		Assert.assertTrue(account != null);
		Assert.assertTrue(JKWebUtil.matchPasswords(currentPassword, account.getPassword()));
		account.setPassword(JKWebUtil.encodePassword(newPassword));
		dataAccessService.update(account);
	}
}
