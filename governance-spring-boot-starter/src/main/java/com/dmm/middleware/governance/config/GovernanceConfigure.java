package com.dmm.middleware.governance.config;

import com.dmm.middleware.governance.aop.DoMethodExtPoint;
import com.dmm.middleware.governance.aop.GovernancePoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mean
 * @date 2025/2/17 18:18
 * @description GovernanceConfigure
 */
@Configuration
public class GovernanceConfigure {

	@Bean
	@ConditionalOnMissingBean
	public DoMethodExtPoint point() {
		return new DoMethodExtPoint();
	}

}
