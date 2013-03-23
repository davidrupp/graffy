(ns graffy.core-test
  (:use clojure.test
        graffy.core))

(deftest test-vertices
  (testing "vertices"
    (let [g {}]
      (is (= #{} (into #{} (vertices g))))
      (is (= #{:a :b} (into #{} (vertices (add-edge g :a :b))))))))

(deftest test-neighbors
  (testing "neighbors"
    (let [g (add-edge {} :a :b)]
      (is (= #{:b} (into #{} (neighbors g :a))))
      (is (= #{:a} (into #{} (neighbors g :b))))
      (is (= #{} (into #{} (neighbors g :c)))))))

(deftest test-traversal
  (testing "depth-first, breadth-first traversal"
    (let [g (-> {}
                (add-edge 0 1)
                (add-edge 0 2)
                (add-edge 0 5)
                (add-edge 0 6)
                (add-edge 3 4)
                (add-edge 4 6)
                (add-edge 5 3)
                (add-edge 5 4))]
      (is (= [0 1 2 5 3 4 6] (dft g 0)))
      (is (= [5 0 1 2 6 4 3] (dft g 5)))
      (is (= [0 1 2 5 6 3 4] (bft g 0)))
      (is (= [5 0 3 4 1 2 6] (bft g 5))))))
