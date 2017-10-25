package com.openkx.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.openkx.repository.NavDao;

@Component
public class NavService {

	
	@Resource
	private NavDao navDao;
	
}
