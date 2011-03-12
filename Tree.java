import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Tree extends JPanel {
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
        highline = insets.top + root_size.height + 10;
	width = insets.left + insets.right + root_size.width; 
	height = 0;
	
        if (left != null) {
	    Dimension left_size = left.getPreferredSize();
	    midline = Math.max(midline, insets.left + left_size.width);
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
            right.setBounds(midline + 5, highline,
			    right_size.width, right_size.height);
            width = midline + Math.max(root_size.width/2,
				       right_size.width+5) + insets.right;
            height = Math.max(height,
			      right.getY() + right.getHeight() + insets.bottom);
            add(right);
	}
	setPreferredSize(new Dimension(width + 30, height + 20));
    }
    
    public static Tree parseTree(BufferedReader in){   
	String line;
	try {
	    line = in.readLine();
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
	
	if(line.isEmpty()){
	    return null;
	} else {
	    Tree left = parseTree(in);
	    Tree right = parseTree(in);
	    JLabel node = new JLabel(line);
	    node.setFont(new Font("Serif", Font.BOLD, 16));
	    node.setHorizontalTextPosition(JLabel.CENTER);
	    node.setVerticalTextPosition(JLabel.BOTTOM);
	    
	    node.setBorder(new LineBorder(Color.BLACK, 2, true));
	    return new Tree(node, left, right);
	}
    }
    
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	if (left != null) {
	    g2.drawLine(left.getX() + left.midline, left.getY(),
			left.getX() + left.getWidth(),
			root.getY() + root.getHeight());
	}
	
	if (right != null) {
	    g2.drawLine(right.getX() + right.midline, right.getY(),
			right.getX(), root.getY() + root.getHeight());
	}
	setOpaque(true);
	setBackground(Color.white);
	
    }
    
    public static void main(String[] args) {
	BufferedReader in =
	    new BufferedReader(new InputStreamReader(System.in));
	Tree tree = parseTree(in);
	JFrame frame = new JFrame("Tree View");
	JScrollPane scrollPane = new JScrollPane(tree);
	scrollPane.setOpaque(true);
	scrollPane.getViewport().setBackground(Color.white);
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setSize(tree.getSize());
	frame.setLayout(new BorderLayout()); //keeps everything centered on resize
	frame.getContentPane().add(scrollPane);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
    }
}
