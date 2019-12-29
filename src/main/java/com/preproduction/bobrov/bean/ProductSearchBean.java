package com.preproduction.bobrov.bean;

import java.math.BigDecimal;
import java.util.List;
/**
 * Class represents entity that stores data from product search form fields
 */
public class ProductSearchBean {

	private String name;

	private BigDecimal priceFrom;

	private BigDecimal priceTo;

	private List<Integer> categories;

	private List<Integer> manufacturers;

	private String sortedBy;

	private boolean isDescending;

	private Integer offset;
	
	private Integer limit;

	private Integer page;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public List<Integer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<Integer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public String getSortedBy() {
		return sortedBy;
	}

	public void setSortedBy(String sortedBy) {
		this.sortedBy = sortedBy;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public boolean isDescending() {
		return isDescending;
	}

	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}	

}
