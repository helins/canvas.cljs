(ns cljs.user

  "For daydreaming in the repl."

  (:require [dvlopt.draw :as draw]))



;;;;;;;;;;
  

(defn draw-frame

  [ctx]

  )



(comment


  (def c
       (goog.dom.createElement "canvas"))

  (goog.dom.setProperties c
                          #js {"style" "height:400px; width:1000px;"})

  (goog.dom.appendChild js/document.body
                        c)


  (def ctx
       (draw/high-dpi (.getContext c
                                   "2d"
                                   #js {"alpha" false})))


  (do
    (frame)
    (def frame
      (draw/on-frame (fn draw [_timestamp]
                       (try
                         (draw-frame ctx)
                         true
                         (catch :default e
                           (js/console.log "err" e)
                           false))))))


  )
