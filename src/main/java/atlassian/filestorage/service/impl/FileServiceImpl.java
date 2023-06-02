package atlassian.filestorage.service.impl;

import atlassian.filestorage.model.FileData;
import atlassian.filestorage.service.FileService;

import java.util.*;

public class FileServiceImpl implements FileService {
  @Override
  public int getTotalSizeOfFilesProcessed(List<FileData> fileDataList) {
    return fileDataList.stream().map(FileData::getFileSize)
        .reduce(0, Integer::sum);
  }

  @Override
  public List<String> calculateTopKCollections(List<FileData> fileDataList, int k) {
    Map<String, Integer> collectionToCollectionSizeMap = new HashMap<>();
    fileDataList.forEach(fileData -> {
      collectionToCollectionSizeMap.merge(fileData.getCollection(), fileData.getFileSize(), Integer::sum);
    });

    if (collectionToCollectionSizeMap.size() == 0) {
      return null;
    }

    Queue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(collectionToCollectionSizeMap::get));
    collectionToCollectionSizeMap.forEach((key, value) -> {
      if (priorityQueue.size() < k) {
        priorityQueue.add(key);
      } else {
        int top = collectionToCollectionSizeMap.get(priorityQueue.remove());
        if (top < value) {
          priorityQueue.add(key);
        }
      }
    });

    return new ArrayList<>(priorityQueue);
  }
}
