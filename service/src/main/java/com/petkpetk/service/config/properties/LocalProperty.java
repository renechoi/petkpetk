package com.petkpetk.service.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Component
@Getter
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-dev.yml")
public class LocalProperty {

	@Value("${itemImageLocation}")
	private String itemLocalStorage;

	public static LocalProperty getInstance() {
		return ApplicationContextProvider.getApplicationContext().getBean(LocalProperty.class);
	}
}




