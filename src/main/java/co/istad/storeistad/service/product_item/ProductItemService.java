package co.istad.storeistad.service.product_item;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.model.request.product_item.CreateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateStatusProductItemRQ;

/**
 * @author Sattya
 * create at 2/9/2024 9:34 PM
 */
public interface ProductItemService {
    /**
     * Add product item
     * @param request is request body of product item
     * default value of status add items is true (active)
     */
    void addProductItem(CreateProductItemRQ request);
    /**
     * Update product item
     * @param id is id of product item
     * @param request is request body of product item
     * note : status true (active) or false (inactive)
     */
    void updateProductItem(Long id, UpdateProductItemRQ request);
    /**
     * Update status product item
     * @param id is id of product item
     * @param request is request body of product item
     * note : status true (active) or false (inactive)
     */
    StructureRS updateStatusProductItem(Long id, UpdateStatusProductItemRQ request);
    /**
     * Get product item
     * @param id is id of product item
     * @return product item response
     */
    StructureRS getProductItem(Long id);
    /**
     * Get all product item
     * @param request is request body of product item
     * @return product item response list
     */
    StructureRS getAllProductItem(BaseListingRQ request);
}
