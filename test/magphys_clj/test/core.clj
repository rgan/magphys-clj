(ns magphys-clj.test.core
  (:use [magphys-clj.core])
  (:use [clojure.test])
)

(defn float= [x y]
  (let [epsilon 0.00001
        scale (if (or (zero? x) (zero? y)) 1 (Math/abs x))]
    (<= (Math/abs (- x y)) (* scale epsilon))))

(deftest test-cosomological-constant
   (is (= 4.7619047619047614E-5 (cosmological_constant 70.0 0.3 0.7)))
)

(deftest test-read-filter-file
   (let [result (read-filter-file "filters.dat")]
	(is (= 20 (count result)))
	(is (= "U" (:name (first result))))
	(is (float= 0.346 (:lambda_eff (first result))))
	(is (= 222 (:id (first result))))
))

(deftest test-should-return-filters-within-10micron-limit
	(is (= 1 (count (filters-within-10micron-limit (list (make-filter "U" 0.346 222)) 0.4600))))
)

(deftest test-should-not-return-filters-outside-10micron-limit
	(is (= 0 (count (filters-within-10micron-limit (list (make-filter "SPIRE500" 500 174)) 0.4600))))
)

(deftest test-should-read-wavelengths-from-optilib-file
	(let [result (read-optilib "optilib.txt")]
		(is (= 6917 (count result)))
		(is (float= 91.0 (first result)))
	)
)

(deftest test-should-read-sed-model-from-optilib-file
	(with-open [reader (java.io.BufferedReader. (java.io.FileReader. "optilib.txt"))]
	    (.readLine reader)  ; consume the first two lines with wavelength info
	    (.readLine reader)
		(let [sed-model (read-sed-model reader)]
			(is (float= 1.14501693E+10 (-> sed-model :model-params :tform)))
			(is (= 816 (-> sed-model :star-formation-history :nage)))
			(is (= 6917 (count (-> sed-model :fprop))))
			(is (= 6917 (count (-> sed-model :fprop0))))
		)
	)
)