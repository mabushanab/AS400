package com.app.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.jk.web.controllers.*;
import com.app.models.UserRole;

@ManagedBean(name = "mbRoles")
@ViewScoped
public class MB_Roles extends JKManagedBeanWithOrmSupport<UserRole> {

}
