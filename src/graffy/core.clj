(ns graffy.core)

(defprotocol Graph
  (add-edge [this s d])
  (nodes [this])
  (edges [this n])
  (all-edges [this])
  (neighbors [this n]))

(defn make-graph []
  (let [state (atom {})]
    (reify Graph
      (add-edge [this s d]
        (when-not (@state s) (swap! state assoc s #{}))
        (when-not (@state d) (swap! state assoc d #{}))
        (swap! state update-in [s] conj d)
        (swap! state update-in [d] conj s)
        this)
      (nodes [this]
        (keys @state))
      (edges [this n]
        (for [dest (neighbors this n)]
          [n dest]))
      (all-edges [this]
        (apply concat (for [source (nodes this)]
                        (edges this source))))
      (neighbors [this n]
        (or (@state n)
            #{}))
      (toString [_]
        (.toString @state)))))

