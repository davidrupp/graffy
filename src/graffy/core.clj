(ns graffy.core)

(defprotocol Graph
  (add-node [this n])
  (add-edge [this s d])
  (nodes [this])
  (edges [this n])
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
      (neighbors [this n]
        (or (@state n)
            #{}))
      (toString [_]
        (.toString @state)))))

(comment
  (let [g (make-graph)]
    (add-edge g :a :b)
    (add-edge g :a :c))
  (-> (make-graph)
      (add-edge :a :b)
      (add-edge :a :c))
  (doto (make-graph)
    (add-edge :a :b)
    (add-edge :a :c)))
