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
  (loop [acc [] stack (seq [n])]
    (if (zero? (count stack))
      acc
      (if (some #{(first stack)} acc)
        (recur acc (rest stack))
        (if (= strategy :df)
          (recur (conj acc (first stack)) (concat (neighbors g (first stack)) (rest stack)))
          (recur (conj acc (first stack)) (concat (rest stack) (neighbors g (first stack)))))))))

(defn dft [g n]
  (traverse g n :df))

(defn bft [g n]
  (traverse g n :bf))
