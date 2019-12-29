$(document).ready(function(){

$("button[name='cart_add_button']").click(function() {
	var productId = $(this).attr("productId");
	addProduct(productId);

});

$("button[name='cart_increment_button']").click(function() {
	var productId = $(this).attr("productId");
	addProduct(productId);
});

$("button[name='cart_decrement_button']").click(function() {
	var productId = $(this).attr("productId");
	deleteProduct(productId);
});

$("button[name='cart_delete_button']").click(function() {
	var productId = $(this).attr("productId");
	deleteAllProducts(productId);
});
function getCartInfo() {
	$.ajax({
		type : "GET",
		url : "getCartInfo",
		success : function(answer) {
			updateCartHeader(answer);			
		}
	});
}

function addProduct(productId) {
	$.ajax({
		type : "POST",
		url : "updateCart",
		data : {
			productId : productId,
			action : 'addProduct'
		},
		success : function(answer) {
			updateCartHeader(answer);
			updateProductCount(productId, answer);
		}
	});
}

function deleteProduct(productId) {
	$.ajax({
		type : "POST",
		url : "updateCart",
		data : {
			productId : productId,
			action : 'removeProduct'
		},
		success : function(answer) {
			updateCartHeader(answer);
			updateProductCount(productId, answer);
		}
	});
}

function deleteAllProducts(productId) {
	$.ajax({
		type : "POST",
		url : "updateCart",
		data : {
			productId : productId,
			action : 'removeAllProducts'
		},
		success : function(answer) {
			if (answer.size == 0) {
				window.location.href = "showCart";
			} else {
				updateCartHeader(answer);
				updateProductCount(productId, answer);
			}
		}
	});
}

function updateCartHeader(answer) {
	$("#cart_size").html(answer.size);
	$("span[name='total_price']").html(answer.totalPrice + "$");
}

function updateProductCount(productId, answer) {
	var tableRow = $("#product_" + productId);
	if (answer.productCount == 0) {
		$(tableRow).remove();
	} else {
		$(tableRow).find("div[name='product_count']").html(answer.productCount);
	}
}

	getCartInfo();
});
