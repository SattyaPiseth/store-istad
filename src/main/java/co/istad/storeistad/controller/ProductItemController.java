package co.istad.storeistad.controller;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.model.request.product_item.CreateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateStatusProductItemRQ;
import co.istad.storeistad.service.product_item.ProductItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 2/10/2024 8:50 PM
 */
@RestController
@RequestMapping("/api/v1/product-items")
@RequiredArgsConstructor
public class ProductItemController {
    private final ProductItemService productItemService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StructureRS addProductItem(@RequestBody @Validated CreateProductItemRQ request){
        productItemService.addProductItem(request);
        return new StructureRS(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_CREATED_SUCCESSFULLY);
    }

    @GetMapping
    public StructureRS getAllProductItem(BaseListingRQ request){
        return productItemService.getAllProductItem(request);
    }

    @GetMapping("/{id}")
    public StructureRS getProductItem(@PathVariable Long id){
        return productItemService.getProductItem(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public StructureRS updateProductItem(@PathVariable Long id,
                                         @RequestBody @Valid UpdateProductItemRQ request){
        productItemService.updateProductItem(id, request);
        return new StructureRS(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_UPDATED_SUCCESSFULLY);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/status")
    public StructureRS updateStatusProductItem(@PathVariable Long id,
                                               @RequestBody @Valid UpdateStatusProductItemRQ request){
        return productItemService.updateStatusProductItem(id, request);
    }
}
