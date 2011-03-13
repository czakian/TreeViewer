;;copied over from the c211-lib.ss file
;; defined the following internal record and tree ADT (sm)
(define-record tnode (data left right)) 
(define-record empty ())

(define empty-tree
  (lambda ()
    (make-empty)))

(define empty-tree? 
  (lambda (x) 
    (empty? x)))

(define tree 
  (lambda (data left right)
    (make-tnode data left right)))

(define node? tnode?)

(define tree?
  (lambda (tr)
    (or (empty-tree? tr) 
	(and (node? tr)
	          (tree? (left-subtree tr))
		       (tree? (right-subtree tr))))))

(define root-value
  (lambda (tr)
    (tnode-data tr)))

(define left-subtree
  (lambda (tr)
    (tnode-left tr)))

(define right-subtree
  (lambda (tr)
    (tnode-right tr)))

(define leaf?
  (lambda (tr)
    (and (node? tr)
	  (empty-tree? (left-subtree tr))
	   (empty-tree? (right-subtree tr)))))

(define leaf
  (lambda (x)
    (tree x (empty-tree) (empty-tree))))
