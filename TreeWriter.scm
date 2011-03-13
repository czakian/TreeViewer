(define display-tree
  (lambda (tr)
    (let* ([p (process "java -jar /home/czakian/TreeViewer.jar")]
	   [in (car p)] [out (cadr p)])
      (let loop ([tr tr])
	(if (empty-tree? tr)
	    (write-char #\newline out)
	    (begin
	      (write (root-value tr) out)
	      (write-char #\newline out)
	      (loop (left-subtree tr))
	      (loop (right-subtree tr)))))
      (write-char #\newline out)
      (flush-output-port out)
      (close-output-port out)
      (close-input-port in))))
