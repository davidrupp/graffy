(ns graffy.core)

(defn add-edge [g s d]
  (-> g
      (assoc s (or (g s) (sorted-set)))
      (assoc d (or (g d) (sorted-set)))
      (update-in [s] conj d)
      (update-in [d] conj s)))

(defn vertices [g]
  (keys g))

(defn neighbors [g n]
  (seq (or (g n)
           (sorted-set))))

(defn dft [g n]
  (loop [acc [] stack (list n)]
    (if (zero? (count stack))
      acc
      (let [nxt (peek stack)
            rst (pop stack)]
        (if (contains? (into #{} acc) nxt)
          (recur acc rst)
          (recur (conj acc nxt) (apply list (concat (neighbors g nxt) rst))))))))

;; TODO: Refactor me!; bft differs from dft only in the concat call in tail position**
(defn bft [g n]
  (loop [acc [] stack (list n)]
    (if (zero? (count stack))
      acc
      (let [nxt (peek stack)
            rst (pop stack)]
        (if (contains? (into #{} acc) nxt)
          (recur acc rst)
          (recur (conj acc nxt) (apply list (concat rst (neighbors g nxt))))))))) ;; ** here
