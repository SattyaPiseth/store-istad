package co.istad.storeistad.mapper;


import co.istad.storeistad.db.entity.CategoryEntity;
import co.istad.storeistad.model.request.category.CategoryDto;
import co.istad.storeistad.model.request.category.UpdateCategoryRQ;
import co.istad.storeistad.model.response.category.CategoryEntityDto;
import org.mapstruct.*;

import java.util.Collection;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UpdateCategoryRQ updateCategoryRQ, @MappingTarget CategoryEntity categoryEntity);

//    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(source = "parent.uuid",target = "parentUuid")
    @Mapping(source = "parent.name",target = "parentName")
    @Mapping(source = "parent.description",target = "parentDescription")
    CategoryEntityDto toDto1(CategoryEntity categoryEntity);

    CategoryEntity toEntity(CategoryDto categoryDto);
}