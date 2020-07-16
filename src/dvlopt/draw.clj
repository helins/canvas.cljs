(ns dvlopt.draw

  ;; Macros.

  {:author "Adam Helinski"})


;;;;;;;;;; Macros


(defmacro subspace

  "The transformation matrix is saved at the beginning of the macro and restored at the end so that any spatial
   transformations are forgotten.

   Specially useful when drawing standalone paths which typically require translation.

   Cf. [[matrix]]
	   [[path]]"

  [[ctx] & forms]

  `(let [ctx#    ~ctx
         matrix# (dvlopt.draw/matrix ctx#)
         ret#    (do
                   ~@forms)]
     (dvlopt.draw/matrix ctx#
                         matrix#)
     ret#))
