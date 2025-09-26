package com.backend.backend.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;

public class PageDTO<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public PageDTO(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PageDTO<?> pageDTO = (PageDTO<?>) o;
        return getPageNumber() == pageDTO.getPageNumber() && getPageSize() == pageDTO.getPageSize() && getTotalElements() == pageDTO.getTotalElements() && getTotalPages() == pageDTO.getTotalPages() && Objects.equals(getContent(), pageDTO.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getPageNumber(), getPageSize(), getTotalElements(), getTotalPages());
    }

}
