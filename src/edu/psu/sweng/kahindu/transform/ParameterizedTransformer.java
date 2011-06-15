package edu.psu.sweng.kahindu.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.psu.sweng.kahindu.image.KahinduImage;

public interface ParameterizedTransformer extends Transformer<KahinduImage> {

	public Map<String, Double> getDefaultParameters();

	public void setParameter(String key, double value);

}
