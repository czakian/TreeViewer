JFAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

class_path := /home/czakian/projects/Treeviewer

CLASSES = \
	InvalidTreeException.java \
	Tree.java 	


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class)
