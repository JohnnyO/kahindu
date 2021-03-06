 package htmlconverter;
 import java.io.File;
import java.io.FilenameFilter;
 
 class WildFilter implements FilenameFilter{
  private String suffix;
  public WildFilter (String suffix_) {
    suffix=suffix_;
  }
  public boolean accept(File dir, String name) {
    return name.endsWith(suffix);
  }
}

  class DirFilter implements FilenameFilter {
  public boolean accept(File dir, String name) {
    return new File(dir, name).isDirectory();
  }
}
