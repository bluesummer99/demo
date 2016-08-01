package com.rails.base.security.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.rails.base.security.domain.User;
import com.rails.base.security.service.UserService;
import com.rails.common.constants.BaseConstants;
import com.rails.common.domain.WebResult;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
public class LoginoutController {

	private Logger logger = LoggerFactory.getLogger(LoginoutController.class);
	@Autowired
	private Producer captchaProducer;
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("/login");
		return model;
	}
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
		model.setViewName("/main");
		User user = (User) request.getSession().getAttribute(BaseConstants.USER_SESSION_KEY);
		model.addObject("user", user);
		return model;
	}
	

	@RequestMapping("/login/action")
	@ResponseBody
	public WebResult loginAction(HttpServletRequest request, String name, String password, String captcha) {
		WebResult wr = new WebResult();
		try {
//			if (captcha == null || !captcha.equalsIgnoreCase((String) request.getSession().getAttribute(BaseConstants.KAPTCHA_SESSION_KEY))) {
//				wr.setMessage("验证码不正确");
//				wr.setStatus(WebResult.ERROR);
//				return wr;
//			}

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			subject.login(token);
		} catch (Exception e) {
			wr.setStatus(WebResult.ERROR);
			wr.setException(e);
			wr.setMessage("登录失败");
		}
		return wr;
	}

	@SuppressWarnings("restriction")
	@RequestMapping("/kaptcha/doGet")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(BaseConstants.KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bi);
		} catch (Exception e) {
			logger.error(this.getClass().toString(), e.getMessage());
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
			}
		}
		System.out.println("Captchca:" + request.getSession().getAttribute(BaseConstants.KAPTCHA_SESSION_KEY));
	}
}
