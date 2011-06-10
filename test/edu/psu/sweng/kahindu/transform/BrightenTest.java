package edu.psu.sweng.kahindu.transform;

import edu.psu.sweng.kahindu.CharacterizationTest;
import edu.psu.sweng.kahindu.image.KahinduImage;
import gui.TopFrame;

public class BrightenTest extends CharacterizationTest {

	@Override
	public TopFrame constructLegacyImage(TopFrame topFrame) {
		topFrame.powImage(1.5);
		return topFrame;
	}

	@Override
	public KahinduImage constructRefactoredImage(KahinduImage img) {
		PowerTransformer pt = new PowerTransformer(1.5); 
		return pt.transform(img);
	}

}
