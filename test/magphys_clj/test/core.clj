(ns magphys-clj.test.core
  (:use [magphys-clj.core])
  (:use [clojure.test]))

(defn float= [x y]
  (let [epsilon 0.00001
        scale (if (or (zero? x) (zero? y)) 1 (Math/abs x))]
    (<= (Math/abs (- x y)) (* scale epsilon))))

(deftest test-cosomological-constant
   (is (= 4.7619047619047614E-5 (cosmological_constant 70.0 0.3 0.7)))
)

(deftest test-read-file
   (let [result (read-file "filters.dat")]
	(is (= 20 (count result)))
	(is (= "U" (:name (first result))))
	(is (float= 0.346 (:lambda_eff (first result))))
	(is (= 222 (:id (first result))))
))
