package com.rvbb.api.template.repository.spec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecCustom {

    public static <T> Specification<T> equal(String attributeName, Object val) {
        return (root, query, builder) -> {
            return builder.equal(root.get(attributeName), val);
        };
    }

   /* public static <T> Specification<T> lessThan(String attributeName, Object val) {
        return (root, query, builder) -> {
            return builder.lessThan(root.get(attributeName), val);
        };
    }

    public static <T> Specification<T> lessThanOrEqual(String attributeName, Object val) {
        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo(root.get(attributeName), val);
        };
    }*/

    public static <T> Specification<T> like(String attributeName, String val) {
        return (root, query, builder) -> {

            return builder.like(root.get(attributeName), val);
        };
    }

}
