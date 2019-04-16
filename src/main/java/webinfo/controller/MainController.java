package webinfo.controller;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webinfo.entity.Products;
import webinfo.form.ProductForm;
import webinfo.dao.ProductDAO;
import webinfo.model.ProductInfo;
import webinfo.pagination.PaginationResult;
import webinfo.validator.ProductFormValidator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
@SuppressWarnings("unused")
@Controller
@Transactional

public class MainController {
	@Autowired
    private ProductDAO productDAO;
	 final int maxResult = 5;
     final int maxNavigationPage = 10;
     @Autowired
     private ProductFormValidator productFormValidator;
	  @InitBinder
	    public void myInitBinder(WebDataBinder dataBinder) {
	        Object target = dataBinder.getTarget();
	        if (target == null) {
	            return;
	        }
	        System.out.println("Target=" + target); 
	        if (target.getClass() == ProductForm.class) {
	            dataBinder.setValidator(productFormValidator); 
	        }
	    }
	 
	    @RequestMapping("/404")
	    public String accessDenied() {
	        return "/404";
	    }
	    @RequestMapping("/")
	    public String home() {
	        return "index";
	    }
	  
	    // Danh sách sản phẩm.
	    @RequestMapping({ "/productList" })
	    public String listProductHandler(Model model, //
	            @RequestParam(value = "name", defaultValue = "") String likeName,
	            @RequestParam(value = "page", defaultValue = "1") int page) {
	       
	 
	        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
	                maxResult, maxNavigationPage, likeName);
	 
	        model.addAttribute("paginationProducts", result);
	        return "productList";
	    }
	    @RequestMapping({ "/Product" })
	    public String listProductHandler(HttpServletRequest request, Model model, //
	            @RequestParam(value = "code", defaultValue = "") String code) {
	 
	        Products product = null;
	        if (code != null && code.length() > 0) {
	            product = productDAO.findProduct(code);
	        }
	        if (product != null) {
	        	
	        	 ProductInfo result = new ProductInfo(product);
	        	 model.addAttribute("productInfo", result);
	        }
	        return "product";
	    }
//	    @RequestMapping(value = { "/Up" }, method = RequestMethod.GET)
//	    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
//	        ProductForm productForm = null;
//	 
//	        if (code != null && code.length() > 0) {
//	            Products product = productDAO.findProduct(code);
//	            if (product != null) {
//	                productForm = new ProductForm(product);
//	            }
//	        }
//	        if (productForm == null) {
//	            productForm = new ProductForm();
//	            productForm.setNewProduct(true);
//	        }
//	        model.addAttribute("productForm", productForm);
//	        return "product";
//	    }
//	    @RequestMapping(value = { "/Up" }, method = RequestMethod.POST)
//	    public String productSave(Model model, //
//	            @ModelAttribute("productForm") @Validated ProductForm productForm, //
//	            BindingResult result, //
//	            final RedirectAttributes redirectAttributes) {
//	 
//	        if (result.hasErrors()) {
//	            return "product";
//	        }
//	        try {
//	            productDAO.save(productForm);
//	        } catch (Exception e) {
//	            Throwable rootCause = ExceptionUtils.getRootCause(e);
//	            String message = rootCause.getMessage();
//	            model.addAttribute("errorMessage", message);
//	            // Show product form.
//	            return "product";
//	        }
//	 
//	        return "redirect:/productList";
//	    }
	    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
	            @RequestParam("code") String code) throws IOException {
	        Products product = null;
	        if (code != null) {
	            product = this.productDAO.findProduct(code);
	        }
	        if (product != null && product.getImage() != null) {
	            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	            response.getOutputStream().write(product.getImage());
	        }
	        response.getOutputStream().close();
	    }
}
