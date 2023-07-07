package com.project.ems.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageWrapper<T> {

    @JsonIgnore
    private Pageable pageable;

    private List<T> content;

    private boolean last;

    private int totalPages;

    private long totalElements;

    private int size;

    private int number;

    private Sort sort;

    private boolean first;

    private int numberOfElements;

    private boolean empty;

    public PageWrapper(List<T> content) {
        this.content = content;
    }
}
