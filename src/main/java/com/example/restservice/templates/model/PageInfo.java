package com.example.restservice.templates.model;

public class PageInfo {
  boolean hasNextPage;
  boolean hasPreviousPage;
  int nonShownInCurrentPageItemsCount;
  int numberOfItemsInCurrentPage;
  int pageNumber;
  int requestedItemsPerPageNumber;
  int totalItemsCount;
  int totalPagesCount;

  public PageInfo(
    boolean hasNextPage,
    boolean hasPreviousPage,
    int nonShownInCurrentPageItemsCount,
    int numberOfItemsInCurrentPage,
    int pageNumber,
    int requestedItemsPerPageNumber,
    int totalItemsCount,
    int totalPagesCount) {
    this.hasNextPage = hasNextPage;
    this.hasPreviousPage = hasPreviousPage;
    this.nonShownInCurrentPageItemsCount = nonShownInCurrentPageItemsCount;
    this.numberOfItemsInCurrentPage = numberOfItemsInCurrentPage;
    this.pageNumber = pageNumber;
    this.requestedItemsPerPageNumber = requestedItemsPerPageNumber;
    this.totalItemsCount = totalItemsCount;
    this.totalPagesCount = totalPagesCount;
  }
}
