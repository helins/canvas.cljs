(ns dvlopt.draw

  ""

  {:author "Adam Helinski"}

  (:require-macros [dvlopt.draw]))


;;;;;;;;;; Gathering declarations


(declare position)


;;;;;;;;;; Core Canvas API


(defn arc

  ""

  ([ctx x y radius angle-start angle-end]

   (arc ctx
        x
        y
        radius
        angle-start
        angle-end
        false))


  ([ctx x y radius angle-start angle-end anti-clockwise?]

   (.arc ctx
         x
         y
         radius
         angle-start
         angle-end
         anti-clockwise?)
   ctx))



(defn arc-ctrl

  ""

  [ctx x-1 y-1 x-2 y-2 radius]

  (.arcTo ctx
          x-1
          y-1
          x-2
          y-2
          radius)
  ctx)



(defn begin

  ""

  [ctx]

  (.beginPath ctx)
  ctx)



(defn close

  ""

  [ctx]

  (.closePath ctx)
  ctx)



(defn color-fill

  ""

  ([ctx]

   (.-fillStyle ctx))


  ([ctx color]

   (set! (.-fillStyle ctx)
         color)
   ctx))



(defn color-stroke

  ""

  ([ctx]

   (.-strokeStyle ctx))


  ([ctx color]

   (set! (.-strokeStyle ctx)
         color)
   ctx))



(defn ellipse

  ""

  ([ctx x y radius-x radius-y rotation angle-start angle-end]

   (ellipse ctx
            x
            y
            radius-y
            radius-y
            rotation
            angle-start
            angle-end
            nil))


  ([ctx x y radius-x radius-y rotation angle-start angle-end anti-clockwise?]

   (.ellipse ctx
             x
             y
             radius-x
             radius-y
             rotation
             angle-start
             angle-end
             anti-clockwise?)
   ctx))



(defn fill

  ""

  ([ctx]

   (.fill ctx)
   ctx)


  ([ctx path]

   (.fill ctx
          path)
   ctx))



(defn line

  ""

  ([ctx x y]

   (.lineTo ctx
            x
            y)
   ctx)


  ([ctx x-1 y-1 x-2 y-2]

   (-> ctx
       (position x-1
                 y-1)
       (line x-2
             y-2))))



(defn line-dash

  ""

  ([ctx]

   (.getLineDash ctx))


  ([ctx segments]

   (.setLineDash ctx
                 (or segments
                     #js []))
   ctx)


  ([ctx segments offset]

   (set! (.-lineDashOffset ctx)
         offset)
   (line-dash ctx
              segments)))



(defn line-width

  ""

  ([ctx]

   (.-lineWidth ctx))


  ([ctx width]

   (set! (.-lineWidth ctx)
         width)
   ctx))



(defn matrix

  ""

  ([ctx]

   (.getTransform ctx))


  ([ctx m]

   (.setTransform ctx
                  m)
   ctx)


  ([ctx a b c d e f]
   
   (.setTransform ctx a b c d e f)
   ctx))



(defn path

  ""

  ([]

   (js/Path2D.))


  ([source]
   ;; Either an existing path which will be copied or a SVG path (string).
   (js/Path2D. source)))



(defn position

  ""

  [ctx x y]

  (.moveTo ctx
           x
           y)
  ctx)



(defn rotate

  ""

  [ctx radians]

  (.rotate ctx
           radians)
  ctx)



(defn rect

  ""

  [ctx x y width height]

  (.rect ctx
         x
         y
         width
         height)
  ctx)



(defn rect-fill

  ""

  [ctx x y width height]

  (.fillRect ctx
             x
             y
             width
             height)
  ctx)



(defn rect-stroke

  ""

  [ctx x y width height]

  (.strokeRect ctx
               x
               y
               width
               height)
  ctx)



(defn scale

  ""

  [ctx x y]

  (.scale ctx
          x
          y)
  ctx)



(defn stroke

  ""

  ([ctx]

   (.stroke ctx)
   ctx)


  ([ctx path]

   (.stroke ctx
            path)
   ctx))



(defn style-pop

  ""

  [ctx]

  (.restore ctx)
  ctx)



(defn style-save

  ""

  [ctx]

  (.save ctx)
  ctx)



(defn transform

  ""

  ([ctx a b c d e f]

   (.transform ctx a b c d e f)
   ctx))



(defn transform-reset

  ""

  [ctx]

  (.setTransform ctx 1 0 0 1 0 0)
  ctx)



(defn translate

  ""

  [ctx x y]

  (.translate ctx
              x
              y)
  ctx)


;;;;;;;;;; Additional utilities


(defn high-dpi

  ""

  [ctx]

  (let [canvas (.-canvas ctx)
        dpr    (goog.dom.getPixelRatio)
        rect   (.getBoundingClientRect canvas)]
    (set! (.-height canvas)
          (* dpr
             (.-height rect)))
    (set! (.-width canvas)
          (* dpr
             (.-width rect)))
    (.scale ctx
            dpr
            dpr))
  ctx)



(defn on-frame

  ""

  [f]

  (let [v*id (volatile! nil)]
    (vreset! v*id
             (js/requestAnimationFrame (fn frame [timestamp]
                                         (when (f timestamp)
                                           (vreset! v*id
                                                    (js/requestAnimationFrame frame))))))
    (fn cancel []
      (js/cancelAnimationFrame @v*id)
      nil)))
