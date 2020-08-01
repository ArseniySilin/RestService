package com.example.restservice.folders.service;

import com.example.restservice.folders.model.CreateFolderRequest;
import com.example.restservice.folders.model.Folder;
import com.example.restservice.folders.repository.FoldersRepository;
import com.example.restservice.users.model.User;
import com.example.restservice.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void saveFolder(String token, CreateFolderRequest createFolderRequest) {
    String name = createFolderRequest.getName();
    int folderType = createFolderRequest.getFolderType();
    String parentFolderKey = createFolderRequest.getParentFolderKey();
    String workGroupKey = createFolderRequest.getWorkgroupKey();
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

  public void deleteFolder(String token, String key, String workGroupKey) {
    User user = usersService.getAuthorizedUser(token, workGroupKey);

    if (user != null) foldersRepository.deleteById(key);
  }
}
