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

(defn- traverse [g n strategy]
  (assert (#{:bf :df} strategy) "Strategy must be one of :bf (breadth-first) or :df (depth-first)")
  (loop [acc [] stack (list n)]
    (if (zero? (count stack))
      acc
      (let [nxt (peek stack)
            rst (pop stack)]
        (if (contains? (into #{} acc) nxt)
          (recur acc rst)
          (if (= strategy :df)
            (recur (conj acc nxt) (apply list (concat (neighbors g nxt) rst)))
            (recur (conj acc nxt) (apply list (concat rst (neighbors g nxt))))))))))

(defn dft [g n]
  (traverse g n :df))

(defn bft [g n]
  (traverse g n :bf))
