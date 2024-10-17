package co.istad.storeistad.service.product;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.model.request.product.CreateProductRQ;
import co.istad.storeistad.model.request.product.UpdateProductRQ;
import co.istad.storeistad.model.request.product.UpdateStatusProductRQ;

/**
 * @author Sattya
 * create at 2/9/2024 5:51 PM
 */
public interface ProductService {
    /**
     * This method is used to get all product from database
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS getAll(BaseListingRQ request);

    /**
     * This method is used to find product by uuid
     *
     * @param uuid of product
     * @return StructureRS
     */
    StructureRS findByUuid(String uuid,BaseListingRQ request);

    /**
     * This method is used to create a new product
     * @param request is the request data from client
     */
    StructureRS createNew(CreateProductRQ request);

    /**
     * This method is used to update(Partially Update) product by uuid
     * @param uuid of product
     * @param request is the request data from client for update
     */
    void updateByUuid(String uuid, UpdateProductRQ request);

    /**
     * This method is used to delete product from database by uuid
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS updateStatusProducts(String uuid, UpdateStatusProductRQ request);
}
