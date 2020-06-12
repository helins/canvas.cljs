(ns dvlopt.draw

  ""

  {:author "Adam Helinski"})


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




(defmacro style-preserve

  ""

  [ctx & forms]

  `(let [ctx# ~ctx]
     (dvlopt.draw/style-save ctx#)
     ~@forms
     (dvlopt.draw/style-pop ctx#)))



(defmacro with-translation

  ""

  [[ctx x y] & forms]

  `(let [ctx# ~ctx]
     (dvlopt.draw/translate ctx#
                            ~x
                            ~y)
     ~@forms
     (dvlopt.draw/translate ctx#
                            (- ~x)
                            (- ~y))))
