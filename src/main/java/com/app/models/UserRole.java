package com.app.models;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "sec_roles")
public class UserRole implements GrantedAuthority {

	public static final String ADMIN = "admin";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "name")
	String name;

	@Override
	public String getAuthority() {
		return getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}