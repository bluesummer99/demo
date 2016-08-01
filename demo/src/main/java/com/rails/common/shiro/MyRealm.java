package com.rails.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.rails.base.security.domain.User;
import com.rails.base.security.service.UserService;
import com.rails.common.constants.BaseConstants;

//@Component
public class MyRealm extends AuthorizingRealm {// implements
												// ApplicationContextAware {

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		AuthenticationInfo authcInfo = null;
		String username = usernamePasswordToken.getUsername();
		String password = new String(usernamePasswordToken.getPassword());
		if (!StringUtils.isBlank(username)) {
			User staff = userService.findByLoginName(username);
			if (staff != null) {
				authcInfo = new SimpleAuthenticationInfo(staff.getLoginName(), password, "" + staff.getId());
				setSession(staff);
			}else{
				
			}
		} else {

		}
		return authcInfo;
	}

	private void setSession(User staff) {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			Session session = subject.getSession();
			if (null != session) {
				session.setAttribute(BaseConstants.USER_SESSION_KEY, staff);
			}
		}
	}

}
