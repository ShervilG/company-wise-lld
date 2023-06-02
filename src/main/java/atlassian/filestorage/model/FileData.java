package atlassian.filestorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileData {
  private String fileName;
  private int fileSize;
  private String collection;
}
