;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at https://mozilla.org/MPL/2.0/.


(ns helins.canvas.dev

  "For daydreaming in the repl."

  (:require [helins.canvas :as canvas]))


;;;;;;;;;;
  

(def img
     (js/Image.))



(set! (.-src img)
      "https://static01.nyt.com/images/2014/01/28/science/28SLOT_SPAN/28SLOT-articleLarge.jpg?quality=75&auto=webp&disable=upscale")



(defn draw-frame

  [ctx]

  (-> ctx
      (canvas/color-fill (canvas/grad-linear ctx 0 0 1000 400 [[0   "black"]
                                                               [0.5 "blue"]
                                                               [1   "black"]]))
      (canvas/rect-fill 0 0 1000 400)
      (canvas/smoothing? true)
      (canvas/paste img
                    500
                    0)
      (canvas/color-fill "white")
      (canvas/color-stroke "grey")
      (canvas/line-width 2)
      (canvas/font "bold 200px serif")
      (canvas/text-fill 50 100 "Hello")
      (canvas/shadow 10 10 10 "green")
      (canvas/text-stroke 100 200 "Hello")
      ))



(comment


  (def c
       (goog.dom.createElement "canvas"))

  (goog.dom.setProperties c
                          #js {"style" "height:400px; width:1000px;"})

  (goog.dom.appendChild js/document.body
                        c)


  (def ctx
       (canvas/high-dpi (.getContext c
                                     "2d"
                                     #js {"alpha" false})))


  (do
    (frame)
    (def frame
      (canvas/on-frame (fn draw [_timestamp]
                         (try
                           (draw-frame ctx)
                           true
                           (catch :default e
                             (js/console.log "err" e)
                             false))))))


  )
