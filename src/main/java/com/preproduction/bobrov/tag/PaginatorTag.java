package com.preproduction.bobrov.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.ProductSearchBean;
import com.preproduction.bobrov.constant.AttributeKey;

/**
 * Tag for displaying paginaton
 */
public class PaginatorTag extends TagSupport {

	private static final Logger LOG = Logger.getLogger(PaginatorTag.class);

	@Override
	public int doStartTag() throws JspException {
		ProductSearchBean formBean = (ProductSearchBean) pageContext.findAttribute(AttributeKey.FORM_BEAN);

		int productsCount = (int) pageContext.findAttribute(AttributeKey.PRODUCTS_COUNT);
		int onePageLimit = formBean.getLimit();
		int numberOfPages = productsCount / onePageLimit;
		if ((productsCount % onePageLimit) != 0) {
			numberOfPages++;
		}
		if (numberOfPages > 1) {
			JspWriter out = pageContext.getOut();
			try {
				out.append("<ul class=\"pagination pagination-lg  \">");
				for (int i = 1; i <= numberOfPages; i++) {
					if (i == formBean.getPage()) {
						out.append("<li class=\"active\"><a href=\"searchProducts?" + getPaginatorParameteres(formBean)
								+ "&page=" + i + "\">" + i + "</a></li>");
					} else {
						out.append("<li><a href=\"searchProducts?" + getPaginatorParameteres(formBean) + "&page=" + i
								+ "\">" + i + "</a></li>");
					}
				}
			} catch (IOException e) {
				LOG.warn("PaginatorTag display error", e);
			}

		}
		return SKIP_BODY;
	}

	/**
	 * Builds link with parameters
	 * @param formBean
	 * @return
	 */
	private String getPaginatorParameteres(ProductSearchBean formBean) {
		StringBuilder sb = new StringBuilder();
		if (formBean.getName() != null) {
			sb.append("name=").append(formBean.getName());
		}
		if (formBean.getPriceFrom() != null) {
			sb.append("&priceFrom=").append(formBean.getPriceFrom());
		}
		if (formBean.getPriceTo() != null) {
			sb.append("&priceTo=").append(formBean.getPriceTo());
		}
		if (formBean.getCategories() != null) {
			for (int categoryId : formBean.getCategories()) {
				sb.append("&categories=").append(categoryId);
			}
		}
		if (formBean.getManufacturers() != null) {
			for (int manufacturerId : formBean.getManufacturers()) {
				sb.append("&manufacturers=").append(manufacturerId);
			}
		}
		if (formBean.getSortedBy() != null) {
			sb.append("&sort=").append(formBean.getSortedBy());
		}
		if (formBean.isDescending()) {
			sb.append("&isDescending=on");
		}
		sb.append("&size=").append(formBean.getLimit());
		return sb.toString();
	}

}
