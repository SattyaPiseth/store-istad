package co.istad.storeistad.service.product;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.ProductEntity;
import co.istad.storeistad.db.repository.CategoryRepository;
import co.istad.storeistad.db.repository.ProductRepository;
import co.istad.storeistad.exception.httpstatus.InternalServerError;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mapper.ProductEntityMapper;
import co.istad.storeistad.model.projection.product.ProductEntityInfo;
import co.istad.storeistad.model.request.product.CreateProductRQ;
import co.istad.storeistad.model.request.product.UpdateProductRQ;
import co.istad.storeistad.model.request.product.UpdateStatusProductRQ;
import co.istad.storeistad.model.response.product.ProductEntityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Sattya
 * create at 2/10/2024 8:23 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends BaseService implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductEntityMapper productEntityMapper;
    @Override
    public StructureRS getAll(BaseListingRQ request) {
        Page<ProductEntityInfo> productEntityInfoPage = productRepository.findByQuery(request.getQuery(),request.getPageable(request.getSort(),request.getOrder()));
        return response(productEntityInfoPage.getContent(),productEntityInfoPage);
    }

    @Override
    public StructureRS findByUuid(String uuid,BaseListingRQ request) {
        if (!productRepository.existsByUuidAndDeletedAtIsNull(uuid)){
            throw new NotFoundException(MessageConstant.PRODUCT.PRODUCT_NOT_FOUND);
        }
        ProductEntityDto productEntityDto = productEntityMapper.toProductEntityDto(productRepository.findByUuid(uuid));
        return response(productEntityDto);
    }
    @Transactional
    @Override
    public StructureRS createNew(CreateProductRQ request) {
        try{
            if (productRepository.existsByName(request.getName())){
                throw new InternalServerError(MessageConstant.PRODUCT.PRODUCT_ALREADY_EXISTS);
            }
            ProductEntity productEntity = productEntityMapper.toEntity(request);
            productEntity.setUuid(UUID.randomUUID().toString());
            if (request.getCategoryId() != null){
                productEntity.setCategory(categoryRepository.findById(Long.valueOf(request.getCategoryId())).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND)));
                productRepository.save(productEntity);
            }else {
                throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage());
        }
        return response(MessageConstant.PRODUCT.PRODUCT_CREATED_SUCCESSFULLY);
    }
    @Transactional
    @Override
    public void updateByUuid(String uuid, UpdateProductRQ request) {
    ProductEntity productEntity = getProductByUuid(uuid);
    checkProductExistsByName(request.getName());
    partialUpdateProduct(productEntity,request);
    saveProduct(productEntity);
    }
    private ProductEntity getProductByUuid(String uuid){
        if (!productRepository.existsByUuidAndDeletedAtIsNull(uuid)){
            throw new NotFoundException(MessageConstant.PRODUCT.PRODUCT_NOT_FOUND);
        }
        return productRepository.findByUuid(uuid);
    }
    private void checkProductExistsByName(String name){
        if (productRepository.existsByName(name)){
            throw new InternalServerError(MessageConstant.PRODUCT.PRODUCT_ALREADY_EXISTS);
        }
    }
    private void partialUpdateProduct(ProductEntity productEntity,UpdateProductRQ request){
        productEntityMapper.partialUpdate(request,productEntity);
        if (request.getCategoryId() != null){
            productEntity.setCategory(categoryRepository.findById(Long.valueOf(request.getCategoryId())).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY.CATEGORY_NOT_FOUND)));
        }
    }

    private void saveProduct(ProductEntity productEntity){
        productRepository.save(productEntity);
    }
    @Transactional
    @Override
    public StructureRS updateStatusProducts(String uuid, UpdateStatusProductRQ request) {
        boolean isFound = productRepository.existsByUuid(uuid);
        if (!isFound){
            throw new NotFoundException(MessageConstant.PRODUCT.PRODUCT_NOT_FOUND);
        }
        Instant deletedAt = request.getStatus() ? null : Instant.now();
        productRepository.findByUuid(uuid).setDeletedAt(deletedAt);

        String message = request.getStatus() ? MessageConstant.PRODUCT.PRODUCT_RESTORED_SUCCESSFULLY : MessageConstant.PRODUCT.PRODUCT_DELETED_SUCCESSFULLY;
        return response(message);
    }
}
