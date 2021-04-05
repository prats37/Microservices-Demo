package com.wipro.gatewayservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class PostFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("---Route is using POST filter---");
		return null;
	}

	@Override
	public String filterType() {
		return "Post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
