(ns graffy.core-test
  (:use clojure.test
        graffy.core))

(deftest test-nodes
  (testing "nodes"
    (let [g (make-graph)]
      (is (= #{} (into #{} (nodes g))))
      (add-edge g :a :b)
      (is (= #{:a :b} (into #{} (nodes g)))))))

(deftest test-neighbors
  (testing "neighbors"
    (let [g (make-graph)]
      (add-edge g :a :b)
      (is (= #{:b} (into #{} (neighbors g :a))))
      (is (= #{:a} (into #{} (neighbors g :b))))
      (is (= #{} (into #{} (neighbors g :c)))))))

(deftest test-edges
  (testing "edges"
    (let [g (make-graph)]
      (-> g (add-edge :a :b)
            (add-edge :a :c)
            (add-edge :a :d)
            (add-edge :b :d))
      (is (= #{[:a :b] [:a :c] [:a :d]} (into #{} (edges g :a))))
      (is (= #{[:d :a] [:d :b]} (into #{} (edges g :d)))))))

(deftest test-all-edges
  (testing "all-edges"
    (let [g (-> (make-graph)
                (add-edge :a :b))]
      (is (= #{[:a :b] [:b :a]} (into #{} (all-edges g)))))))

(deftest test-dft
  (testing "depth-first traversal"
    (let [g (-> (make-graph)
                (add-edge 0 1)
                (add-edge 0 2)
                (add-edge 0 6)
                (add-edge 0 5)
                (add-edge 5 3)
                (add-edge 5 4)
                (add-edge 3 4)
                (add-edge 4 6))]
      (is (= [0 1 2 5 3 4 6] (dft g 0)))
      (is (= [5 0 1 2 6 4 3] (dft g 5))))))
