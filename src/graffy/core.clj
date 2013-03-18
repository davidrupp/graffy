(ns graffy.core)

(defprotocol Graph
  (add-node [this n])
  (add-edge [this s d])
  (nodes [this])
  (edges [this n])
  (all-edges [this])
  (neighbors [this n]))

(defn make-graph []
  (let [state (atom {})]
    (reify Graph
      (add-node [_ n]
        (when-not (@state n)
          (swap! state assoc n #{})))
      (add-edge [this s d]
        (add-node this s)
        (add-node this d)
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

