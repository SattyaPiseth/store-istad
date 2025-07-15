package co.istad.storeistad.service.product_item;


import co.istad.storeistad.base.BaseListingRQ;
import co.istad.storeistad.base.BaseService;
import co.istad.storeistad.base.StructureRS;
import co.istad.storeistad.constant.MessageConstant;
import co.istad.storeistad.db.entity.ProductEntity;
import co.istad.storeistad.db.entity.ProductItemEntity;
import co.istad.storeistad.db.entity.VariationOptionEntity;
import co.istad.storeistad.db.repository.ProductItemRepository;
import co.istad.storeistad.db.repository.ProductRepository;
import co.istad.storeistad.db.repository.VariationOptionRepository;
import co.istad.storeistad.exception.httpstatus.BadRequestException;
import co.istad.storeistad.exception.httpstatus.InternalServerError;
import co.istad.storeistad.exception.httpstatus.NotFoundException;
import co.istad.storeistad.mapper.ProductItemMapper;
import co.istad.storeistad.model.projection.productItem.ProductItemEntityInfo;
import co.istad.storeistad.model.request.product_item.CreateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateStatusProductItemRQ;
import co.istad.storeistad.model.request.product_item.VariationOptionRQ;
import co.istad.storeistad.utils.SkuGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Sattya
 * create at 2/10/2024 8:49 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductItemServiceImpl extends BaseService implements ProductItemService{
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemMapper productItemMapper;
    @Transactional
    @Override
    public void addProductItem(CreateProductItemRQ request) {
        try {
            ProductItemEntity productItem = productItemMapper.fromCreateProductItemRQ(request);
            String code = generateProductItemCode(request);
            productItem.setCode(code);
            Set<VariationOptionEntity> variationOptions = fetchAndValidateVariationOptions(request.getVariationOptions());
            updateVariationOptions(variationOptions, request.getVariationOptions());
            productItem.setVariationOptions(variationOptions);
            productItemRepository.save(productItem);
        } catch (DataIntegrityViolationException ex) {
            throw new InternalServerError(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_ALREADY_EXISTS);
        }
    }

    private String generateProductItemCode(CreateProductItemRQ request) {
        Long productId = Long.valueOf(request.getProductId());
        ProductEntity productEntity = getProductById(productId);
        if (request.getVariationOptions().isEmpty()) {
            throw new BadRequestException(MessageConstant.VARIATION_OPTION.VARIATION_OPTION_REQUIRED);
        }
        String productName = productEntity.getName();
        Map<String, String> variations = new HashMap<>();
        request.getVariationOptions().forEach(variationOptionRQ -> {
            VariationOptionEntity variationOption = getVariationOptionById(variationOptionRQ.getVariationOptionId());
            if (variationOptionRQ.getStatus()) {
                variations.put(variationOption.getVariation().getName(), variationOption.getValue());
            }
        });
        return SkuGenerator.generateSku(variations, productName);
    }

    private void updateVariationOptions(Set<VariationOptionEntity> variationOptions, Set<VariationOptionRQ> variationOptionRQs) {
        List<VariationOptionEntity> variationOptionsToRemove = new ArrayList<>();

        for (VariationOptionRQ variationOptionRQ : variationOptionRQs) {
            Optional<VariationOptionEntity> variationOptionEntityOptional = variationOptionRepository.findById(variationOptionRQ.getVariationOptionId());

            variationOptionEntityOptional.ifPresent(variationOption -> {
                if (variationOptionRQ.getStatus()) {
                    variationOptions.add(variationOption);
                } else if (!variationOptions.isEmpty() && !variationOption.equals(variationOptions.iterator().next())) {
                    variationOptions.remove(variationOption);
                } else {
                    // If it's the last record and its status is false, don't remove it
                    variationOptionsToRemove.add(variationOption);
                }
            });
        }

        variationOptionsToRemove.forEach(variationOptions::remove);
    }


    private Set<VariationOptionEntity> fetchAndValidateVariationOptions(Set<VariationOptionRQ> variationOptions) {
        validateVariationOptionsExist(variationOptions);
        return variationOptions.stream()
                .map(variationOptionRQ -> getVariationOptionById(variationOptionRQ.getVariationOptionId()))
                .collect(Collectors.toSet());
    }

    private void validateVariationOptionsExist(Set<VariationOptionRQ> variationOptions) {
        boolean anyNotFound = variationOptions.stream()
                .anyMatch(variationOptionRQ -> !variationOptionRepository.existsById(variationOptionRQ.getVariationOptionId()));
        if (anyNotFound) {
            throw new NotFoundException(MessageConstant.VARIATION_OPTION.VARIATION_OPTION_NOT_FOUND);
        }
    }

    private ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(MessageConstant.PRODUCT.PRODUCT_NOT_FOUND));
    }

    private VariationOptionEntity getVariationOptionById(Long variationOptionId) {
        return variationOptionRepository.findById(variationOptionId)
                .orElseThrow(() -> new NotFoundException(MessageConstant.VARIATION_OPTION.VARIATION_OPTION_NOT_FOUND));
    }
    @Transactional
    @Override
    public void updateProductItem(Long id, UpdateProductItemRQ request) {
        ProductItemEntity productItem = getProductItemById(id);
        mapUpdateProductItem(request, productItem);
        updateVariationOptionsIfProvided(request, productItem);
        String code = generateProductItemCode(request);
        productItem.setCode(code);
        productItemRepository.save(productItem);
    }
    private ProductItemEntity getProductItemById(Long id) {
        return productItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_NOT_FOUND));
    }

    private void mapUpdateProductItem(UpdateProductItemRQ request, ProductItemEntity productItem) {
        productItemMapper.fromUpdateProductItemRQ(productItem, request);
        productItem.setProduct(getProductById(Long.valueOf(request.getProductId())));
    }

    private void updateVariationOptionsIfProvided(UpdateProductItemRQ request, ProductItemEntity productItem) {
        if (request.getVariationOptions() != null && !request.getVariationOptions().isEmpty()) {
            Set<VariationOptionEntity> variationOptions = fetchAndValidateVariationOptions(request.getVariationOptions());
            updateVariationOptions(variationOptions, request.getVariationOptions());
            productItem.setVariationOptions(variationOptions);
        }
    }
    private String generateProductItemCode(UpdateProductItemRQ request) {
        Integer productId = request.getProductId();
        if (productId == null) {
            throw new BadRequestException(MessageConstant.PRODUCT_ITEM.PRODUCT_ID_REQUIRED);
        }
        ProductEntity productEntity = getProductById(Long.valueOf(productId));
        if (request.getVariationOptions().isEmpty()) {
            throw new BadRequestException(MessageConstant.VARIATION_OPTION.VARIATION_OPTION_REQUIRED);
        }
        String productName = productEntity.getName();
        Map<String, String> variations = extractVariations(request.getVariationOptions());
        return SkuGenerator.generateSku(variations, productName);
    }

    private Map<String, String> extractVariations(Set<VariationOptionRQ> variationOptions) {
        Map<String, String> variations = new HashMap<>();
        variationOptions.forEach(variationOptionRQ -> {
            VariationOptionEntity variationOption = getVariationOptionById(variationOptionRQ.getVariationOptionId());
            if (variationOptionRQ.getStatus()) {
                variations.put(variationOption.getVariation().getName(), variationOption.getValue());
            }
        });
        return variations;
    }

    @Transactional
    @Override
    public StructureRS updateStatusProductItem(Long id, UpdateStatusProductItemRQ request) {
        boolean isFound = productItemRepository.existsById(id);

        if (!isFound) {
            throw new NotFoundException(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_NOT_FOUND);
        }

        Instant deletedAt = request.getStatus() ? null : Instant.now();
        productItemRepository.updateDeletedAtById(deletedAt, id);

        String message = request.getStatus() ? MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_DELETED_SUCCESSFULLY :
                MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_UPDATED_SUCCESSFULLY;
        return response(message);
    }

    private StructureRS response(String message) {
        return new StructureRS(message); // Assuming StructureRS constructor accepts a message
    }

    @Override
    public StructureRS getProductItem(Long id) {
        try {
            ProductItemEntity productItem = productItemRepository.findByIdFetchVariationOptions(id);
            if (productItem != null) {
                return response(productItemMapper.toDto(productItem));
            } else {
                throw new NotFoundException(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new InternalServerError(MessageConstant.PRODUCT_ITEM.FAILED_TO_GET_PRODUCT_ITEM);
        }
    }

    @Override
    public StructureRS getAllProductItem(BaseListingRQ request) {
        Page<ProductItemEntityInfo> productItemEntityInfoPage = productItemRepository.findByQuery(request.getQuery(), request.getPageable(request.getSort(),request.getOrder()));
        if (productItemEntityInfoPage.isEmpty()) {
            throw new NotFoundException(MessageConstant.PRODUCT_ITEM.PRODUCT_ITEM_NOT_FOUND);
        }
        return response(productItemEntityInfoPage.getContent(),productItemEntityInfoPage);
    }
}
