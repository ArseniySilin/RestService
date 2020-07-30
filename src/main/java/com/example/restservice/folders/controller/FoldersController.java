package com.example.restservice.folders.controller;

import com.example.restservice.CommonResponse;
import com.example.restservice.Messages;
import com.example.restservice.folders.model.Folder;
import com.example.restservice.folders.repository.FoldersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoldersController {
  @Autowired
  FoldersRepository foldersRepository;

  @GetMapping(value = "/WorkGroups/{workGroupKey}/Folders/All")
  public ResponseEntity<CommonResponse> getFolders(@PathVariable String workGroupKey) {
    List<Folder> folderList =
      foldersRepository.findByWorkGroupKeyAndParentFolderKey(workGroupKey, null);
    return ResponseEntity.ok(
      new CommonResponse(Messages.SUCCESS.message, Messages.SUCCESS.code, null, folderList)
    );
  }
}
