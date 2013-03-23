(ns graffy.core)

(defn add-edge [g s d]
  (when-not (@g s) (swap! g assoc s (sorted-set)))
  (when-not (@g d) (swap! g assoc d (sorted-set)))
  (swap! g update-in [s] conj d)
  (swap! g update-in [d] conj s)
  g)

(defn vertices [g]
  (keys @g))

(defn neighbors [g n]
  (or (@g n)
      (sorted-set)))

(defn edges [g n]
  (for [dest (neighbors g n)]
    [n dest]))

(defn all-edges [g]
  (apply concat (for [source (vertices g)]
                  (edges g source))))

(defn neighbor-list [g n]
  (apply list (neighbors g n)))

(defn dft [g n]
  (loop [acc [] stack (list n)]
    (if (zero? (count stack))
      acc
      (let [nxt (peek stack)
            rst (pop stack)]
        (if (contains? (into #{} acc) nxt)
          (recur acc rst)
          (recur (conj acc nxt) (apply list (concat (neighbor-list g nxt) rst))))))))

;; TODO: Refactor me!; bft differs from dft only in the concat call in tail position**
(defn bft [g n]
  (loop [acc [] stack (list n)]
    (if (zero? (count stack))
      acc
      (let [nxt (peek stack)
            rst (pop stack)]
        (if (contains? (into #{} acc) nxt)
          (recur acc rst)
          (recur (conj acc nxt) (apply list (concat rst (neighbor-list g nxt))))))))) ;; ** here


