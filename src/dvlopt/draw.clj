(ns dvlopt.draw

  ""

  {:author "Adam Helinski"}

  (:refer-clojure :exclude [->]))


;;;;;;;;;; Macros


(defmacro ->

  ""

  [[ctx & args] & forms]

  (let [pre-args  (cons ctx
                        args)
        syms      (take (count pre-args)
                        (repeatedly gensym))]
    `(let ~(vec (interleave syms
                            pre-args))
       ~@(map (fn add-pre-args [form]
                (if (symbol? form)
                  (list* form
                         syms)
                  (cons (first form)
                        (concat syms
                                (rest form)))))
              forms)
       ~(first syms))))



(defmacro subspace

  ""

  [[ctx] & forms]

  `(let [ctx#    ~ctx
         matrix# (dvlopt.draw/matrix ctx#)
         ret#    (do
                   ~@forms)]
     (dvlopt.draw/matrix ctx#
                         matrix#)
     ret#))
