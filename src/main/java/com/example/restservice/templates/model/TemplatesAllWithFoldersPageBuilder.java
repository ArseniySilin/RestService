package com.example.restservice.templates.model;

import com.example.restservice.common.AllWithFoldersEntity;
import com.example.restservice.folders.model.Folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TemplatesAllWithFoldersPageBuilder {
  private List<Template> templates;
  private List<Folder> folders;
  private int itemsPerPage;
  private String columnToOrderBy;
  private String orderBy;
  private final String ORDER_BY_CREATION = "1";
  private final String ORDER_BY_UPDATE = "2";
  private final String ORDER_BY_AUTHOR = "3";
  private final String ORDER_BY_NAME = "4";
  private final String ASC = "0";
  private final String DESC = "1";

  public TemplatesAllWithFoldersPageBuilder(
    List<Template> templates,
    List<Folder> folders,
    String itemsPerPage,
    String columnToOrderBy,
    String orderBy
    ) throws NumberFormatException {
    this.templates = templates;
    this.folders = folders;

    if (columnToOrderBy == null) {
      this.columnToOrderBy = ORDER_BY_NAME;
    } else {
      this.columnToOrderBy = columnToOrderBy;
    }

    if (orderBy == null) {
      this.orderBy = ASC;
    } else {
      this.orderBy = orderBy;
    }

    if (itemsPerPage == null) {
      this.itemsPerPage = 15;
    } else {
      this.itemsPerPage = Integer.parseInt(itemsPerPage);
    }

    this.sort();
  }

  private int sort() {
    // use comparing by entity name by default
    Comparator<AllWithFoldersEntity> comparator = Comparator.comparing(AllWithFoldersEntity::getName);

    switch (this.columnToOrderBy) {
      case ORDER_BY_CREATION: {
        comparator = Comparator.comparing(AllWithFoldersEntity::getCreatedDateTimeUtc);
        break;
      }
      case ORDER_BY_UPDATE: {
        comparator = Comparator.comparing(AllWithFoldersEntity::getUpdatedDateTimeUtc);
        break;
      }
      case ORDER_BY_AUTHOR: {
        comparator = Comparator.comparing(AllWithFoldersEntity::getCreatedUserName);
        break;
      }
    }

    if (this.orderBy.equals(DESC)) comparator.reversed();
    if (templates != null) Collections.sort(templates, comparator);
    if (folders != null) Collections.sort(folders, comparator);

    return 0;
  }

  public TemplatesAllWithFoldersPage getPage(int page) {
    int totalFolders = folders.size();
    int totalTemplates = templates.size();
    int totalItems = totalFolders + totalTemplates;
    int startPosition = itemsPerPage * (page - 1);
    int endPosition = totalItems - startPosition + 1 >= itemsPerPage
      ? startPosition + itemsPerPage
      : totalItems - startPosition;

    List<Folder> foldersPerPage = new ArrayList<>();
    List<Template> templatesPerPage = new ArrayList<>();
    List<Object> entities = new ArrayList<>(folders);
    entities.addAll(templates);

//    System.out.println("entities.size " + entities.size());
//    System.out.println("startPosition " + startPosition);
//    System.out.println("endPosition " + endPosition);
//    System.out.println("itemsPerPage " + itemsPerPage);
//    System.out.println("totalItems " + totalItems);

    for (int i = startPosition; i < endPosition; i++) {
      Object entity = entities.get(i);

      if (entity.getClass() == Folder.class) {
        Folder f = (Folder) entity;
        foldersPerPage.add(f);
      }

      if (entity.getClass() == Template.class) {
        Template t = (Template) entity;
        templatesPerPage.add(t);
      }
    }

    boolean hasNextPage = totalItems > endPosition;
    boolean hasPreviousPage = startPosition > itemsPerPage;
    int totalPagesCount = (int) Math.ceil(totalItems / itemsPerPage);

    TemplatesAllWithFoldersPage twfp = new TemplatesAllWithFoldersPage(
      templatesPerPage,
      foldersPerPage,
      new PageInfo(
        hasNextPage,
        hasPreviousPage,
        0, // TODO: not used, remove
        0, // TODO: not used, remove
        page,
        0, // TODO: not used, remove
        totalItems,
        totalPagesCount
      )
    );


    return twfp;
  }
}
