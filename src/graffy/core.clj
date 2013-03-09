(ns graffy.core)

(defn add-node [g n]
  (when-not (@g n)
    (swap! g assoc n #{}))
  @g)

(defn add-nodes [g & ns]
  (doseq [n ns]
    (add-node g n)))

(defn add-edge [g s d]
  (add-nodes g s d)
  (swap! g update-in [s] conj d)
  (swap! g update-in [d] conj s)
  @g)

(comment
  (def g (atom {}))
  (add-edge g :a :b)
  (add-edge g :a :c)
)
