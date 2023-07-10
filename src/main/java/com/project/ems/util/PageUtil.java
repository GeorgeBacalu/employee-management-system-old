package com.project.ems.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    public static String getSortField(Pageable pageable) {
        return pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(pageable.getSort().toString());
    }

    public static String getSortDirection(Pageable pageable) {
        return pageable.getSort().stream().findFirst().map(order -> order.getDirection().name().toLowerCase()).orElse(pageable.getSort().toString().toLowerCase());
    }

    public static int getStartIndexCurrentPage(int page, int size) {
        return page * size + 1;
    }

    public static long getEndIndexCurrentPage(int page, int size, long nrEmployees) {
        return Math.min((long) (page + 1) * size, nrEmployees);
    }

    public static int getStartIndexPageNavigation(int page, int nrPages) {
        return page > 0 ? (page < nrPages - 1 ? page - 1 : (page == 1 ? nrPages - 2 : nrPages - 3)) : 0;
    }

    public static int getEndIndexPageNavigation(int page, int nrPages) {
        return page > 0 ? (page < nrPages - 1 ? page + 1 : nrPages - 1) : (nrPages > 3 ? 2 : nrPages - 1);
    }
}
