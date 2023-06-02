package atlassian.filestorage.service;

import atlassian.filestorage.model.FileData;

import java.util.List;

public interface FileService {
  int getTotalSizeOfFilesProcessed(List<FileData> fileDataList);
  List<String> calculateTopKCollections(List<FileData> fileDataList, int k);
}
