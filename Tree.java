import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.border.*;

/**
   This class paint will paint a tree.
*/
public class Tree extends JPanel {
    private static final int HEIGHT_PADDING = 10; //this is how far lower each node is displayed at each level
    private static final int MIDLINE_PADDING = 5; //this is the separation of the nodes laterally
    private static final String TREE = "T";
    private static final String LEAF = "L";
    private static final String INVALID = "I";
    private static final String EMPTY = "E";
    private JComponent root;
    private Tree left, right;
    public int midline, width, height, highline; // TODO: dynamic midline calculation
  
 /**
     Tree method takes a root, and left and right. Then reset the public variable
     root, left and right. After that, this method will set the boundary of the
     graph of the tree. Tree method checks the left subtree first, if it is not null,
     tree method draws the left subtree. Then it draws root, and right subtree.
     @param root JComponent
     @param left Tree
     @param right Tree
  */  
    public Tree(JComponent root, Tree left, Tree right) {
        this.root = root;
        this.left = left;
        this.right = right;

	setLayout(null);
        Insets insets = getInsets();
        Dimension root_size = root.getPreferredSize();
        midline = insets.left + root_size.width / 2;
        int highline = insets.top + root_size.height + HEIGHT_PADDING;
        int width = insets.left + insets.right + root_size.width;
        int height = 0;

        if (left != null) {
	    Dimension left_size = left.getPreferredSize();
	    midline = Math.max(midline, insets.left + left_size.width + MIDLINE_PADDING);
	    left.setBounds(insets.left, highline,
			   left_size.width, left_size.height);
	    width = midline + root_size.width/2;
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
            right.setBounds(midline + MIDLINE_PADDING, highline,
			    right_size.width, right_size.height);
            width = midline + Math.max(root_size.width/2,
				       right_size.width + MIDLINE_PADDING) + insets.right;
            height = Math.max(height,
			      right.getY() + right.getHeight() + insets.bottom);
            add(right);
	}
	setPreferredSize(new Dimension(width, height));
    }
    
/**
   ParseTree takes the input, check the line if it is empty. If it isn't,
   create left subtree and right subtree, run ParseTree as a recursive.
   After that, returns a new tree with a JLabel root and left, right subtree.
   If the input line is empty, this method will catch the IOException
   and returns null.
*/   
    public static Tree parseTree(BufferedReader in){   
	String line;
	Color color = Color.BLACK;
	try {
	    line = in.readLine();
	} catch (IOException e) {
	    JOptionPane.showMessageDialog
		(null, "Error in read from stdin", "IO Exception!",  JOptionPane.ERROR_MESSAGE); 
	    return null;
	}
	
	if(line.isEmpty()){
	    return null;
	} else {
	    String type = line.substring(0, 1);
	    Tree left = parseTree(in);
	    Tree right = parseTree(in);

	    if(type.equals(INVALID)) {
		color = Color.RED;
	    }	    
	    JLabel node = new JLabel(line.substring(1,line.length()), JLabel.CENTER);
	    node.setFont(new Font("MONOSPACE", Font.PLAIN, 14));
	    node.setVerticalTextPosition(JLabel.BOTTOM);
	    node.setBorder(new LineBorder(color, 2, true));
	    node.setForeground(color);
	    return new Tree(node, left, right);
	}
    }
 

  /**
     PaintComponent method takes a Graphics, then call the super method.
     Then check both left subtree and right subtree, if left subtree
     is not empty, then draw left subtree. So do the right subtree.
  */       
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (left != null) {
	    g.drawLine(left.getX() + left.midline, left.getY(),
		       left.getX() + left.getWidth(),
		       root.getY() + root.getHeight());
	}
	if (right != null) {
	    g.drawLine(right.getX() + right.midline, right.getY(),
		       right.getX(), root.getY() + root.getHeight());
	}

   	setOpaque(true);
	setBackground(Color.WHITE);
    }
    
 /**
     Main method takes the tree input, makes a new tree in java.
     Then create a JFrame bucket, paint it, pack, then set it to visible.
  */   
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

