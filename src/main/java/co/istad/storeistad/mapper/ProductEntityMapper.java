package co.istad.storeistad.mapper;


import co.istad.storeistad.db.entity.ProductEntity;
import co.istad.storeistad.model.projection.product.ProductEntityInfo;
import co.istad.storeistad.model.request.product.CreateProductRQ;
import co.istad.storeistad.model.request.product.UpdateProductRQ;
import co.istad.storeistad.model.response.product.ProductEntityDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityMapper {
    @Mapping(source = "category.id", target = "category.id")
    @Mapping(source = "category.uuid", target = "category.uuid")
    @Mapping(source = "category.name", target = "category.name")
    @Mapping(source = "category.description", target = "category.description")
    @Mapping(source = "category.parent.id", target = "category.parent.id")
    @Mapping(source = "category.parent.uuid", target = "category.parent.uuid")
    @Mapping(source = "category.parent.name", target = "category.parent.name")
    @Mapping(source = "category.parent.description", target = "category.parent.description")
    ProductEntityDto toProductEntityDto(ProductEntity productEntity);

    ProductEntity toEntity(CreateProductRQ createProductRQ);

    ProductEntity toEntity(ProductEntityDto productEntityDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UpdateProductRQ updateProductRQ, @MappingTarget ProductEntity productEntity);

    ProductEntity toEntity(ProductEntityInfo productEntityInfo);
}