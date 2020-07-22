(ns dvlopt.draw

  "An more idiomatic access to the Canvas API.

   Aims to be particularly minimalistic and close to the original thing (cf. README).

   Functions producing side effects are organized in a fluid interface. In other words, they return the given context or
   standalone path and can conveniently be be chained using `->`.

   Shape drawing functions, such as [[line]] or [[arc]], can be applied both to contexts as well as standalone
   paths produced by [[path]].

   Functions prefixed with `path-` only apply to standalone paths and mimick other functions applying only to contexts.
   For instance, [[path-stroke]] is the sibling of [[stroke]]."

  {:author "Adam Helinski"}

  (:require-macros [dvlopt.draw])
  ;;
  ;; <!> Attention, can be confusing if not kept in mind <!>
  ;;
  (:refer-clojure :exclude [contains?]))


;; MAYBEDO. A nice API for ImageData?


;;;;;;;;;; Gathering declarations


(declare color-shadow
         move
         shadow-blur
         shadow-x
         shadow-y)


;;;;;;;;;; Handling contextes


(defn begin

  "Starts a new path by emptying the list of sub-paths. Call this method when you want to create a new path.
  
   Cf. [.beginPath()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/beginPath)"

  ([ctx]

   (.beginPath ctx)
   ctx)


  ([ctx x y]

   (.beginPath ctx)
   (move ctx
         x
         y)))



(defn clear

  "Erases the pixels in a rectangular area by setting them to transparent black.

   Cf. [.clearRect()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clearRect)"

  [ctx x y width height]

  (.clearRect ctx
              x
              y
              width
              height)
  ctx)



(defn contains?

  "Reports whether or not the specified point is inside the area contained by the stroking of a path (initialized
   by [[begin]]).
  
   Cf. [.isPointInStroke()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInStroke)"

  [ctx x y]

  (.isPointInStoke ctx
                   x
                   y))


(defn clip

  "Turns the current path into the current clipping region. It replaces any previous clipping region.

   Cf. [.clip()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clip)"

  ([ctx]

   (clip ctx
         nil))


  ([ctx fill-rule]

   (.clip ctx
          fill-rule)
   ctx))



(defn close

  "Attempts to add a straight line from the current point to the start of the current sub-path.
  
   If the shape has already been closed or has only one point, this function does nothing.

   Cf. [.closePath()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/closePath)"

  [ctx]

  (.closePath ctx)
  ctx)



(defn encloses?

  "Reports whether or not the specified point is contained in the current path.
  
   Cf. [.isPointInPath()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInPath)"

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

  "Fills the current path with with the color set by [[color-fill]].

   Cf. [.fill()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fill)"

  ([ctx]

   (.fill ctx)
   ctx)


  ([ctx fill-rule]

   (.fill ctx
          fill-rule)
   ctx))



(defn paste

  "Provides different ways to draw an image onto the canvas.
  
   Cf. [.drawImage()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage)"

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

  "Draws a rectangle that is filled according to the color set by [[color-fill]]

   This method draws directly to the canvas without modifying the current path.
  
   Cf. [.fillRect()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillRect)"

  [ctx x y width height]

  (.fillRect ctx
             x
             y
             width
             height)
  ctx)



(defn rect-stroke

  "Draws a rectangle that is stroked (outlined).

   This method draws directly to the canvas without modifying the current path.

   Cf. [[stroke]]
       [.strokeRect()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeRect)"

  [ctx x y width height]

  (.strokeRect ctx
               x
               y
               width
               height)
  ctx)



(defn stroke

  "Strokes (outlines) the current path according to the color set by [[color-stroke]] and line related settings such as [[line-width]].

   Strokes are aligned to the center of a path; in other words, half of the stroke is drawn on the inner side, and half on the outer side.

   The stroke is drawn using the non-zero winding rule, which means that path intersections will still get filled.

   Cf. [.stroke()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/stroke)"

  [ctx]

   (.stroke ctx)
   ctx)


;;;;;;;;;; Gradients


(defn- -add-color-stops

  ;; Helper for [[grad-linear]] and [[grad-radial]].

  [grad color-stops]

  (doseq [[stop
           color] color-stops]
    (.addColorStop grad
                   stop
                   color))
  grad)



(defn grad-linear

  "Creates a gradient along the line connecting two given coordinates which can be assigned using [[color-fill]] and/or [[color-stroke]].

   `color-stops` is a vector of [percent color].

   Cf. [.createLinearGradient()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createLinearGradient)"

  [ctx x-1 y-1 x-2 y-2 color-stops]

  (-add-color-stops (.createLinearGradient ctx
                                           x-1
                                           y-1
                                           x-2
                                           y-2)
                    color-stops))



(defn grad-radial

  "Creates a radial gradient using the size and coordinates of two circles which can be assigned using [[color-fill]] and/or [[color-stroke]].

   `color-stops` is a vector of [percent color].

   Cf. [.createRadialGradient()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createRadialGradient)"

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

  "Creates a pattern using the specified image and repetition which can be assigned using [[color-fill]] and/or [[color-stroke]].

   Cf. [.createPattern()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createPattern)"


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

  "Returns a new path object which can be reused in order to improve performance.

   Just like a context, it can be used with shape drawing functions such as [[line]] and functions prefixed with `path-`.

   `source` is either an existing path which will be copied or an SVG string path.

   Cf. [Path2D](https://developer.mozilla.org/en-US/docs/Web/API/Path2D/Path2D)"

  ([]

   (js/Path2D.))


  ([source]
   ;; Either an existing path which will be copied or an SVG path (string).
   (js/Path2D. source)))



(defn path-clip

  "Just like [[clip]], but clips the given path."

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

  "Just like [[contains?]], but in reference to the given path."

  ([ctx path x y]

   (.isPointInStoke ctx
                    path
                    x
                    y)))



(defn path-encloses?

  "Just like [[encloses?]], but in reference to the given path."

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

  "Just like [[fill]], but fills the given path."

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

  "Just like [[stroke]], but strokes the given path."

  [ctx path]

  (.stroke ctx
           path)
  ctx)


;;;;;;;;;; Styling


(defn alpha

  "Gets or sets the alpha (transparency) value that is applied to shapes and images before they are drawn onto the canvas.

   Cf. [.globalAlpha](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/globalAlpha)"

  ([ctx]

   (.-globalAlpha ctx))

  
  ([ctx percent]

   (set! (.-globalAlpha ctx)
         percent)
   ctx))



(defn color-fill

  "Gets or sets the color, gradient, or pattern to use inside shapes. The default style is #000 (black).

   Cf. [.fillStyle](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillStyle)"

  ([ctx]

   (.-fillStyle ctx))


  ([ctx color]

   (set! (.-fillStyle ctx)
         color)
   ctx))



(defn color-shadow

  "Gets or sets the color of shadows.

   Be aware that the shadow's rendered opacity will be affected by the opacity of the color set by [[color-fill]] when filling, and of the
   color set by [[color-stroke]] when stroking.

   Cf. [.shadowColor](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowColor)"

  ([ctx]

   (.-shadowColor ctx))


  ([ctx color]

   (set! (.-shadowColor ctx)
         color)
   ctx))



(defn color-stroke

  "Gets or sets the color, gradient, or pattern to use for the strokes (outlines) around shapes. The default is #000 (black).

   Cf. [.strokeStyle](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeStyle)"

  ([ctx]

   (.-strokeStyle ctx))


  ([ctx color]

   (set! (.-strokeStyle ctx)
         color)
   ctx))



(defn composite-op

  "Gets or sets the type of compositing operation to apply when drawing new shapes.

   Cf. [.globalCompositeOperation](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/globalCompositeOperation)"

  ([ctx]

   (.-globalCompositeOperation ctx))


  ([ctx type]

   (set! (.-globalCompositeOperation ctx)
         type)
   ctx))



(defn line-cap

  "Gets or sets the shape used to draw the end points of lines.

   Cf. [.lineCap](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineCap)"

  ([ctx]

   (.-lineCap ctx))


  ([ctx cap]

   (set! (.-lineCap ctx)
         cap)
   ctx))



(defn line-dash

  "Gets or sets the line dash pattern used when stroking lines.
  
   If needed, also sets the offset.

   `segments` is a JS array of numbers that specify distances to alternately draw a line and a gap (in coordinate space units)
   
   Arity 1 returns `[current-dash current-offset]`.

   Cf. [.lineDashOffset](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset)
	   [.setLineDash()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash)"

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

  "Gets or sets the shape used to join two line segments where they meet.

   This property has no effect wherever two connected segments have the same direction, because no joining area will be added in this case.

   Degenerate segments with a length of zero (i.e., with all endpoints and control points at the exact same position) are also ignored.

   Cf. [.lineJoin](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineJoin)"

  ([ctx]

   (.-lineJoin ctx))


  ([ctx join]

   (set! (.-lineJoin ctx)
         join)
   ctx))



(defn line-width

  "Gets or sets the thickness of lines.

   Cf. [.lineWidth](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineWidth)"

  ([ctx]

   (.-lineWidth ctx))


  ([ctx width]

   (set! (.-lineWidth ctx)
         width)
   ctx))



(defn miter-limit

  "Gets or sets the miter limit ratio.

   Cf. [.miterLimit](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/miterLimit)"

  ([ctx]

   (.-miterLimit ctx))


  ([ctx limit]

   (set! (.-miterLimit ctx)
         limit)
   ctx))



(defn shadow

  "Shorthand calling [[shadow-blur]], [[shadow-x]], [[shaddow-y]], and if needed, [[color-shadow]]."

  ([ctx blur x-offset y-offset]

   (-> ctx
       (shadow-blur blur)
       (shadow-x x-offset)
       (shadow-y y-offset)))

   
  ([ctx blur x-offset y-offset color]

   (-> ctx
       (shadow blur
               x-offset
               y-offset)
       (color-shadow color))))



(defn shadow-blur

  "Gets or sets the amount of blur applied to shadows. The default is 0 (no blur).

   Cf. [.shadowBlur](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowBlur)"

  ([ctx]

   (.-shadowBlur ctx))


  ([ctx blur]

   (set! (.-shadowBlur ctx)
         blur)
   ctx))



(defn shadow-x

  "Gets or sets the distance that shadows will be offset horizontally.

   Cf. [.shadowOffsetX](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowOffsetX)"

  ([ctx]

   (.-shadowOffsetX ctx))


  ([ctx x-offset]

   (set! (.-shadowOffsetX ctx)
         x-offset)
   ctx))



(defn shadow-y

  "Gets or sets the distance that shadows will be offset vertically.

   Cf. [.shadowOffsetY](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowOffsetY)"

  ([ctx]

   (.-shadowOffsetY ctx))


  ([ctx y-offset]

   (set! (.-shadowOffsetY ctx)
         y-offset)
   ctx))



(defn smoothing?

  "Gets or sets whether scaled images are smoothed (true, default) or not (false).

   Useful for games and other apps that use pixel art. When enlarging images, the default resizing algorithm will blur the pixels.

   Set this property to false to retain the pixels' sharpness.

   Cf. [.imageSmoothingEnabled](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/imageSmoothingEnabled)"

  ([ctx]

   (.-imageSmoothingEnabled ctx))


  ([ctx enabled?]

   (set! (.-imageSmoothingEnabled ctx)
         enabled?)
   ctx))



(defn style-pop

  "Restores the most recently saved canvas state by popping the top entry in the drawing state stack.
   
   If there is no saved state, this method does nothing.

   Cf. [[style-save]]
	   [.restore()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/restore)"

  [ctx]

  (.restore ctx)
  ctx)



(defn style-save

  "Saves the entire state of the canvas (that is related to any styling) by pushing the current state onto a stack.

   Cf. [[style-pop]]
       [.save()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/save)"

  [ctx]

  (.save ctx)
  ctx)


;;;;;;;;;; Subpaths


(defn arc

  "Adds a circular arc to the current sub-path.

   Cf. [.arc()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arc)"

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

  "Adds a circular arc to the current sub-path, using the given control points and radius.

   The arc is automatically connected to the path's latest point with a straight line, if necessary for the specified parameters.
  
   Commonly used for making rounded corners.

   Cf. [.arcTo](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arcTo)"

  [path x-1 y-1 x-2 y-2 radius]

  (.arcTo path
          x-1
          y-1
          x-2
          y-2
          radius)
  path)



(defn bezier-1

  "Adds a quadratic Bézier curve (1 control point) to the current sub-path. It requires two points: the first one is a control point and
   the second one is the end point. The starting point is the latest point in the current path, which can be changed using [[move]] before
   creating the quadratic Bézier curve.

   Cf. [.quadraticCurveTo()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/quadraticCurveTo)"

  ([path x-cp y-cp x-end y-end]

   (.quadraticCurveTo path
                      x-cp
                      y-cp
                      x-end
                      y-end)
   path)


  ([path x-start y-start x-cp y-cp x-end y-end]

   (-> path 
       (move x-start
             y-start)
       (bezier-1 x-cp y-cp x-end y-end))))



(defn bezier-2

  "Adds a cubic Bézier curve (2 control points) to the current sub-path. It requires three points: the first two are control points and
   the third one is the end point. The starting point is the latest point in the current path, which can be changed using [[move]] before
   creating the Bézier curve.

   Cf. [.bezierCurveTo()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/bezierCurveTo)"

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

   (-> path
       (move x-start
             y-start)
       (bezier-2 x-cp-1 y-cp-1 x-cp-2 y-cp-2 x-end y-end))))



(defn ellipse

  "Adds an elliptical arc to the current sub-path.
 
   Cf. [.ellipse()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/ellipse)"

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

  "Adds a straight line to the current sub-path by connecting the sub-path's last point to the specified (x, y) coordinates.

   Cf. [.line()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineTo)"

  ([path x y]

   (.lineTo path
            x
            y)
   path)


  ([path x-1 y-1 x-2 y-2]

   (-> path
       (move x-1
             y-1)
       (line x-2
             y-2))))



(defn move

  "Begins a new sub-path at the point specified by the given (x, y) coordinates.

   Cf. [.moveTo](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/moveTo)"

  [path x y]

  (.moveTo path 
           x
           y)
  path)



(defn rect

  "Adds a rectangle to the current path.

   Cf. [.rect](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/rect)"

  [path x y width height]

  (.rect path
         x
         y
         width
         height)
  path)


;;;;;;;;;; Text


(defn font

  "Gets or sets the current text style to use when drawing text. This string uses the same syntax as a CSS font specifier.

   Cf. [.font](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/font)
       [Font specifier](https://developer.mozilla.org/en-US/docs/Web/CSS/font)"

  ([ctx]

   (.-font ctx))


  ([ctx new-font]

   (set! (.-font ctx)
         new-font)
   ctx))



(defn text-align

  "Gets or sets the current text alignment used when drawing text.
  
   The alignment is relative to the x value of the [[text-fill]] function. For example, if textAlign is \"center\", then the
   text's left edge will be at x - (textWidth / 2).

   Cf. [.textAlign](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/textAlign)"

  ([ctx]

   (.-textAlign ctx))


  ([ctx align]

   (set! (.-textAlign ctx)
         align)
   ctx))



(defn text-baseline

  "Gets or sets the current text baseline used when drawing text.

   Cf. [.textBaseline](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/textBaseline)"

  ([ctx]

   (.-textBaseline ctx))


  ([ctx baseline]

   (set! (.-textBaseline ctx)
         baseline)
   ctx))



(defn text-fill 

  "Draws a text string at the specified coordinates, filling the string's characters with the current fillStyle. An optional parameter
   allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
  
   This function draws directly to the canvas without modifying the current path.
  
   The text is rendered using the font and text layout configuration as defined by [[font]], [[text-align]], and [[text-baseline]].

   Cf. [[ŧext-stroke]]
	   [.fillText()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillText)"

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

  "Strokes — that is, draws the outlines of — the characters of a text string at the specified coordinates. An optional parameter
   allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
  
   This function draws directly to the canvas without modifying the current path.

   Cf. [[text-fill]]
       [.strokeText()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeText)"

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

  "Gets (a copy) or sets the transformation matrix.

   Cf. [.getTransform()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/getTransform)
       [.setTransform()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setTransform)"

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

  "Multiplies the current transformation matrix by the given arguments.

   Allows to scale, rotate, translate (move), and skew the context.

   Cf. [.transform()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/transform)"

  [ctx a b c d e f]

  (.transform ctx a b c d e f)
  ctx)



(defn rotate

  "Adds a rotation to the transformation matrix.

   Cf. [.rotate()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/rotate)"

  [ctx radians]

  (.rotate ctx
           radians)
  ctx)



(defn scale

  "Adds a scaling transformation to the canvas units horizontally and/or vertically.
  
   By default, one unit on the canvas is exactly one pixel. A scaling transformation modifies this behavior. For instance,
   a scaling factor of 0.5 results in a unit size of 0.5 pixels; shapes are thus drawn at half the normal size. Similarly, a
   scaling factor of 2.0 increases the unit size so that one unit becomes two pixels; shapes are thus drawn at twice the normal size.

   Cf. [.scale()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/scale)"

  [ctx x y]

  (.scale ctx
          x
          y)
  ctx)



(defn scale-x

  "Like [[scale]], but only for the X axis."

  [ctx x]

  (scale ctx
         x
         1))



(defn scale-y

  "Like [[scale]], but only for the Y axis."

  [ctx y]

  (scale ctx
         1
         y))



(defn translate

  "Adds a translation transformation to the current matrix.

   Cf. [.translate()](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/translate)"

  [ctx x y]

  (.translate ctx
              x
              y)
  ctx)



(defn translate-x

  "Like [[translate]] but only for the X axis."

  [ctx x]

  (translate ctx
             x
             0))



(defn translate-y

  "Like [[translate]] but only for the Y axis."

  [ctx y]

  (translate ctx
             0
             y))



;;;;;;;;;; Additional utilities


(def ^:private -rad-conv (/ Math/PI
                            180))


(defn deg->rad

  "Converts degrees to radians."

  [deg]

  (* deg
     -rad-conv))



(defn high-dpi

  "Adapts a context and its underlying canvas to high DPI screens by scaling the image in order to avoid otherwise pixelated
   drawings.

   Modifies the `scale` properties of the transformation matrix.

   Cf. [[matrix]]"

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

  "Invokes `f` everytime the next frame it is time to draw a frame with the current timestamp. Aims for 60 frames per second.

   There are 2 ways for cancelling it: either call the returned function (no arguments) or return a falsy value from `f`."

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
