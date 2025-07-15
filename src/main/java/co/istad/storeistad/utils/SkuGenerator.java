package co.istad.storeistad.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Sattya
 * create at 2/10/2024 12:10 AM
 */
@Component
public class SkuGenerator {
    public static String generateSku(Map<String,String> variations,String productName){
        StringBuilder skuBuilder = new StringBuilder(productName.toUpperCase().replaceAll("\\s+", "_"));
    //    skuBuilder.append("_").append(baseSKU);

        for (Map.Entry<String, String> entry : variations.entrySet()) {
            String variation = entry.getKey();
            String option = entry.getValue();

            // append the variation and option to the base sku
//            skuBuilder.append("_").append(variation).append("_").append(option);
            skuBuilder.append(" ").append(variation).append(" ").append(option);

        }
        return skuBuilder.toString();
    }
}
