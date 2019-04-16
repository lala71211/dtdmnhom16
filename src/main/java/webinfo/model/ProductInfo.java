package webinfo.model;
import webinfo.entity.Products;
public class ProductInfo {
	private String code;
    private String name;
    private String detail;
    public ProductInfo() {
    }
    public ProductInfo(Products product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.detail = product.getDetail();
    }
    public ProductInfo(String code, String name, String detail) {
        this.code = code;
        this.name = name;
        this.detail = detail;
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
 
    public void setdetail(String detail) {
        this.detail = detail;
    }
}
