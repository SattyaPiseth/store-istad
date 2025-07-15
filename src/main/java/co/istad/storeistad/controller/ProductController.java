package co.istad.storeistad.controller;


import co.istad.storeistad.base.BaseController;
import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.model.request.product.CreateProductRQ;
import co.istad.storeistad.model.request.product.UpdateProductRQ;
import co.istad.storeistad.model.request.product.UpdateStatusProductRQ;
import co.istad.storeistad.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 2/10/2024 8:39 PM
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService productService;

    @GetMapping
    public StructureRS getAll(BaseListingRQ request){
        return productService.getAll(request);
    }

    @GetMapping("/{uuid}")
    public StructureRS findByUuid(@PathVariable String uuid, BaseListingRQ request){
        return response(productService.findByUuid(uuid,request)).getBody();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureRS createNew(@RequestBody @Valid CreateProductRQ request){
        return productService.createNew(request);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public StructureRS updateByUuid(@PathVariable String uuid, @RequestBody @Valid UpdateProductRQ request){
        productService.updateByUuid(uuid,request);
        return response(MessageConstant.PRODUCT.PRODUCT_UPDATED_SUCCESSFULLY).getBody();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/status")
    public StructureRS updateStatusProducts(@PathVariable String uuid,@ModelAttribute @RequestBody @Valid UpdateStatusProductRQ request){
        return response(productService.updateStatusProducts(uuid,request)).getBody();
    }
}
