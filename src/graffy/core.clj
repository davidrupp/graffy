(ns graffy.core)

(defprotocol Graph
  (add-edge [this s d])
  (nodes [this])
  (edges [this n])
  (all-edges [this])
  (neighbors [this n])
  (neighbor-list [this n])
  (dft [this n])
  (bft [this n]))

(defn make-graph []
  (let [state (atom {})]
    (reify Graph
      ;; TODO: implement (add-edges [this s d & ds])
      (add-edge [this s d]
        (when-not (@state s) (swap! state assoc s (sorted-set)))
        (when-not (@state d) (swap! state assoc d (sorted-set)))
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
            (sorted-set)))
      (neighbor-list [this n]
        (apply list (neighbors this n)))
      (dft [this n]
        (loop [acc [] stack (list n)]
          (if (zero? (count stack))
            acc
            (let [nxt (peek stack)
                  rst (pop stack)]
              (if (contains? (into #{} acc) nxt)
                (recur acc rst)
                (recur (conj acc nxt) (apply list (concat (neighbor-list this nxt) rst))))))))
      ;; N.B.: bft is exactly the same as dft, except for the order of the concat in the tail call
      (bft [this n]
        (loop [acc [] stack (list n)]
          (if (zero? (count stack))
            acc
            (let [nxt (peek stack)
                  rst (pop stack)]
              (if (contains? (into #{} acc) nxt)
                (recur acc rst)
                (recur (conj acc nxt) (apply list (concat rst (neighbor-list this nxt))))))))) ; this is the only line that differs from dst
      (toString [_]
        (.toString @state)))))

