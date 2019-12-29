package com.preproduction.bobrov.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.bean.ProductSearchBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.service.ProductService;
import com.preproduction.bobrov.validator.FilterSearchValidator;

/**
 * Servlet that provides searching products by filters
 */
@WebServlet("/searchProducts")
public class ProductFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_PRODUCT_LIMIT = 8;
	private static final int DEFAULT_PAGE = 1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
		ProductService productService = (ProductService) context.getAttribute(AttributeKey.PRODUCT_SERVICE);
		FilterSearchValidator validator = (FilterSearchValidator) context
				.getAttribute(AttributeKey.SEARCH_FILTER_VALIDATOR);
		ProductSearchBean formBean = obtainFields(request, validator);
		request.setAttribute(AttributeKey.CATEGORIES, productService.getAllCategories());
		request.setAttribute(AttributeKey.MANUFACTURERS, productService.getAllManufacturers());
		request.setAttribute(AttributeKey.PRODUCTS, productService.getFilteredProducts(formBean));
		request.setAttribute(AttributeKey.PRODUCTS_COUNT, productService.countFilteredProducts(formBean));

		request.setAttribute(AttributeKey.FORM_BEAN, formBean);

		request.getRequestDispatcher(Path.FILTER_PRODUCTS).forward(request, response);
	}

	private ProductSearchBean obtainFields(HttpServletRequest request, FilterSearchValidator validator) {
		ProductSearchBean searchBean = new ProductSearchBean();
		searchBean.setName(request.getParameter("name"));
		String priceFrom = request.getParameter("priceFrom");
		String priceTo = request.getParameter("priceTo");
		if (validator.checkIsPositiveDouble(priceFrom)) {
			searchBean.setPriceFrom(new BigDecimal(priceFrom));
		}
		if (validator.checkIsPositiveDouble(priceTo)) {
			searchBean.setPriceTo(new BigDecimal(priceTo));
		}
		String[] categories = request.getParameterValues("categories");
		if (categories != null && categories.length != 0) {
			List<Integer> categoriyIds = new ArrayList<>();
			for (String category : categories) {
				if (validator.checkIsPositiveInteger(category)) {
					categoriyIds.add(Integer.parseInt(category));
				}
			}
			searchBean.setCategories(categoriyIds);
		}
		String[] manufacturers = request.getParameterValues("manufacturers");
		if (manufacturers != null && manufacturers.length != 0) {
			List<Integer> manufacturerIds = new ArrayList<>();
			for (String manufacturer : manufacturers) {
				if (validator.checkIsPositiveInteger(manufacturer)) {
					manufacturerIds.add(Integer.parseInt(manufacturer));
				}
			}
			searchBean.setManufacturers(manufacturerIds);
		}

		String sortedBy = request.getParameter("sort");
		searchBean.setSortedBy(sortedBy);
		if (request.getParameterValues("isDescending") != null) {
			searchBean.setDescending(true);
		}
		String page = request.getParameter("page");
		if (validator.checkIsPositiveInteger(page)) {
			searchBean.setPage(Integer.parseInt(page));
		} else {
			searchBean.setPage(DEFAULT_PAGE);
		}
		String size = request.getParameter("size");
		if (validator.checkIsPositiveInteger(size)) {
			searchBean.setLimit(Integer.parseInt(size));
		} else {
			searchBean.setLimit(DEFAULT_PRODUCT_LIMIT);
		}
		searchBean.setOffset((searchBean.getPage() - 1) * searchBean.getLimit());

		return searchBean;

	}

}
