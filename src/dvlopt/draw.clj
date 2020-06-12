(ns dvlopt.draw

  ""

  {:author "Adam Helinski"}

  (:refer-clojure :exclude [->]))


;;;;;;;;;; Macros


(defmacro chain

  ""

  [[ctx state] & forms]

  (let [sym-ctx   (gensym)
        sym-state (gensym)
        nest      (fn nest [[form & forms]]
                    (when form
                      `(let [~sym-state ~(if (symbol? form)
                                           (list form
                                                 sym-ctx
                                                 sym-state)
                                           (list* (first form)
                                                  sym-ctx
                                                  sym-state
                                                  (rest form)))]
                         ~(nest forms))))]
                                                

    `(let [~sym-ctx   ~ctx
           ~sym-state ~state]
       ~(nest forms))))


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
