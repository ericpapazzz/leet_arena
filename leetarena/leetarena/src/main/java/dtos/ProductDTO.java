package dtos;

import lombok.Data;

@Data
public class ProductDTO {
    
    private String product_name;
    private Integer product_price;
    private String product_tag;
    private String product_description;
    private String product_img;


}
