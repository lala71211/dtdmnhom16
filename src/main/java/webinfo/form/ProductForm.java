package webinfo.form;
import org.springframework.web.multipart.MultipartFile;
import webinfo.entity.Products;
public class ProductForm {
	private String code;
    private String name;
    private String detail;
 
    private boolean newProduct = false;
 
    // Upload file.
    private MultipartFile fileData;
 
    public ProductForm() {
        this.newProduct= true;
    }
 
    public ProductForm(Products product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.detail = product.getDetail();
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getDetail() {
        return detail;
    }
 
    public void setDetail(String detail) {
        this.detail = detail;
    }
 
    public MultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
}
