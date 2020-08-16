package com.example.restservice.entities.folders.service;

import com.example.restservice.entities.users.service.UsersService;
import com.example.restservice.execptions.EntityNotFoundException;
import com.example.restservice.entities.folders.model.CommonFolderRequest;
import com.example.restservice.entities.folders.model.Folder;
import com.example.restservice.entities.folders.repository.FoldersRepository;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FoldersService {
  @Autowired
  FoldersRepository foldersRepository;

  @Autowired
  UsersService usersService;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  public List<Folder> getFolders(String workGroupKey, String parentFolderKey) {
    return foldersRepository.findByWorkGroupKeyAndParentFolderKey(workGroupKey, parentFolderKey);
  }

  public Folder getFolder(String workGroupKey, String key) {
    Folder folder = foldersRepository.findByWorkGroupKeyAndKey(workGroupKey, key);

    if (folder == null) {
      throw new EntityNotFoundException(Folder.class);
    }

    return folder;
  }

  public void saveFolder(String token, CommonFolderRequest commonFolderRequest) {
    String name = commonFolderRequest.getName();
    int folderType = commonFolderRequest.getFolderType();
    String parentFolderKey = commonFolderRequest.getParentFolderKey();
    String workGroupKey = commonFolderRequest.getWorkgroupKey();
    String userName = jwtTokenUtil.getUsernameFromToken(token);
    User user = usersService.getUser(userName);

    String key = UUID.randomUUID().toString();
    String createdUserFirstName = user.getFirstName();
    String createdUserLastName = user.getLastName();
    String createdUserName = user.getUserName();
    String createdUserKey = user.getKey();
    LocalDateTime createdDateTimeUtc = LocalDateTime.now();
    LocalDateTime updatedDateTimeUtc = LocalDateTime.now();
    String parentFolderName = null;

    if (parentFolderKey != null) {
      Folder parentFolder = foldersRepository.findById(parentFolderKey).orElse(null);

      if (parentFolder != null) {
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

  @Transactional
  public Folder updateFolder(String workGroupKey, String folderKey, CommonFolderRequest commonFolderRequest) {
    foldersRepository.setFolderNameByWorkGroupKeyAndKey(commonFolderRequest.getName(), workGroupKey, folderKey);

    return foldersRepository.findById(folderKey).orElse(null);
  }

  public void deleteFolder(String key, String workGroupKey) throws EntityNotFoundException {
    if (foldersRepository.existsById(key)) {
      foldersRepository.deleteById(key);
    } else {
      throw new EntityNotFoundException(Folder.class);
    }
  }
}
