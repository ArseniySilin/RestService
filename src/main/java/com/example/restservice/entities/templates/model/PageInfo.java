package com.example.restservice.entities.templates.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PageInfo {
  @Getter
  boolean hasNextPage;
  @Getter
  boolean hasPreviousPage;
  @Getter
  int nonShownInCurrentPageItemsCount;
  @Getter
  int numberOfItemsInCurrentPage;
  @Getter
  int pageNumber;
  @Getter
  int requestedItemsPerPageNumber;
  @Getter
  int totalItemsCount;
  @Getter
  int totalPagesCount;
}
