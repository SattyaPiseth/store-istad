package co.istad.storeistad.init;


import co.istad.storeistad.db.entity.*;
import co.istad.storeistad.db.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final CategoryRepository categoryRepository;
    private final VariationRepository variationRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){

        // Staff-Authorities-Management
        PermissionEntity readProduct = PermissionEntity.builder().name("product:read").module("product").build();
        PermissionEntity writeProduct = PermissionEntity.builder().name("product:write").module("product").build();
        PermissionEntity deleteProduct = PermissionEntity.builder().name("product:delete").module("product").build();
        PermissionEntity updateProduct = PermissionEntity.builder().name("product:update").module("product").build();
        PermissionEntity patchProduct = PermissionEntity.builder().name("product:patch").module("product").build();
        List<PermissionEntity> productAuthorities = List.of(readProduct,writeProduct,deleteProduct,updateProduct,patchProduct);
        permissionRepository.saveAll(productAuthorities);

        // User-Authorities-Management
        PermissionEntity readUser = PermissionEntity.builder().name("user:read").module("user").build();
        PermissionEntity writeUser = PermissionEntity.builder().name("user:write").module("user").build();
        PermissionEntity deleteUser = PermissionEntity.builder().name("user:delete").module("user").build();
        PermissionEntity updateUser = PermissionEntity.builder().name("user:update").module("user").build();
        PermissionEntity patchUser = PermissionEntity.builder().name("user:patch").module("user").build();
        PermissionEntity userProfile = PermissionEntity.builder().name("user:profile").module("user").build();
        List<PermissionEntity> userAuthorities = List.of(readUser,writeUser,deleteUser,updateUser,patchUser,userProfile);
        permissionRepository.saveAll(userAuthorities);

        // Role-Authorities-Management
        PermissionEntity readRole = PermissionEntity.builder().name("role:read").module("role").build();
        PermissionEntity writeRole = PermissionEntity.builder().name("role:write").module("role").build();
        PermissionEntity deleteRole = PermissionEntity.builder().name("role:delete").module("role").build();
        PermissionEntity updateRole = PermissionEntity.builder().name("role:update").module("role").build();
        PermissionEntity patchRole = PermissionEntity.builder().name("role:patch").module("role").build();
        List<PermissionEntity> roleAuthorities = List.of(readRole,writeRole,deleteRole,updateRole,patchRole);
        permissionRepository.saveAll(roleAuthorities);

        // Combine Authorities (Staff + User)
        List<PermissionEntity> fullAuthorities = new ArrayList<>(){{
            addAll(productAuthorities);
            addAll(userAuthorities);
            addAll(roleAuthorities);
        }};


        // Role Management : Admin,Staff,Customer

        RoleEntity adminRole = RoleEntity.builder()
                .name("ADMIN")
                .code("admin")
                .permissionEntities(fullAuthorities)
                .build();

        RoleEntity staffRole = RoleEntity.builder()
                .name("STAFF")
                .code("staff")
                .permissionEntities(new ArrayList<>(){{
                    addAll(productAuthorities);
                    add(userProfile);
                }})
                .build();

        RoleEntity customerRole = RoleEntity.builder()
                .name("CUSTOMER")
                .code("customer")
                .permissionEntities(new ArrayList<>(){{
                    add(readProduct);
                    add(userProfile);
                }})
                .build();

        roleRepository.saveAll(List.of(adminRole,staffRole,customerRole));

        // User Management : Init Main Admin
        UserEntity mainAdmin = UserEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("pisethsattya33@gmail.com")
                .name("Piseth Sattya")
                .bio("student")
                .avatar("")
                .address("Phnom Penh, Cambodia")
                .isDeleted(false)
                .phone("099771616")
                .status(true)
                .roleEntity(adminRole)
                .build();
        mainAdmin.setCreatedBy(mainAdmin);
        userRepository.save(mainAdmin);

        CategoryEntity clothing = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Clothing")
                .description("clothing")
                .parent(null)
                .build();

        CategoryEntity ladiesClothing = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Ladies Clothing")
                .description("ladies clothing")
                .parent(clothing)
                .build();

        CategoryEntity menClothing = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Mens Clothing")
                .description("mens clothing")
                .parent(clothing)
                .build();

        CategoryEntity baseLayer = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Base Layer")
                .description("base layer")
                .parent(menClothing)
                .build();

        CategoryEntity jacketsAndVests = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Jackets & Vests")
                .description("jackets & vests")
                .parent(menClothing)
                .build();

        CategoryEntity pantsAndLeggings = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Pants & Leggings")
                .description("pants & leggings")
                .parent(menClothing)
                .build();

        CategoryEntity shirtsAndTops = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shirts & Tops")
                .description("shirts & tops")
                .parent(menClothing)
                .build();

        CategoryEntity shoes = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shoes")
                .description("shoes")
                .parent(menClothing)
                .build();

        CategoryEntity shorts = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shorts")
                .description("shorts")
                .parent(menClothing)
                .build();

        CategoryEntity underwear = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Underwear")
                .description("underwear")
                .parent(menClothing)
                .build();

        CategoryEntity baseLayerLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Base Layer")
                .description("base layer")
                .parent(ladiesClothing)
                .build();

        CategoryEntity jacketsAndVestsLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Jackets & Vests")
                .description("jackets & vests")
                .parent(ladiesClothing)
                .build();

        CategoryEntity pantsAndLeggingsLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Pants & Leggings")
                .description("pants & leggings")
                .parent(ladiesClothing)
                .build();

        CategoryEntity shirtsAndTopsLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shirts & Tops")
                .description("shirts & tops")
                .parent(ladiesClothing)
                .build();

        CategoryEntity shoesLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shoes")
                .description("shoes")
                .parent(ladiesClothing)
                .build();

        CategoryEntity shortsLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Shorts")
                .description("shorts")
                .parent(ladiesClothing)
                .build();

        CategoryEntity dressesAndSkirts = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Dresses & Skirts")
                .description("dresses & skirts")
                .parent(ladiesClothing)
                .build();

        CategoryEntity underwearLadies = CategoryEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Underwear")
                .description("underwear")
                .parent(ladiesClothing)
                .build();

        categoryRepository.saveAll(List.of(clothing,ladiesClothing,menClothing,baseLayer,jacketsAndVests,pantsAndLeggings, shirtsAndTops,shoes,shorts,underwear,
                baseLayerLadies,jacketsAndVestsLadies,pantsAndLeggingsLadies,shirtsAndTopsLadies,shoesLadies,shortsLadies,dressesAndSkirts,underwearLadies));


        // Variation Management
        VariationEntity color = VariationEntity.builder()
                .name("Color")
                .category(clothing)
                .build();

        VariationEntity size = VariationEntity.builder()
                .name("Size")
                .category(clothing)
                .build();

        VariationEntity fit = VariationEntity.builder()
                .name("Fit")
                .category(clothing)
                .build();
        VariationEntity price = VariationEntity.builder()
                .name("Price")
                .category(clothing)
                .build();
        variationRepository.saveAll(List.of(color,size,fit,price));

        // Variation Option Management
        VariationOptionEntity black = VariationOptionEntity.builder()
                .variation(color)
                .value("Black")
                .build();

        VariationOptionEntity white = VariationOptionEntity.builder()
                .variation(color)
                .value("White")
                .build();

        VariationOptionEntity gray = VariationOptionEntity.builder()
                .variation(color)
                .value("Gray")
                .build();

        VariationOptionEntity blue = VariationOptionEntity.builder()
                .variation(color)
                .value("Blue")
                .build();

        VariationOptionEntity red = VariationOptionEntity.builder()
                .variation(color)
                .value("Red")
                .build();

        VariationOptionEntity green = VariationOptionEntity.builder()
                .variation(color)
                .value("Green")
                .build();

        VariationOptionEntity purple = VariationOptionEntity.builder()
                .variation(color)
                .value("Purple")
                .build();

        VariationOptionEntity yellow = VariationOptionEntity.builder()
                .variation(color)
                .value("Yellow")
                .build();

        VariationOptionEntity pink = VariationOptionEntity.builder()
                .variation(color)
                .value("Pink")
                .build();

        VariationOptionEntity orange = VariationOptionEntity.builder()
                .variation(color)
                .value("Orange")
                .build();

        VariationOptionEntity brown = VariationOptionEntity.builder()
                .variation(color)
                .value("Brown")
                .build();

        VariationOptionEntity xs = VariationOptionEntity.builder()
                .variation(size)
                .value("XS")
                .build();

        VariationOptionEntity s = VariationOptionEntity.builder()
                .variation(size)
                .value("S")
                .build();

        VariationOptionEntity m = VariationOptionEntity.builder()
                .variation(size)
                .value("M")
                .build();

        VariationOptionEntity l = VariationOptionEntity.builder()
                .variation(size)
                .value("L")
                .build();

        VariationOptionEntity xl = VariationOptionEntity.builder()
                .variation(size)
                .value("XL")
                .build();

        VariationOptionEntity xxl = VariationOptionEntity.builder()
                .variation(size)
                .value("XXL")
                .build();

        VariationOptionEntity X3l = VariationOptionEntity.builder()
                .variation(size)
                .value("3XL")
                .build();

        VariationOptionEntity slim = VariationOptionEntity.builder()
                .variation(fit)
                .value("Slim")
                .build();

        VariationOptionEntity regular = VariationOptionEntity.builder()
                .variation(fit)
                .value("Regular")
                .build();

        VariationOptionEntity loose = VariationOptionEntity.builder()
                .variation(fit)
                .value("Loose")
                .build();

        VariationOptionEntity under25 = VariationOptionEntity.builder()
                .variation(price)
                .value("Under $25")
                .build();

        VariationOptionEntity $25to$50 = VariationOptionEntity.builder()
                .variation(price)
                .value("$25 to $50")
                .build();

        VariationOptionEntity $50to$100 = VariationOptionEntity.builder()
                .variation(price)
                .value("$50 to $100")
                .build();

        VariationOptionEntity $100to$200 = VariationOptionEntity.builder()
                .variation(price)
                .value("$100 to $200")
                .build();

        VariationOptionEntity $200to$500 = VariationOptionEntity.builder()
                .variation(price)
                .value("$200 to $500")
                .build();

        VariationOptionEntity over$500 = VariationOptionEntity.builder()
                .variation(price)
                .value("Over $500")
                .build();

variationOptionRepository.saveAll(List.of(black,white,gray,blue,red,green,purple,yellow,pink,orange,brown,
        xs,s,m,l,xl,xxl,X3l,slim,regular,loose,under25,$25to$50,$50to$100,$100to$200,$200to$500,over$500));

//        // Product Management
//        ProductEntity menUaFastLeftChestTShirt = ProductEntity.builder()
//                .uuid(UUID.randomUUID().toString())
//                .name("Men's UA Fast Left Chest T-Shirt")
//                .description("Super-soft, cotton-blend fabric provides all-day comfort")
//                .image("https://underarmour.scene7.com/is/image/Underarmour/V5-1370954-011_FC?rp=standard-0pad%7CpdpMainDesktop&scl=1&fmt=jpg&qlt=85&resMode=sharp2&cache=on%2Con&bgc=F0F0F0&wid=566&hei=708&size=566%2C708")
//                .category(shirtsAndTops)
//                .build();
//
//        ProductEntity womanUaRivalFleeceOverSizedCrew = ProductEntity.builder()
//                .uuid(UUID.randomUUID().toString())
//                .name("Women's UA Rival Fleece OverSized Crew")
//                .description("Ultra-soft, mid-weight cotton-blend fleece with brushed interior for extra warmth")
//                .image("https://underarmour.scene7.com/is/image/Underarmour/V5-1379491-743_FC?rp=standard-0pad%7CpdpMainDesktop&scl=1&fmt=jpg&qlt=85&resMode=sharp2&cache=on%2Con&bgc=F0F0F0&wid=566&hei=708&size=566%2C708")
//                .category(shirtsAndTopsLadies)
//                .build();
//
//        productRepository.saveAll(List.of(menUaFastLeftChestTShirt,womanUaRivalFleeceOverSizedCrew));
    }
}