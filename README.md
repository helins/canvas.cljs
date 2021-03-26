# Canvas

[![Clojars
Project](https://img.shields.io/clojars/v/io.helins/canvas.svg)](https://clojars.org/io.helins/canvas)

[![cljdoc badge](https://cljdoc.org/badge/io/helins/canvas)](https://cljdoc.org/d/io.helins/canvas)

Provides a more Clojurescriptish access to the [Canvas
API](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/translate)
while remaining true to it.

While looking low-level to web developers, the Canvas API is somewhat
high-level in the world of graphics. Further abstractions tend to mask
performance issues and the full range of possibilities it offers. As such, this
library is almost a one-to-one mapping with a fluid interface, improved names,
and barely a few additional utilities. The purpose is to make the API more
enjoyable from Clojurescript without any black magic involved.

A second reason not to provide anything more exciting or extravagant is that
when it comes to graphics, higher-level abstractions are usually built
according to the specific requirement of the application. A data visualization
software radically differs from a game, yet both might use a canvas.  This
field is one where there is no "one size fits all". Or, as they say: "one
person's abstraction is another person's garbage".


## Usage

Prior knowledge of the Canvas API is preferred. However, the
[documentation](https://cljdoc.org/d/io.helins/canvas) clearly helps in acquainting
oneself with it as pretty much all docstrings have links to the relevant
functions and properties [described by the Canvas API in
MDN](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/translate)

Other than that, dare to be bold.


## Why not Quil?

The [Quil project](http://quil.info/) is a well-known graphics library in the
Clojure community. It is perfectly adequate for at least some use cases as it
provides higher-level utilities than just drawing to a canvas. It is
crossplatorm and has basic support for WebGL, for instance.

However, if the intent is to clearly draw on a canvas, then it obfuscates
lower-level operations and can become cumbersome for more serious projects. In
those cases, we recommend sticking to direct access to the canvas without any
additional layer as we believe it will be more fruitful, at least in the longer
term.


## Animation

Graphics libraries typically offer utilities for drawing but not for animating
(how values needed for drawing evolve over time). As it turns out, animation is
actually a very specific topic and it is particularly hard to produce a
general-purpose library. Librairies specifically aimed for animation, in any
language, tend to be opiniated and often counterproductive for more serious
projects.

The exciting world of simulation happens to be an excellent way to understand
animation. We recommend the [DSim](https://github.com/helins/dsim.cljc) library
which was clearly built for such endaveours. Familiarizing oneself with it takes
some learning curve as it invokes concept from simulation, possibly new to the
user ones, but acquiring those concepts result in great flexibility and
versatility.

Tutorials showing how to combine both might be available in the future. However,
the user shall be as compliant as to not hold his/her breath.


## License

Copyright © 2020 Adam Helinski

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
