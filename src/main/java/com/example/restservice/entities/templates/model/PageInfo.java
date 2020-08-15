package com.example.restservice.entities.templates.model;

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

  public boolean isHasNextPage() {
    return hasNextPage;
  }

  public boolean isHasPreviousPage() {
    return hasPreviousPage;
  }

  public int getNonShownInCurrentPageItemsCount() {
    return nonShownInCurrentPageItemsCount;
  }

  public int getNumberOfItemsInCurrentPage() {
    return numberOfItemsInCurrentPage;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getRequestedItemsPerPageNumber() {
    return requestedItemsPerPageNumber;
  }

  public int getTotalItemsCount() {
    return totalItemsCount;
  }

  public int getTotalPagesCount() {
    return totalPagesCount;
  }
}
