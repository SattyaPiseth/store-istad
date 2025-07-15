package co.istad.storeistad.service.category;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.model.request.category.CategoryDto;
import co.istad.storeistad.model.request.category.UpdateCategoryRQ;
import co.istad.storeistad.model.request.category.UpdateStatusCategoryRQ;

/**
 * @author Sattya
 * create at 2/9/2024 5:19 PM
 */
public interface CategoryService {
    /**
     * This method is used to create a new category
     * resource into database
     * @param request is the request data from client
     */
    StructureRS createNew(CategoryDto request);

    /**
     * This method is used to find category by uuid
     * @param uuid of category (unique identifier)
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS findByUuid(String uuid, BaseListingRQ request);

    /**
     * This method is used to delete category from database by uuid (recommended)
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS updateStatusCategories(String uuid, UpdateStatusCategoryRQ request);

    /**
     * This method is used to update category from database by uuid
     * @param uuid is identifier of category
     * @param request is the request data from client
     */
    void updateByUuid(String uuid, UpdateCategoryRQ request);

    /**
     * This method is used to get all category from database
     * @param request is the request data from client
     * @return StructureRS
     */
    StructureRS getAll(BaseListingRQ request);
}
