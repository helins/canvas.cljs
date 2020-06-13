(ns dvlopt.draw

  ""

  {:author "Adam Helinski"}

  (:require [cljs.core :as cljs])
  (:require-macros [dvlopt.draw])
  ;;
  ;; <!> Attention, can be confusing if not kept in mind <!>
  ;;
  (:refer-clojure :exclude [->
                            contains?]))


;; TODO. ImageDate.


;;;;;;;;;; Gathering declarations


(declare color-shadow
         move
         shadow-blur
         shadow-x
         shadow-y)


;;;;;;;;;; Handling contextes


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



(defn clear

  ""

  [ctx x y width height]

  (.clearRect ctx
              x
              y
              width
              height)
  ctx)



(defn contains?

  ""

  [ctx x y]

  (.isPointInStoke ctx
                   x
                   y))


(defn clip

  ""

  ([ctx]

   (clip ctx
         nil))


  ([ctx fill-rule]

   (.clip ctx
          fill-rule)
   ctx))



(defn close

  ""

  [ctx]

  (.closePath ctx)
  ctx)



(defn encloses?

  ""

  ([ctx x y]

   (encloses? ctx
              x
              y
              nil))


  ([ctx x y fill-rule]

   (.isPointInPath ctx
                   x
                   y
                   fill-rule)))



(defn fill

  ""

  ([ctx]

   (.fill ctx)
   ctx)


  ([ctx fill-rule]

   (.fill ctx
          fill-rule)
   ctx))



(defn paste

  ""

  ([ctx src x-dest y-dest]

   (.drawImage ctx
               src
               x-dest
               y-dest)
   ctx)


  ([ctx src x-dest y-dest width-dest height-dest]

   (.drawImage ctx
               src
               x-dest
               y-dest
               width-dest
               height-dest)
   ctx)


  ([ctx src x-src y-src width-src height-src x-dest y-dest width height]

   (.drawImage ctx
               src
               x-src
               y-src
               width-src
               height-src
               x-dest
               y-dest
               width
               height)
   ctx))



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



(defn stroke

  ""

  [ctx]

   (.stroke ctx)
   ctx)


;;;;;;;;;; Gradients


(defn- -add-color-stops

  ""

  [grad color-stops]

  (doseq [[stop
           color] color-stops]
    (.addColorStop grad
                   stop
                   color))
  grad)



(defn grad-linear

  ""

  [ctx x-1 y-1 x-2 y-2 color-stops]

  (-add-color-stops (.createLinearGradient ctx
                                           x-1
                                           y-1
                                           x-2
                                           y-2)
                    color-stops))



(defn grad-radial

  ""

  [ctx x-1 y-1 radius-1 x-2 y-2 radius-2 color-stops]

  (-add-color-stops (.createRadialGradient ctx
                                           x-1
                                           y-1
                                           radius-1
                                           x-2
                                           y-2
                                           radius-2)
                    color-stops))



(defn pattern

  ""

  ([ctx src]

   (pattern ctx
            src
            nil))


  ([ctx src repeat]

   (.createPattern ctx
                   src
                   (or repeat
                       "repeat"))))



;;;;;;;;;; Paths


(defn path

  ""

  ([]

   (js/Path2D.))


  ([source]
   ;; Either an existing path which will be copied or a SVG path (string).
   (js/Path2D. source)))



(defn path-clip

  ""

  ([ctx path]

   (path-clip ctx
              path
              nil))


  ([ctx path fill-rule]

   (.clip ctx
          path
          fill-rule)
    ctx))



(defn path-contains?

  ""

  ([ctx path x y]

   (.isPointInStoke ctx
                    path
                    x
                    y)))



(defn path-encloses?

  ""

  ([ctx path x y]

   (path-encloses? ctx
                   path
                   x
                   y
                   nil))


  ([ctx path x y fill-rule?]

   (.isPointInPath ctx
                   path
                   x
                   y
                   fill-rule?)))



(defn path-fill

  ""

  ([ctx path]

   (.fill ctx
          path)
   ctx)


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


;;;;;;;;;; Styling


(defn alpha

  ""

  ([ctx]

   (.-globalAlpha ctx))

  
  ([ctx percent]

   (set! (.-globalAlpha ctx)
         percent)
   ctx))



(defn color-fill

  ""

  ([ctx]

   (.-fillStyle ctx))


  ([ctx color]

   (set! (.-fillStyle ctx)
         color)
   ctx))



(defn color-shadow

  ""

  ([ctx]

   (.-shadowColor ctx))


  ([ctx color]

   (set! (.-shadowColor ctx)
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



(defn composite-op

  ""

  ([ctx]

   (.-globalCompositeOperation ctx))


  ([ctx type]

   (set! (.-globalCompositeOperation ctx)
         type)
   ctx))



(defn line-cap

  ""

  ([ctx]

   (.-lineCap ctx))


  ([ctx cap]

   (set! (.-lineCap ctx)
         cap)
   ctx))



(defn line-dash

  ""

  ([ctx]

   [(.getLineDash ctx)
    (.-lineDashOffset ctx)])


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



(defn line-join

  ""

  ([ctx]

   (.-lineJoin ctx))


  ([ctx join]

   (set! (.-lineJoin ctx)
         join)
   ctx))



(defn line-width

  ""

  ([ctx]

   (.-lineWidth ctx))


  ([ctx width]

   (set! (.-lineWidth ctx)
         width)
   ctx))



(defn miter-limit

  ""

  ([ctx]

   (.-miterLimit ctx))


  ([ctx limit]

   (set! (.-miterLimit ctx)
         limit)
   ctx))



(defn shadow

  ""

  ([ctx blur x-offset y-offset]

   (cljs/-> ctx
            (shadow-blur blur)
            (shadow-x x-offset)
            (shadow-y y-offset)))

   
  ([ctx blur x-offset y-offset color]

   (cljs/-> ctx
            (shadow blur
                    x-offset
                    y-offset)
            (color-shadow color))))



(defn shadow-blur

  ""

  ([ctx]

   (.-shadowBlur ctx))


  ([ctx blur]

   (set! (.-shadowBlur ctx)
         blur)
   ctx))



(defn shadow-x

  ""

  ([ctx]

   (.-shadowOffsetX ctx))


  ([ctx x-offset]

   (set! (.-shadowOffsetX ctx)
         x-offset)
   ctx))



(defn shadow-y

  ""

  ([ctx]

   (.-shadowOffsetY ctx))


  ([ctx y-offset]

   (set! (.-shadowOffsetY ctx)
         y-offset)
   ctx))



(defn smoothing?

  ""

  ([ctx]

   (.-imageSmoothingEnabled ctx))


  ([ctx enabled?]

   (set! (.-imageSmoothingEnabled ctx)
         enabled?)
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


;;;;;;;;;; Subpaths


(defn arc

  ""

  ([path x y radius angle-start angle-end]

   (arc path
        x
        y
        radius
        angle-start
        angle-end
        nil))


  ([path x y radius angle-start angle-end anti-clockwise?]

   (.arc path
         x
         y
         radius
         angle-start
         angle-end
         anti-clockwise?)
   path))



(defn arc-ctrl

  ""

  [path x-1 y-1 x-2 y-2 radius]

  (.arcTo path
          x-1
          y-1
          x-2
          y-2
          radius)
  path)



(defn bezier-1

  ""

  ;; Quadratic Bézier

  ([path x-cp y-cp x-end y-end]

   (.quadraticCurveTo path
                      x-cp
                      y-cp
                      x-end
                      y-end)
   path)


  ([path x-start y-start x-cp y-cp x-end y-end]

   (cljs/-> path 
            (move x-start
                  y-start)
            (bezier-1 x-cp y-cp x-end y-end))))



(defn bezier-2

  ""

  ;; Cubic Bézier

  ([path x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end]

   (.bezierCurveTo path 
                   x-cp-1
                   y-cp-1
                   x-cp-2
                   y-cp-2
                   x-end
                   y-end)
   path)


  ([path x-start y-start x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end]

   (cljs/-> path
            (move x-start
                  y-start)
            (bezier-2 x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end))))



(defn ellipse

  ""

  ([path x y radius-x radius-y rotation angle-start angle-end]

   (ellipse path 
            x
            y
            radius-y
            radius-y
            rotation
            angle-start
            angle-end
            nil))


  ([path x y radius-x radius-y rotation angle-start angle-end anti-clockwise?]

   (.ellipse path 
             x
             y
             radius-x
             radius-y
             rotation
             angle-start
             angle-end
             anti-clockwise?)
   path))



(defn line

  ""

  ([path x y]

   (.lineTo path
            x
            y)
   path)


  ([path x-1 y-1 x-2 y-2]

   (cljs/-> path
            (move x-1
                  y-1)
            (line x-2
                  y-2))))



(defn move

  ""

  [path x y]

  (.moveTo path 
           x
           y)
  path)



(defn rect

  ""

  [path x y width height]

  (.rect path
         x
         y
         width
         height)
  path)


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



(defn text-metrics

  ""

  [ctx text]

  (.measureText ctx
                text))



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


;;;;;;;;;; Spatial transformations


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



(defn matrix-mult

  ""

  [ctx a b c d e f]

  (.transform ctx a b c d e f)
  ctx)



(defn matrix-reset

  ""

  [ctx]

  (.setTransform ctx 1 0 0 1 0 0)
  ctx)



(defn rotate

  ""

  [ctx radians]

  (.rotate ctx
           radians)
  ctx)



(defn scale

  ""

  [ctx x y]

  (.scale ctx
          x
          y)
  ctx)



(defn scale-x

  ""

  [ctx x]

  (scale ctx
         x
         1))



(defn scale-y

  ""

  [ctx y]

  (scale ctx
         1
         y))



(defn translate

  ""

  [ctx x y]

  (.translate ctx
              x
              y)
  ctx)



(defn translate-x

  ""

  [ctx x]

  (translate ctx
             x
             0))



(defn translate-y

  ""

  [ctx y]

  (translate ctx
             0
             y))



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
