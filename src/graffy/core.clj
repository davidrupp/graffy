(ns graffy.core)

(defn add-node [g n]
  (when-not (@g n)
    (swap! g assoc n #{}))
  @g)

(defn add-edge [g s d]
  (add-node g s)
  (add-node g d)
  (swap! g update-in [s] conj d)
  (swap! g update-in [d] conj s)
  @g)

(comment
  (def g (atom {}))
  (add-node g :a)
  (add-edge g :a :b)
)
