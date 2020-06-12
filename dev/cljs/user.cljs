(ns cljs.user

  "For daydreaming in the repl."

  (:require [dvlopt.draw :as draw]))



;;;;;;;;;;
  

(defn draw-frame

  [ctx]

  (-> ctx
      (draw/color-fill "black")
      (draw/rect-fill 0 0 1000 400)
      (draw/color-stroke "white")
      (draw/line-width 2)
      draw/begin
      (draw/line 0 0 300 100)
      draw/stroke
      draw/begin
      (draw/color-stroke "green")
      ;(draw/line 500 200)
      (draw/bezier-1 600 0 900 50)
      (draw/line 600 300)
      draw/stroke
      ))



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
