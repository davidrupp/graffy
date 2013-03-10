# graffy

Simple graph representation and manipulation in Clojure.

## Usage

```clojure
  (let [g (make-graph)]
    (add-edge g :a :b)
    (add-edge g :a :c))
  (-> (make-graph)
      (add-edge :a :b)
      (add-edge :a :c))
  (doto (make-graph)
    (add-edge :a :b)
    (add-edge :a :c))
```

## License

Copyright © 2013 David Rupp

Distributed under the Eclipse Public License, the same as Clojure.
