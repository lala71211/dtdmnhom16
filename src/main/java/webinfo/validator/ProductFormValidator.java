package webinfo.validator;

import webinfo.dao.ProductDAO;
import webinfo.entity.Products;
import webinfo.form.ProductForm;
import webinfo.model.ProductInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@SuppressWarnings("unused")
@Component
public class ProductFormValidator implements Validator {
	 @Autowired
	    private ProductDAO productDAO;
	 
	    // Validator này chỉ dùng để kiểm tra class ProductForm.
	    @Override
	    public boolean supports(Class<?> clazz) {
	        return clazz == ProductForm.class;
	    }
	 
	    @Override
	    public void validate(Object target, Errors errors) {
	        ProductForm productForm = (ProductForm) target;
	 
	        // Kiểm tra các trường (field) của ProductForm.
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detail", "NotEmpty.productForm.detail");
	 
	        String code = productForm.getCode();
	        if (code != null && code.length() > 0) {
	            if (code.matches("\\s+")) {
	                errors.rejectValue("code", "Pattern.productForm.code");
	            } else if (productForm.isNewProduct()) {
	                Products product = productDAO.findProduct(code);
	                if (product != null) {
	                    errors.rejectValue("code", "Duplicate.productForm.code");
	                }
	            }
	        }
	    }
}
