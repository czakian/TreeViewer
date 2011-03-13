import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Tree extends JPanel {
    private static final  String IMPROPER_TREE = "!$&";
    private JComponent root;
    private Tree left, right;
    public int midline, width, height, highline; // TODO: dynamic midline calculation
    
    public Tree(JComponent root, Tree left, Tree right) {
        this.root = root;
        this.left = left;
        this.right = right;
	
	setLayout(null);
	Insets insets = getInsets();
        Dimension root_size = root.getPreferredSize();
        midline = insets.left + root_size.width / 2 + 10;
        highline = insets.top + root_size.height + 12;
	width = insets.left + insets.right + root_size.width + 30; 
	height = 0;
	
        if (left != null) {
	    Dimension left_size = left.getPreferredSize();
	    midline = Math.max(midline, insets.left + left_size.width);
	    left.setBounds(insets.left, highline, 
			   left_size.width, left_size.height) ;
	    width = midline + root_size.width/2 + 30; 
	    height = Math.max(height,
			      left.getY() + left.getHeight() + insets.bottom);
	    add(left);
        }
	
        root.setBounds(midline - root_size.width/2, insets.top,
		       root_size.width, root_size.height);
        height = Math.max(height,
			  root.getY() + root.getHeight() + insets.bottom);
        add(root);
	
        if (right != null) {
            Dimension right_size = right.getPreferredSize();
            right.setBounds(midline + 5, highline,
			    right_size.width, right_size.height);
            width = midline + Math.max(root_size.width/2,
				       right_size.width+5) + insets.right + 30;
            height = Math.max(height,
			      right.getY() + right.getHeight() + insets.bottom);
            add(right);
	}
	setPreferredSize(new Dimension(width, height));
    }
    
    public static Tree parseTree(BufferedReader in){   
	String line;
	boolean improperTree = false;
	Color color = Color.BLACK;
	try {
	    line = in.readLine();
	    if(line.isEmpty()){
		return null;
	    } else {
		if (line.equals(IMPROPER_TREE)) { //this is just a flag. Scheme side does the checking.
		    improperTree = true;
		    line = in.readLine();
		    JOptionPane.showMessageDialog
			(null, "Improper tree at item: "  + line, "Improper Tree!",  JOptionPane.ERROR_MESSAGE); 
		}
		Tree left = parseTree(in);
		Tree right = parseTree(in);
		JLabel node = new JLabel(" " + line + " ", JLabel.CENTER);
		node.setFont(new Font("MONOSPACE", Font.PLAIN, 14));
		node.setVerticalTextPosition(JLabel.BOTTOM);
		if(improperTree) {
		    color = Color.RED;
		} 
		node.setBorder(new LineBorder(color, 2, true));
		node.setForeground(color);
		return new Tree(node, left, right);
	    }
	} catch (IOException e) {
	    JOptionPane.showMessageDialog
		(null, "Error in read from stdin", "IO Exception!",  JOptionPane.ERROR_MESSAGE); 
	    e.printStackTrace();
	    return null;
	}
    }
    
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (left != null) {
	    g.drawLine(left.getX() + left.midline, left.getY(),
			left.getX() -3 + left.getWidth(),
			root.getY() + root.getHeight());
	}
	
	if (right != null) {
	    g.drawLine(right.getX() + right.midline, right.getY(),
			right.getX() -3, root.getY() + root.getHeight());
	}
	setOpaque(true);
	setBackground(Color.WHITE);
    }
    
    public static void main(String[] args) {
	BufferedReader in =
	    new BufferedReader(new InputStreamReader(System.in));
	Tree tree = parseTree(in);
	if(tree != null) {
	    JFrame frame = new JFrame("Tree View");
	    JScrollPane scrollPane = new JScrollPane
		(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setOpaque(true);
	    scrollPane.getViewport().setBackground(Color.white);
	    scrollPane.setSize(tree.getSize());
	    frame.getContentPane().add(scrollPane);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	} else {
	    JOptionPane.showMessageDialog
		(null, "Tree exception in read: \n  Expected: Tree \n  Read: Empty-Tree",
		 "Error!", JOptionPane.ERROR_MESSAGE);
	    throw new NullPointerException("newline sent in as first input");
	}
    }
}

