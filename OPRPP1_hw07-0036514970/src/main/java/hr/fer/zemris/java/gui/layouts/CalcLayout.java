package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CalcLayout implements LayoutManager2 {
	int space;
	Map<RCPosition, Component> components = new TreeMap<RCPosition, Component>();
	

	public CalcLayout(int space) {
		super();
		this.space = space;
	}
	
	public CalcLayout() {
		this(0);
	}



	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			if(entry.getValue().equals(comp)) {
				components.remove(entry.getKey());
				break;
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimenzionByArgument(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimenzionByArgument(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension dimension = parent.getSize();
		int x = insets.left;
		int y = insets.top;
		int[] widths = generate1DArray(dimension.width - insets.left - insets.right - 6 * space, 7);
		int[] xs = generatePositions(widths, x);
		int[] heigths = generate1DArray(dimension.height - insets.top - insets.bottom - 4 * space, 5);
		int[] ys = generatePositions(heigths, y);
		for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition position = entry.getKey();
			if(position.equals(new RCPosition(1, 1))) {
				int w  = 0;
				for (int i = 0; i < 5.; i++) {
					w += widths[i];
					w += space;
				}
				w -= space;
				entry.getValue().setBounds(xs[position.getColumn()-1], ys[position.getRow()-1], w, heigths[position.getRow()-1]);
				continue;
			}
			
			entry.getValue().setBounds(xs[position.getColumn()-1], ys[position.getRow()-1], widths[position.getColumn()-1], heigths[position.getRow()-1]);
		}
	}


	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) throw new NullPointerException();
		
		RCPosition position;
		if(constraints.getClass() == String.class) position = RCPosition.parse((String) constraints);
		else if(constraints.getClass() == RCPosition.class) position = (RCPosition) constraints;
		else throw new IllegalArgumentException("ograničenje(constraints) mora po tipu biti ili String ili RCPosition");
		
		 if(components.containsKey(position)) throw new CalcLayoutException("Na pozicijij: " + position + " postoji već komponenta.");
		 if(components.containsValue(comp)) throw new CalcLayoutException("Komponenta: " + comp + " je več dodana.");
		 
		 components.put(position, comp);
		
		
		
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getDimenzionByArgument(parent, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	private Dimension getDimenzionByArgument(Container parent, Function<Component, Dimension> getter) {
		Dimension dim = new Dimension(0, 0);
		components.forEach((k, v) -> {
			Dimension compDim = getter.apply(v);
			if(compDim == null) return;
			if (k.equals(new RCPosition(1, 1))) compDim.width =  (int) ((compDim.width - 4 * space) / 5.0);
			if(compDim.height > dim.height) dim.height = compDim.height;
			if(compDim.width > dim.width) dim.width = compDim.width;
		});
		Insets insets = parent.getInsets();
		dim.width = insets.left + 7 * dim.width + 6 * space + insets.right;
		dim.height = insets.top + 5 * dim.height + 4* space + insets.bottom;
		return dim;
		
	}
	public static int[] generate1DArray(int dim, int part) {
		int[] parts = new int[part];
		int size = dim /part;
		int mode = dim % part;
		
		if(mode == 0) {
			Arrays.fill(parts, size);
			return parts;
		}
		int a = size;
		int b = size +1;
		if(mode > part - mode) {
			a = a + b;
			b = a - b;
			a = a - b;
			mode = part - mode;
		}
		Arrays.fill(parts, a);
		int i = 1 - mode % 2;
		for (int j = 0; j < mode; j++) {
			if(j % 2 == i) {
				parts[part/2 - j] = b;
				parts[part/2 + j] = b;
			}
		}
		return parts;
		
		
		
	}
	

	private int[] generatePositions(int[] sizes, int offset) {
		int[] positions = new int[sizes.length];
		positions[0] = offset;
		for (int i = 1; i < sizes.length; i++) {
			positions[i] = positions[i - 1] + sizes[i-1] + space;
		}
		return positions;
	}


}
