package co.istad.storeistad.mapper;


import co.istad.storeistad.db.entity.ProductItemEntity;
import co.istad.storeistad.model.request.product_item.CreateProductItemRQ;
import co.istad.storeistad.model.request.product_item.UpdateProductItemRQ;
import co.istad.storeistad.model.response.productItems.ProductItemEntityDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author Sattya
 * create at 2/10/2024 8:53 PM
 */
@Mapper(componentModel = "spring")
public interface ProductItemMapper {
    @Mapping(target = "product.id", source = "productId")
    ProductItemEntity fromCreateProductItemRQ(CreateProductItemRQ request);

    ProductItemEntityDto toDto(ProductItemEntity productItemEntity);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateProductItemRQ(@MappingTarget ProductItemEntity productItemEntity, UpdateProductItemRQ request);

    
}
