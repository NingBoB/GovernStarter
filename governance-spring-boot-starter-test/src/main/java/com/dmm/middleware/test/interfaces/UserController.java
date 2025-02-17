package com.dmm.middleware.test.interfaces;

import com.dmm.middleware.governance.annotation.DoMethodExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mean
 * @date 2025/2/14 19:30
 * @description UserController
 */
@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 测试：http://localhost:8081/api/queryUserInfo?userId=aaa
	 */
	@DoMethodExt(method = "blacklist", returnJson = "{\"code\":\"1111\",\"info\":\"自定义校验方法拦截，不允许访问！\"}")
	// @RequestMapping(path = "/api/queryUserInfo", method = RequestMethod.GET)
	@GetMapping("/api/queryUserInfo")
	public UserInfo queryUserInfo(@RequestParam String userId) throws InterruptedException {
		logger.info("查询用户信息，userId：{}", userId);
		Thread.sleep(1000);
		return new UserInfo("虫虫:" + userId, 19, "天津市东丽区万科赏溪苑14-0000");
	}

	/**
	 * 自定义黑名单，拦截方法
	 */
	public boolean blacklist(@RequestParam String userId) {
		if ("bbb".equals(userId) || "222".equals(userId)) {
			logger.info("拦截自定义黑名单用户 userId：{}", userId);
			return false;
		}
		return true;
	}

}
