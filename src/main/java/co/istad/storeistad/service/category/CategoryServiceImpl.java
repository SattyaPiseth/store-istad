package co.istad.storeistad.service.category;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.CategoryEntity;
import co.istad.storeistad.db.repository.CategoryRepository;
import co.istad.storeistad.exception.httpstatus.InternalServerError;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mapper.CategoryEntityMapper;
import co.istad.storeistad.model.projection.category.CategoryEntityInfo;
import co.istad.storeistad.model.request.category.CategoryDto;
import co.istad.storeistad.model.request.category.UpdateCategoryRQ;
import co.istad.storeistad.model.request.category.UpdateStatusCategoryRQ;
import co.istad.storeistad.model.response.category.CategoryEntityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Sattya
 * create at 2/14/2024 4:23 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl extends BaseService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    @Transactional
    @Override
    public StructureRS createNew(CategoryDto request) {
        try {
            if (categoryRepository.existsByName(request.name())) {
                throw new InternalServerError(MessageConstant.CATEGORY.CATEGORY_ALREADY_EXISTS);
            }
            CategoryEntity categoryEntity = categoryEntityMapper.toEntity(request);
            categoryEntity.setUuid(UUID.randomUUID().toString());
            if (request.parentId() != null && categoryRepository.existsById(request.parentId())) {
                categoryEntity.setParent(categoryRepository.findById(request.parentId()).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND)));
                categoryRepository.save(categoryEntity);
            }
            else if (request.parentId() == null){
                categoryEntity.setId(null);
                categoryRepository.save(categoryEntity);
            }else {
                throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or handle it according to your application's requirements
            // For now, let's print the stack trace
            e.printStackTrace();
            // You might also throw a custom exception or rollback the transaction if needed
            throw new InternalServerError(e.getMessage());
        }
        return response(MessageConstant.CATEGORY.CATEGORY_CREATED_SUCCESSFULLY);
    }


    @Override
    public StructureRS findByUuid(String uuid, BaseListingRQ request) {
       if (!categoryRepository.existsByUuidAndDeletedAtIsNull(uuid)){
           throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
       }
    CategoryEntityDto categoryEntityDto = categoryEntityMapper.toDto1(categoryRepository.findByUuid(uuid));
        return response(categoryEntityDto);
    }
    @Transactional
    @Override
    public StructureRS updateStatusCategories(String uuid, UpdateStatusCategoryRQ request) {
        boolean isFound = categoryRepository.existsByUuid(uuid);
        if (!isFound){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
        }
        Instant deletedAt = request.getStatus() ? null : Instant.now();
        categoryRepository.findByUuid(uuid).setDeletedAt(deletedAt);

        String message = request.getStatus() ? MessageConstant.CATEGORY.CATEGORY_RESTORED_SUCCESSFULLY : MessageConstant.CATEGORY.CATEGORY_DELETED_SUCCESSFULLY;
        return response(message);
    }


    @Transactional
    @Override
    public void updateByUuid(String uuid, UpdateCategoryRQ request) {
        CategoryEntity categoryEntity = getCategoryByUuid(uuid);
        checkCategoryExistsByName(request.getName());
        partialUpdateCategory(request, categoryEntity);
        updateParentCategory(request.getParentId(), categoryEntity);
        saveCategory(categoryEntity);

    }

    private CategoryEntity getCategoryByUuid(String uuid) {
        if (!categoryRepository.existsByUuidAndDeletedAtIsNull(uuid)) {
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
        }
        return categoryRepository.findByUuid(uuid);
    }

    private void checkCategoryExistsByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new InternalServerError(MessageConstant.CATEGORY.CATEGORY_ALREADY_EXISTS);
        }
    }

    private void partialUpdateCategory(UpdateCategoryRQ request, CategoryEntity categoryEntity) {
        categoryEntityMapper.partialUpdate(request, categoryEntity);
    }

    private void updateParentCategory(Long parentId, CategoryEntity categoryEntity) {
        if (parentId != null && categoryRepository.existsById(parentId) ) {
            if (categoryEntity.getId().equals(parentId)){
                throw new InternalServerError(MessageConstant.CATEGORY.CATEGORY_CANNOT_BE_PARENT_OF_ITSELF);
            }
            categoryEntity.setParent(categoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND)));
        } else if (parentId == null){
            categoryEntity.setParent(null);
        }
        else {
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
        }
    }

    private void saveCategory(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
    }

    @Override
    public StructureRS getAll(BaseListingRQ request) {
        Page<CategoryEntityInfo> categoryEntityInfos = categoryRepository.findByQuery(request.getQuery(), request.getPageable(request.getSort(),request.getOrder()));
        return response(categoryEntityInfos.getContent(),categoryEntityInfos);
    }
}
