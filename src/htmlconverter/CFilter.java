package htmlconverter; 
import java.io.FilenameFilter;
import java.io.File;
// Thanks to
// curry@IMPACT.xerox.com (Don Curry)
// for his simplifying suggestion here...
class CFilter2 implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return name.endsWith(".c");
	}
}