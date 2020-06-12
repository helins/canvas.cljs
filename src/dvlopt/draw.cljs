(ns dvlopt.draw

  ""

  {:author "Adam Helinski"}

  (:require-macros [dvlopt.draw]))


;;;;;;;;;; Gathering declarations


(declare move)


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
        nil))


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

  ([ctx]

   (begin ctx
          0
          0))


  ([ctx x y]

   (.beginPath ctx)
   (move ctx
         x
         y)))



(defn bezier-1

  ""

  ;; Quadratic Bézier

  ([ctx x-cp y-cp x-end y-end]

   (.quadraticCurveTo ctx
                      x-cp
                      y-cp
                      x-end
                      y-end)
   ctx)


  ([ctx x-start y-start x-cp y-cp x-end y-end]

   (-> ctx
       (move x-start
             y-start)
       (bezier-1 x-cp y-cp x-end y-end))))



(defn bezier-2

  ""

  ;; Cubic Bézier

  ([ctx x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end]

   (.bezierCurveTo ctx
                   x-cp-1
                   y-cp-1
                   x-cp-2
                   y-cp-2
                   x-end
                   y-end)
   ctx)


  ([ctx x-start y-start x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end]

   (-> ctx
       (move x-start
             y-start)
       (bezier-2 x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end))))



(defn clear

  ""

  [ctx x y width height]

  (.clearRect ctx
              x
              y
              width
              height)
  ctx)



(defn clip

  ""

  ([ctx]

   (clip ctx
         nil))


  ([ctx fill-rule]

   (.clip ctx
          fill-rule)
   ctx))



(defn clip-path

  ""

  ([ctx path]

   (clip-path ctx
              path
              nil))


  ([ctx path fill-rule]

   (.clip ctx
          path
          fill-rule)
    ctx))



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

   (fill ctx
         nil))


  ([ctx fill-rule]

   (.fill ctx
          fill-rule)
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
       (move x-1
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



(defn move

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

  [ctx]

   (.stroke ctx)
   ctx)


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


;;;;;;;;;; Paths


(defn path

  ""

  ([]

   (js/Path2D.))


  ([source]
   ;; Either an existing path which will be copied or a SVG path (string).
   (js/Path2D. source)))



(defn path-fill

  ""

  ([ctx path]

   (path-fill ctx
              path
              nil))


  ([ctx path fill-rule]

   (.fill ctx
          path
          fill-rule)
   ctx))



(defn path-stroke

  ""

  [ctx path]

  (.stroke ctx
           path)
  ctx)


;;;;;;;;;; Text


(defn font

  ""

  ([ctx]

   (.-font ctx))


  ([ctx new-font]

   (set! (.-font ctx)
         new-font)
   ctx))



(defn text-align

  ""

  ([ctx]

   (.-textAlign ctx))


  ([ctx align]

   (set! (.-textAlign ctx)
         align)
   ctx))



(defn text-baseline

  ""

  ([ctx]

   (.-textBaseline ctx))


  ([ctx baseline]

   (set! (.-textBaseline ctx)
         baseline)
   ctx))



(defn text-fill 

  ""

  ([ctx x y text]

   (.fillText ctx
              text
              x
              y)
   ctx)


  ([ctx x y text max-width]

   (.fillText ctx
              text
              x
              y
              max-width)
   ctx))



(defn text-stroke 

  ""

  ([ctx x y text]

   (.strokeText ctx
                text
                x
                y)
   ctx)


  ([ctx x y text max-width]

   (.strokeText ctx
                text
                x
                y
                max-width)
   ctx))


;;;;;;;;;; Additional utilities


(def ^:private -rad-conv (/ Math/PI
                            180))


(defn deg->rad

  ""

  [deg]

  (* deg
     -rad-conv))



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
