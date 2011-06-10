package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.CharacterizationTest;
import edu.psu.sweng.kahindu.image.KahinduImage;
import gui.TopFrame;

public class NegateTest extends CharacterizationTest {

	@Override
	public TopFrame constructLegacyImage(TopFrame topFrame) {
		topFrame.negate();
		return topFrame;
	}

	@Override
	public KahinduImage constructRefactoredImage(KahinduImage img) {
		NegateTransformer nt = new NegateTransformer();
		return nt.transform(img);
	}

}
