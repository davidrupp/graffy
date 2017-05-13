(ns graffy.core)

(defn add-edge
  "graph: a map of sources to sets of dests; adds a bi-directional edge"
  [graph source dest]
  (-> graph
      (assoc source (or (graph source) (sorted-set)))
      (assoc dest (or (graph dest) (sorted-set)))
      (update-in [source] conj dest)
      (update-in [dest] conj source)))

(defn vertices [graph]
  (keys graph))

(defn neighbors [graph node]
  (seq (or (graph node)
           (sorted-set))))

(defn- traverse [graph node strategy]
  (assert (#{:bf :df} strategy) "Strategy must be one of :bf (breadth-first) or :df (depth-first)")
  (loop [acc []
         stack (seq [node])]
    (if (zero? (count stack))
      acc
      (if (some #{(first stack)} acc)
        (recur acc (rest stack))
        (if (= strategy :df)
          (recur (conj acc (first stack)) (concat (neighbors graph (first stack)) (rest stack)))
          (recur (conj acc (first stack)) (concat (rest stack) (neighbors graph (first stack)))))))))

(defn dft [graph node]
  (traverse graph node :df))

(defn bft [graph node]
  (traverse graph node :bf))
