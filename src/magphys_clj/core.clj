(ns magphys-clj.core
    (:require [clojure.string :as str])
    (:use [incanter.core])
    (:use [magphys-clj.model])
)

(defn cosmological_constant [h omega omega_lambda]
        (/ omega_lambda (* 3 (pow h 2))))

(defn q [cosmological_constant omega omega_lambda]
             (if (= omega_lambda 0)
             (/ omega 2.0)
                 (- (* 3 (/ omega 2.0) 1))))

(defrecord Filter [name lambda_eff id])

(defn make-filter [name lambda_eff id]
	(Filter. name lambda_eff id)
)

(defn read-file [file] (str/split-lines (slurp file)))

(defn read-filter-file [file]
	(map #(let [x (str/split % #"\t\s")] (make-filter (first x) (Float. (second x)) (Integer. (str/trim (nth x 2))))) 
	     (rest (read-file file))) ; first line is header
)

;if lambda > 10 microns, contribution from SPs is negligible
(defn filters-within-10micron-limit [filters redshift]
	(filter #(let [lambda_rest (/ (:lambda_eff %) (+ 1.0 redshift))] (< lambda_rest 10)) filters)
)

(defn extract-number-list [astr]
   (map #(Double. %) (rest (str/split (str/replace astr #"\s+" ",") #",")))
)

(defn read-sed-model [reader]
   (let [model-params (make-model-params (extract-number-list (.readLine reader)))
         star-formation-history (make-star-formation-history (extract-number-list (.readLine reader)))
         fprop (extract-number-list (.readLine reader))
         fprop0 (extract-number-list (.readLine reader))
        ]
      (make-sed-model fprop fprop0 model-params star-formation-history))
)

(defn ab-magnitudes-from-filter-fluxes [filter-fluxes]
	(map #(+ (- (* -2.5 (log10 %)) 48.6) (* 5.0 (log10 (* 1.7684e+08 1.e-5)))) filter-fluxes)
)



