package com.lingxi.framework.domain;

import java.util.List;

public class Page<T> {

	private org.springframework.data.domain.Page<T> page;

	public Page(org.springframework.data.domain.Page<T> page) {
		this.page = page;
	}

	public long getTotal() {
		return page.getTotalElements();
	}

//	public int getTotalPages() {
//		return page.getTotalPages();
//	}

	public List<T> getResultList() {
		return page.getContent();
	}
}
