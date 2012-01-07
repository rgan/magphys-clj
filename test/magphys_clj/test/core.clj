(ns magphys-clj.test.core
  (:use [magphys-clj.core])
  (:use [clojure.test]))

(deftest test-cosomological-constant
   (is (= 4.7619047619047614E-5 (cosmological_constant 70.0 0.3 0.7)))
)
