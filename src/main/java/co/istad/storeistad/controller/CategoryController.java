package co.istad.storeistad.controller;


import co.istad.storeistad.base.BaseController;
import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.model.request.category.CategoryDto;
import co.istad.storeistad.model.request.category.UpdateCategoryRQ;
import co.istad.storeistad.model.request.category.UpdateStatusCategoryRQ;
import co.istad.storeistad.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sattya
 * create at 2/14/2024 4:33 PM
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    @GetMapping
    public StructureRS getAll(BaseListingRQ request){
        return response(categoryService.getAll(request)).getBody();
    }

    @GetMapping("/{uuid}")
    public StructureRS findByUuid(@PathVariable String uuid, BaseListingRQ request){
        return response(categoryService.findByUuid(uuid,request)).getBody();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureRS createNew(@RequestBody @Valid CategoryDto request){
       return categoryService.createNew(request);
    }

    @PatchMapping("/{uuid}")
    public StructureRS updateByUuid(@PathVariable String uuid, @RequestBody UpdateCategoryRQ request){
        categoryService.updateByUuid(uuid,request);
        return response(MessageConstant.CATEGORY.CATEGORY_UPDATED_SUCCESSFULLY).getBody();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/status")
    public StructureRS updateStatusCategories(@PathVariable String uuid,@ModelAttribute @RequestBody @Valid UpdateStatusCategoryRQ request){
        return response(categoryService.updateStatusCategories(uuid,request)).getBody();
    }
}
