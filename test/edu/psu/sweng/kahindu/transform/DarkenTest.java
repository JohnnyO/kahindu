package edu.psu.sweng.kahindu.transform;


import edu.psu.sweng.kahindu.CharacterizationTest;
import edu.psu.sweng.kahindu.image.KahinduImage;
import gui.TopFrame;

public class DarkenTest extends CharacterizationTest {

	@Override
	public TopFrame constructLegacyImage(TopFrame topFrame) {
		topFrame.powImage(0.9);
		return topFrame;
	}

	@Override
	public KahinduImage constructRefactoredImage(KahinduImage img) {
		PowerTransformer pt = new PowerTransformer(0.9); 
		return pt.transform(img);
	}

}
