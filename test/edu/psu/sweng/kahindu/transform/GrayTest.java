package edu.psu.sweng.kahindu.transform;



import edu.psu.sweng.kahindu.CharacterizationTest;
import edu.psu.sweng.kahindu.image.KahinduImage;
import edu.psu.sweng.kahindu.image.TransformedImage;
import gui.TopFrame;

public class GrayTest extends CharacterizationTest {


	@Override
	public TopFrame constructLegacyImage(TopFrame topFrame) {
		topFrame.gray();
		return topFrame;
	}

	@Override
	public KahinduImage constructRefactoredImage(KahinduImage img) {
		GrayTransformer gt = new GrayTransformer();
		return gt.transform(img);
	}
	
}
