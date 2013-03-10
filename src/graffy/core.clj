(ns graffy.core)

(defprotocol Graph
  (add-node [this n])
  (add-edge [this s d]))

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
        (swap! state update-in [d] conj s))
      (toString [_]
        (.toString @state)))))

(comment
  (let [g (make-graph)]
    (add-edge g :a :b)
    (add-edge g :a :c)
    g))
