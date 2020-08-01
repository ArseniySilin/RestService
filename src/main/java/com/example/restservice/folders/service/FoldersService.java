package com.example.restservice.folders.service;

import com.example.restservice.folders.model.CommonFolderRequest;
import com.example.restservice.folders.model.Folder;
import com.example.restservice.folders.repository.FoldersRepository;
import com.example.restservice.users.model.User;
import com.example.restservice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Service
public class FoldersService {
  @Autowired
  FoldersRepository foldersRepository;

  @Autowired
  UsersService usersService;

  public List<Folder> getFolders(String workGroupKey) {
    return foldersRepository.findByWorkGroupKeyAndParentFolderKey(workGroupKey, null);
  }

  public void saveFolder(String token, CommonFolderRequest commonFolderRequest) {
    String name = commonFolderRequest.getName();
    int folderType = commonFolderRequest.getFolderType();
    String parentFolderKey = commonFolderRequest.getParentFolderKey();
    String workGroupKey = commonFolderRequest.getWorkgroupKey();
    User user = usersService.getAuthorizedUser(token, workGroupKey);
    String key = UUID.randomUUID().toString();
    String createdUserFirstName = user.getFirstName();
    String createdUserLastName = user.getLastName();
    String createdUserName = user.getUsername();
    String createdUserKey = user.getKey();
    LocalDateTime createdDateTimeUtc = LocalDateTime.now();
    LocalDateTime updatedDateTimeUtc = LocalDateTime.now();
    String parentFolderName = null;

    if (parentFolderKey != null) {
      Optional<Folder> optionalParentFolder = foldersRepository.findById(parentFolderKey);

      if (optionalParentFolder.isPresent()) {
        Folder parentFolder = optionalParentFolder.get();
        parentFolderName = parentFolder.getName();
      }
    }

    Folder folder = new Folder(
      key,
      name,
      createdUserFirstName,
      createdUserLastName,
      createdUserName,
      createdUserKey,
      createdDateTimeUtc,
      updatedDateTimeUtc,
      folderType,
      parentFolderKey,
      parentFolderName,
      workGroupKey
    );

    foldersRepository.save(folder);
  }

  public Folder updateFolder(String token, String workGroupKey, String folderKey, CommonFolderRequest commonFolderRequest) {
    String name = commonFolderRequest.getName();
    User user = usersService.getAuthorizedUser(token, workGroupKey);

    if (user == null) return null;

    foldersRepository.setFolderNameByKey(name, folderKey);

    return foldersRepository.findById(folderKey).orElse(null);
  }

  public void deleteFolder(String token, String key, String workGroupKey) {
    User user = usersService.getAuthorizedUser(token, workGroupKey);

    if (user != null) foldersRepository.deleteById(key);
  }
}
