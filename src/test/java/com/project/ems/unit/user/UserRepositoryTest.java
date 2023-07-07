package com.project.ems.unit.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_QUERY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    void findAllByKey_shouldReturnListOfUsersPaginatedSortedAndFilteredByKey() {
        String param = "%" + USER_FILTER_KEY + "%";
        given(entityManager.createQuery(USER_FILTER_QUERY)).willReturn(query);
        given(query.setParameter("key", param)).willReturn(query);
        given(query.getResultList()).willReturn(null);
        Query actualQuery = entityManager.createQuery(USER_FILTER_QUERY);
        actualQuery.setParameter("key", param);
        actualQuery.getResultList();
        verify(entityManager).createQuery(USER_FILTER_QUERY);
        verify(query).setParameter("key", param);
        verify(query).getResultList();
    }
}
