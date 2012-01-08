(ns magphys-clj.test.model
  (:use [magphys-clj.model])
  (:use [clojure.test])
)

(deftest test-make-star-formation-history
	(let [result (make-star-formation-history [2 1 2 3 4 5])]
	   (is (= 2 (:nage result)))
	   (is (= [1 2] (:age result)))
	   (is (= [3 4] (:sfr result)))
	   (is (= [5] (:sfrav result)))
	   )
)

(deftest test-make-model-params
   (let [result (make-model-params [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,0,0,0,0,23,24,0,0,25,26,27])]
      (is (= [11 12 13 14 15] (:fburst result)))
      (is (= [16 17 18 19 20] (:ftot result)))
  )
)