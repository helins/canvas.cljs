;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at https://mozilla.org/MPL/2.0/.


(ns helins.canvas

  ;; Macros.

  {:author "Adam Helinski"})


;;;;;;;;;; Macros


(defmacro subspace

  "The transformation matrix is saved at the beginning of the macro and restored at the end so that any spatial
   transformations are forgotten.

   Specially useful when drawing standalone paths which typically require translation.

   Cf. [[matrix]]
	   [[path]]"

  [[ctx] & forms]

  `(let [ctx#    ~ctx
         matrix# (helins.canvas/matrix ctx#)
         ret#    (do
                   ~@forms)]
     (helins.canvas/matrix ctx#
                           matrix#)
     ret#))
