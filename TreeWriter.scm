(define display-tree
  (lambda (tr)
    (let* ([p (process "java -jar TreeViewer.jar")]
	   [in (car p)] [out (cadr p)][invalid-tree '!$&])
      (let loop ([tr tr])
	(cond
          [(empty-tree? tr) 
;;           (write "E" out)
           (write-char #\newline out)]
          [(leaf? tr)
           (write 'L out)
           (write (root-value tr) out)
           (write-char #\newline out)
           (loop (left-subtree tr))
           (loop (right-subtree tr))]
          [(tree? tr) 
           (write 'T out)
           (write (root-value tr) out)
           (write-char #\newline out)
           (loop (left-subtree tr))
           (loop (right-subtree tr))]
          [(node? tr)
           (write 'N out)
           (write (root-value tr) out)
           (write-char #\newline out)
           (loop (left-subtree tr))
           (loop (right-subtree tr))]
          [else 
             (write 'I out)
             (write tr out)
             (write-char #\newline out)
             (write-char #\newline out)
             (write-char #\newline out)]))
        (write-char #\newline out)
        (flush-output-port out)
        (close-output-port out)
        (close-input-port in))))
