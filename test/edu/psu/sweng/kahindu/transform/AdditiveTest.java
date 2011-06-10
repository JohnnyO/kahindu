package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.CharacterizationTest;
import edu.psu.sweng.kahindu.image.KahinduImage;
import gui.TopFrame;

public class AdditiveTest extends CharacterizationTest {

	@Override
	public TopFrame constructLegacyImage(TopFrame topFrame) {
		topFrame.add10();
		return topFrame;
	}

	@Override
	public KahinduImage constructRefactoredImage(KahinduImage img) {
		AdditiveTransformer at = new AdditiveTransformer(10);
		return at.transform(img);
	}

}
