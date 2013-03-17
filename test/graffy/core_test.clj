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
