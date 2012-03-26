package no.ntnu.fp.util;

import java.awt.GridBagConstraints;

public class GridBagHelper {
	public static GridBagConstraints setConstraints(GridBagConstraints constr, int x, int y) {
		constr.gridx = x;
		constr.gridy = y;
		return constr;
	}
}
